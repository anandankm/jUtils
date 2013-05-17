package com.grooveshark.util.db;

import com.grooveshark.util.FileUtils;
import com.google.gson.JsonElement;

public class DBProperties
{
    public static String DEFAULT_MYSQL_URL = "jdbc:mysql://localhost:3306/mysql";
    public static String DEFAULT_MYSQL_USER = "root";
    public static String DEFAULT_MYSQL_PASS = "pass";
    public static String DEFAULT_MONGO_HOST = "localhost";
    public static int DEFAULT_MONGO_PORT = 2718;
    public static String DEFAULT_MONGO_DB = "test";

    public static String HIVE_URL = "jdbc:hive://localhost:10000/default";
    public static String HIVE_TEST_QUERY = "";

    public static String DEFAULT_MYSQL_DRIVER = "com.mysql.jdbc.Driver";
    public static String DEFAULT_HIVE_DRIVER = "org.apache.hadoop.hive.jdbc.HiveDriver";

    public static final String dsnFilename = "dsn/sample.dsn.json";

    public DBProperties()
    {
    }

    public void loadDefaults()
        throws Exception
    {
        String[] defaults = { "defaults" };
        JsonElement je = FileUtils.parseJson(dsnFilename, defaults);
        this.DEFAULT_MONGO_HOST = FileUtils.getJsonValue(je, "mongo_host");
        this.DEFAULT_MONGO_PORT = Integer.parseInt(FileUtils.getJsonValue(je, "mongo_port"));
        this.DEFAULT_MONGO_DB = FileUtils.getJsonValue(je, "mongo_db");
        String mysqlHost = FileUtils.getJsonValue(je, "mysql_host");
        String mysqlDB = FileUtils.getJsonValue(je, "mysql_db");
        String mysqlOptions = FileUtils.getJsonValue(je, "mysql_options");
        this.DEFAULT_MYSQL_URL = "jdbc:mysql://" + mysqlHost + ":3306/" + mysqlDB + mysqlOptions;
        System.out.println(this.DEFAULT_MYSQL_URL);
    }

}
