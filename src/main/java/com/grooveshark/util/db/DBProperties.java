package com.grooveshark.util.db;

import com.grooveshark.util.FileUtils;
import com.google.gson.JsonElement;

import org.apache.log4j.Logger;

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

    public static String DEFAULTS_DSN_FILENAME = "dsn/sample.dsn.json";

    public static final Logger log = Logger.getLogger(DBProperties.class);

    protected String mysqlHost = "localhost";
    protected String mysqlDB = "mysql";
    protected String mysqlOptions = "?allowMultiQueries=true";

    protected String mongoHost = "localhost";

    public DBProperties() {
    }

    public void loadDefaults() throws Exception {
        String[] defaults = { "defaults" };
        JsonElement je = FileUtils.parseJson(this.defaultsDsnFilename, defaults);
        this.DEFAULT_MONGO_HOST = FileUtils.getJsonValue(je, "mongo_host");
        this.DEFAULT_MONGO_PORT = Integer.parseInt(FileUtils.getJsonValue(je, "mongo_port"));
        this.DEFAULT_MONGO_DB = FileUtils.getJsonValue(je, "mongo_db");
        String mysqlHost = FileUtils.getJsonValue(je, "mysql_host");
        String mysqlDB = FileUtils.getJsonValue(je, "mysql_db");
        String mysqlOptions = FileUtils.getJsonValue(je, "mysql_options");
        this.DEFAULT_MYSQL_URL = "jdbc:mysql://" + mysqlHost + ":3306/" + mysqlDB + mysqlOptions;
        log.debug("Default mysql url: " + this.DEFAULT_MYSQL_URL);
        this.DEFAULT_MYSQL_DRIVER = this.getMysqlDriver(je);
    }

    public String getMysqlDB(JsonElement je) throws Exception {
        String mysql_db = FileUtils.getJsonValue(je, "mysql_db");
        return mysql_db.equals("") ? this.mysqlDB : mysql_db;
    }

    public String getMysqlDriver(JsonElement je) throws Exception {
        String mysql_driver = FileUtils.getJsonValue(je, "mysql_driver");
        return mysql_driver.equals("") ? this.DEFAULT_MYSQL_DRIVER : mysql_driver;
    }

    public void setDefaultsDsnFilename(String filename) {
        this.DEFAULTS_DSN_FILENAME = filename;
    }

    /**
     * Setter method for mysqlHost
     */
    public void setMysqlHost(String mysqlHost) {
        this.mysqlHost = mysqlHost;
    }
    /**
     * Getter method for mysqlHost
     */
    public String getMysqlHost() {
        return this.mysqlHost;
    }
    /**
     * Setter method for mysqlDB
     */
    public void setMysqlDB(String mysqlDB) {
        this.mysqlDB = mysqlDB;
    }
    /**
     * Getter method for mysqlDB
     */
    public String getMysqlDB() {
        return this.mysqlDB;
    }
    /**
     * Setter method for mysqlOptions
     */
    public void setMysqlOptions(String mysqlOptions) {
        this.mysqlOptions = mysqlOptions;
    }
    /**
     * Getter method for mysqlOptions
     */
    public String getMysqlOptions() {
        return this.mysqlOptions;
    }
    /**
     * Setter method for mongoHost
     */
    public void setMongoHost(String mongoHost) {
        this.mongoHost = mongoHost;
    }
    /**
     * Getter method for mongoHost
     */
    public String getMongoHost() {
        return this.mongoHost;
    }
}
