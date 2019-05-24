package com.utils;


import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;

import java.util.List;

public class ExcelToSql {

    public static void main(String[] args) throws Exception {


        String tableName = "MY_USER";  //表名
        String tableNameDesc = "我的用户表";  //表名


        File file = new File("D:\\excelToSql\\src\\main\\resources\\Excel.xlsx"); //文件位置
        FileInputStream fileInputStream = new FileInputStream(file);
        String name = file.getName();
        List<Excel> list = ExcelUtils.readExcelToEntity(Excel.class, fileInputStream, name);

        // System.out.println("list :" + list);

        StringBuilder sbuilder = new StringBuilder();  //总的部分
        //sbuilder.append(); //sql 前部分
        StringBuilder sbInner = new StringBuilder();  //具体表字段信息

        StringBuilder sbAnnotation = new StringBuilder();  //后面注释内容
        String rowAnnotation = "\n" +
                "comment on column {{tableName}}.{{ITEM_LINE}} is '{{annotation}}'\n" +
                "/";

        String rowModel = " {{rowItem}}              {{dataType}}{{(length)}}       {{isNeed}}";
        String rowModelEnd = ",\n"; //最后一行不加

        if (CollectionUtils.isNotEmpty(list)) {
            for (int i = 0; i < list.size(); i++) {
                //oracle
                Excel vo = list.get(i);
                if (vo != null) {
                    if (i != (list.size() - 1)) {
                        rowModel += rowModelEnd;  //最后一行不加
                    }
                    String item = vo.getItem();
                    String annotation = vo.getAnnotation();
                    String type = vo.getType(); //数据类型
                    String isNeed = vo.getIsNeed(); //是否必填
                    if (StringUtils.isBlank(item) || StringUtils.isBlank(annotation)
                            || StringUtils.isBlank(type) || StringUtils.isBlank(isNeed)) {
                        System.out.println("当前行存在空数据 :" + vo);

                        return;  //报错
                    }
                    if (type.contains("int") || type.contains("Number") || type.contains("Int") || type.contains("number")) {
                        type = Constant.NUMBER;
                    } else {
                        type = Constant.VARCHAR2;
                    }


                    if (isNeed.contains("是") || isNeed.contains("true") || isNeed.contains("True")) {
                        isNeed = Constant.NOT_NULL;
                    } else {
                        isNeed = "";
                    }

                    rowModel.replace("{{rowItem}}", item).
                            replace("{{dataType}}", type).
                            //replace("{{annotation}}", annotation).
                                    replace("{{isNeed}}", isNeed);

                    sbInner.append(rowModel);   //列 拼接

                    String ITEM_LINE = ChangeChar.camelToUnderline(item, 2); //转大写下划线
                    rowAnnotation.replace("{{tableName}}", tableName).
                            replace("{{ITEM_LINE}}", ITEM_LINE).
                            replace("{{annotation}}", annotation).
                            replace("{{tableNameDesc}}", tableNameDesc);

                    sbAnnotation.append(rowAnnotation);  //注释拼接
                }
            }
        }
        //sbuilder.replace("{{itemContent}}", sbInner).append(sbAnnotation);
       String  preStr= Constant.pre.replace("{{tableName}}", tableName); //替换一下表名
        preStr.replace("{{itemContent}}", sbInner);
        sbuilder.append(preStr).append(sbAnnotation);
        //Constant.pre.replace("{{tableName}}", tableName)
        System.out.println("sbuilder :" + sbuilder);


    }
}
