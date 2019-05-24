package com.utils;

public interface Constant {

    String pre = " create table _tableName_ \n" +
            "(\n" +
            "    _itemContent_  \n" +
            ")\n" +
            "/\n" +
            "\n" +
            "comment on table _tableName_ is '_tableNameDesc_'" +
            "/\n"+
            "_annotationContent_";



    //oracle数据类型相关
    String NUMBER = "NUMBER";
    String VARCHAR2 = "VARCHAR2";
    String DATE = "DATE";
    String NOT_NULL = "not null";

}
