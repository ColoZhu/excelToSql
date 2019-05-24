package com.utils;


import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;

import java.util.List;

public class ExcelToSql {

    public static void main(String[] args) throws Exception {


        String tableName = "MY_USER";  //表名
        String tableNameDesc = "我的用户表";  //表名


        File file = new File("D:\\code\\excelToSql\\src\\main\\resources\\Excel.xlsx"); //文件位置
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
                    String rowModel = " _rowItem_              _dataType__(length)_       _isNeed_";

                    if (i != (list.size() - 1)) {
                        rowModel += rowModelEnd;  //最后一行不加
                    }

                    String item = vo.getItem();
                    String annotation = vo.getAnnotation();
                    String type = vo.getType(); //数据类型
                    String isNeed = vo.getIsNeed(); //是否必填

                    if (StringUtils.isBlank(item)) {
                        continue; //列不存在直接跳过
                    }
                    //用于替换sql语句中的占位 字符串__
                    String itemNew = item;
                    String annotationNew = annotation;
                    String typeNew = Constant.VARCHAR2;   //默认字符串类型
                    String isNeedNew = "";  //默认不必填


                    if (type.contains("int") || type.contains("Number") || type.contains("Int") || type.contains("number")) {
                        typeNew = Constant.NUMBER;
                    } else if (type.contains("date") || type.contains("Date") || type.contains("日期")) {
                        typeNew = Constant.DATE;
                    }

                    if (isNeed.contains("是") || isNeed.contains("true") || isNeed.contains("True")) {
                        isNeedNew = Constant.NOT_NULL;
                    }

                    //默认值问题???

                    //列字段相关
                    rowModel.replaceAll("_rowItem_", itemNew).
                            replaceAll("_dataType_", typeNew).
                            //replace("_annotation_", annotation).
                                    replaceAll("_isNeed_", isNeedNew);

                    itemContent+=rowModel;   //列 拼接

                    //注释相关
                    String rowAnnotation = "\n" +
                            "comment on column _tableName_._ITEM_LINE_ is '_annotation_'\n" +
                            "/";
                    String ITEM_LINE = ChangeChar.camelToUnderline(item, 2); //对应数据库列名转大写下划线
                    rowAnnotation.replaceAll("_tableName_", tableName).
                            replaceAll("_ITEM_LINE_", ITEM_LINE).
                            replaceAll("_annotation_", annotation)
                    ;

                    annotationContent+=rowAnnotation;  //注释拼接
                }
            }
        }


        //替换一下表字段名和注释部分
        sqlStr.replaceAll("_itemContent_", itemContent)
                .replaceAll("_tableNameDesc_", tableNameDesc)
                .replaceAll("_annotationContent_", annotationContent)
        ;

        sqlStr.replaceAll("_tableName_", tableName);
        System.out.println("-----------下面是生成表的sql-------------- :");
        System.out.println(sqlStr);
// create table _tableName_
//(
//    _itemContent_
//)
///
//
//comment on table _tableName_ is '_tableNameDesc_'/
//_annotationContent_

    }
}
