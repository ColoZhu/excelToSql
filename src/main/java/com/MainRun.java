package com;

import com.utils.ExcelToSql;

public class MainRun {
    public static void main(String[] args) throws Exception {
        /**
         * 本工具主要为EXCEL生成ORACLE数据库建表sql
         * 第一步:修改resource文件夹下面Excel.xlsx内容(第一行不要修改),
         * item:列字段,	annotation:注释,type:数据类型(Number/String/Date 默认String),maxLength:最大长度(字符串默认256),isNeed:是否必须(是/否)
         * 第二步:设置 下面的tableName, tableNameDesc,excelPath
         * 第三步:运行MainRun.java类下的main方法,控制台输出sqls
         * 注意格式:只测试过excel里面(驼峰字段)--->生成表列都是(下划线大写)
         *
         */


        //String tableName = "DLB_PURCHASE_ORDER_HEADER";  //表名,必填
       // String tableNameDesc = "带萝卜-采购订单头表";  //表名,可以为空
        //String excelPath = "D:\\code\\excelToSql\\src\\main\\resources\\Excel.xlsx"; //excel硬盘上路径

        String tableNameDesc = "带萝卜-采购订单明细表";  //表名,可以为空
        String tableName = "DLB_PURCHASE_ORDER_DETAIL";  //表名,必填
        String excelPath = "D:\\code\\excelToSql\\src\\main\\resources\\Excel_DLB_PURCHASE_ORDER_DETAIL.xlsx";


        String sql = ExcelToSql.createSql(tableName, tableNameDesc, excelPath);

    }
}
