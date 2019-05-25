package com.utils;

public interface Constant {

    String pre = " create table <<tableName>> \n" +
            "(\n" +
            "<<itemContent>>  \n" +
            ")\n" +
            "/\n" +
            "comment on table <<tableName>> is '<<tableNameDesc>>'" +
            "\n" +
            "/\n" +
            "<<annotationContent>>";


    //oracle数据类型相关
    String NUMBER = "NUMBER";
    String VARCHAR2 = "VARCHAR2";
    String DATE = "DATE";
    String NOT_NULL = "not null";
    String STRING_MAX_LENGTH = "256";//默认字符串最大位数
    String ROW_PRE = "    ";//行开头缩进

}
