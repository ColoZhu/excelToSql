version: 1.00 版

 * 本工具主要为EXCEL生成ORACLE数据库建表sql

 * 第一步:修改resource文件夹下面Excel.xlsx内容(第一行不要修改),

 * item:列字段,
   annotation:注释,
   type:数据类型(Number/String/Date 默认String),
   maxLength:最大长度(字符串默认256),
   isNeed:是否必须(是/否)

 * 第二步:点开MainRun.java类里面的main方法,修改表名和表名描述和Excel文件完整硬盘路径(tableName, tableNameDesc,excelPath)

 * 第三步:运行MainRun.java类下的main方法,~~控制台输出sql~~~

 * 注意格式:只测试过excel里面(驼峰字段)--->生成表列都是(下划线大写)

 ----后面可能会上传对应mysql数据库的对应的版本---
 ----有兴趣的或已经实现其他数据库的小伙伴可以留言交流一下---