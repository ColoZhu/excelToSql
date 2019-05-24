package com.utils;

public class Test1 {

    public static void main(String[] args) throws Exception {

        String tableName = "MY_USER";  //表名
        String  a=Constant.pre.replaceAll("<<tableName>>", tableName);
        System.out.println(a);
    }
}
