package com.utils;


import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;

import java.util.List;

public class ExcelToSql {


    /**
     * @param tableName
     * @param tableNameDesc
     * @param excelPath
     * @throws Exception
     */
    public static String createSql(String tableName, String tableNameDesc, String excelPath) throws Exception {
        //String tableName = "MY_USER";  //表名
        // String tableNameDesc = "我的用户表";  //表名描述
        // String excelPath = "D:\\code\\excelToSql\\src\\main\\resources\\Excel.xlsx";
        File file = new File(excelPath); //文件位置
        FileInputStream fileInputStream = new FileInputStream(file);
        String name = file.getName();
        List<Excel> list = ExcelUtils.readExcelToEntity(Excel.class, fileInputStream, name);
        // System.out.println("list :" + list);
        String sqlStr = Constant.pre;  //总的部分,先添加前置部分
        String itemContent = "";  //具体表字段信息
        String annotationContent = "";  //后面注释内容

        String rowModelEnd = ",\n"; //最后一行不加

        if (CollectionUtils.isNotEmpty(list)) {
            for (int i = 0; i < list.size(); i++) {
                //oracle
                Excel vo = list.get(i);
                if (vo != null) {

                    String item = vo.getItem();
                    String annotation = vo.getAnnotation();
                    String type = vo.getType(); //数据类型
                    String isNeed = vo.getIsNeed(); //是否必填
                    Integer maxLength = vo.getMaxLength(); //最大长度
                    if (StringUtils.isBlank(item)) {
                        continue; //列不存在直接跳过
                    }
                    //用于替换sql语句中的占位 字符串__
                    String itemNew = item;
                    String annotationNew = annotation;
                    String typeNew = Constant.VARCHAR2;   //默认字符串类型
                    String isNeedNew = "";  //默认不必填
                    String maxLengthNew = ""; //最大长度


                    if (type.contains("int") || type.contains("Num") || type.contains("Int")
                            || type.contains("数") || type.contains("num")) {
                        typeNew = Constant.NUMBER;
                    } else if (type.contains("date") || type.contains("Date") || type.contains("日期")) {
                        typeNew = Constant.DATE;
                    }

                    if (isNeed.contains("是") || isNeed.contains("true") || isNeed.contains("True")) {
                        isNeedNew = Constant.NOT_NULL;
                    }
                    if (isNeed.contains("是") || isNeed.contains("true") || isNeed.contains("True")) {
                        isNeedNew = Constant.NOT_NULL;
                    }

                    //如果是字符串类型没有指定长度,默认256
                    if (maxLength == null || maxLength < 0) {
                        if (typeNew.equals(Constant.VARCHAR2)) {
                            maxLengthNew = "(" + Constant.STRING_MAX_LENGTH + ")";
                        }
                    } else {
                        maxLengthNew = "(" + maxLength + ")";  //有值直接赋值
                    }
                    //默认值问题???

                    //列字段相关,行左端保持必要空格,方便格式对齐
                    String rowModel = Constant.ROW_PRE + "<<rowItem>>              <<dataType>><<(maxLength)>>       <<isNeed>>"; //maxlength有括号
                    if (i != (list.size() - 1)) {
                        rowModel += rowModelEnd;  //最后一行不加
                    }

                    rowModel = rowModel.replaceAll("<<rowItem>>", itemNew).
                            replaceAll("<<dataType>>", typeNew).
                            replace("<<(maxLength)>>", maxLengthNew).
                            replaceAll("<<isNeed>>", isNeedNew);

                    itemContent += rowModel;   //列拼接

                    //注释相关
                    String rowAnnotation = "\n" +
                            "comment on column <<tableName>>.<<ITEM_LINE>> is '<<annotation>>'\n" +
                            "/";
                    String ITEM_LINE = ChangeChar.camelToUnderline(item, 2); //对应数据库列名转大写下划线
                    rowAnnotation = rowAnnotation.replaceAll("<<tableName>>", tableName).
                            replaceAll("<<ITEM_LINE>>", ITEM_LINE).
                            replaceAll("<<annotation>>", annotationNew)
                    ;

                    annotationContent += rowAnnotation;  //注释拼接
                }
            }
        }


        //替换一下表字段名和注释部分
        sqlStr = sqlStr.replaceAll("<<itemContent>>", itemContent)
                .replaceAll("<<tableNameDesc>>", tableNameDesc)
                .replaceAll("<<annotationContent>>", annotationContent)
        ;

        sqlStr = sqlStr.replaceAll("<<tableName>>", tableName);
        System.out.println("-----------下面是生成表的sql--------------------------------------------------------");
        System.out.println(sqlStr);

        System.out.println("-----------sql结束----------------------------------------------------------------");
        return sqlStr;
    }

}
