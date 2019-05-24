package com.utils;

public interface Constant {

    String pre = " create table {{tableName}} \n" +
            "(\n" +
            "    {{itemContent}}  \n" +
            ")"
            + " create table DLB_SENDER\n" +
            "(\n" +
            "    {{itemContent}}  \n" +
            ")\n" +
            "/\n" +
            "\n" +
            "comment on table {{tableName}} is '{{tableNameDesc}}'";

    //oracle数据类型相关
    String NUMBER = "NUMBER";
    String VARCHAR2 = "VARCHAR2";
    String DATE = "DATE";
    String NOT_NULL = "not null";

}
