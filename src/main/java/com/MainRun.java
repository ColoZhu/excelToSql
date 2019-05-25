package com;

import com.utils.ExcelToSql;

public class MainRun {
    public static void main(String[] args) throws Exception {

        String tableName = "MY_USER";  //表名,必填
        String tableNameDesc = "我的用户表";  //表名,可以为空
        String excelPath = "D:\\code\\excelToSql\\src\\main\\resources\\Excel.xlsx"; //excel硬盘上路径
        String sql = ExcelToSql.createSql(tableName, tableNameDesc, excelPath);

    }
}
