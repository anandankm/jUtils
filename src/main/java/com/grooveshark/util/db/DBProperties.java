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


    protected String mysqlHost = "localhost";
    protected String mysqlDB = "mysql";
    protected String mysqlOptions = "";

    protected String mongoHost = "localhost";
    protected String mongoDB = "test";
    protected int mongoPort = 2718;

    protected String hiveHost = "localhost";
    protected String hiveDB = "default";
    protected int hivePort = 10000;

    public static final Logger log = Logger.getLogger(DBProperties.class);

    public DBProperties() {
    }

    public void loadDefaults() throws Exception {
        String[] defaults = { "defaults" };
        JsonElement je = FileUtils.parseJson(this.DEFAULTS_DSN_FILENAME, defaults);
        this.DEFAULT_MONGO_HOST = this.getJsonMongoHost(je);
        this.DEFAULT_MONGO_PORT = this.getJsonMongoPort(je);
        this.DEFAULT_MONGO_DB = this.getJsonMongoDB(je);
        this.DEFAULT_MYSQL_URL = this.getJsonMysqlUrl(je);
        this.DEFAULT_MYSQL_DRIVER = this.getJsonMysqlDriver(je);
        this.HIVE_URL = this.getJsonHiveUrl(je);
        this.HIVE_TEST_QUERY = this.getJsonHiveTestQuery(je);
        log.debug("DEFAULT_MONGO_HOST=" + this.DEFAULT_MONGO_HOST);
        log.debug("DEFAULT_MONGO_PORT=" + this.DEFAULT_MONGO_PORT);
        log.debug("DEFAULT_MONGO_DB=" + this.DEFAULT_MONGO_DB);
        log.debug("DEFAULT_MYSQL_URL=" + this.DEFAULT_MYSQL_URL);
        log.debug("DEFAULT_MYSQL_DRIVER=" + this.DEFAULT_MYSQL_DRIVER);
        log.debug("HIVE_URL=" + this.HIVE_URL);
        log.debug("HIVE_TEST_QUERY=" + this.HIVE_TEST_QUERY);
    }

    public String getJsonHiveTestQuery(JsonElement je) throws Exception {
        String hive_test_query = FileUtils.getJsonValue(je, "hive_test_query");
        return hive_test_query.equals("") ? this.HIVE_TEST_QUERY : hive_test_query;
    }

    public String getJsonHiveUrl(JsonElement je) throws Exception {
        this.hiveHost = this.getJsonHiveHost(je);
        this.hiveDB = this.getJsonHiveDB(je);
        this.hivePort = this.getJsonHivePort(je);
        return "jdbc:hive://" + this.hiveHost + ":" + this.hivePort + "/" + this.hiveDB;
    }

    public String getJsonHiveHost(JsonElement je) throws Exception {
        String hive_host = FileUtils.getJsonValue(je, "hive_host");
        return hive_host.equals("") ? this.hiveHost : hive_host;
    }

    public String getJsonHiveDB(JsonElement je) throws Exception {
        String hive_db = FileUtils.getJsonValue(je, "hive_db");
        return hive_db.equals("") ? this.hiveDB : hive_db;
    }

    public int getJsonHivePort(JsonElement je) throws Exception {
        String hive_port = FileUtils.getJsonValue(je, "hive_port");
        return hive_port.equals("") ? this.hivePort : Integer.parseInt(hive_port);
    }

    public String getJsonMongoHost(JsonElement je) throws Exception {
        String mongo_host = FileUtils.getJsonValue(je, "mongo_host");
        return mongo_host.equals("") ? this.mongoHost : mongo_host;
    }

    public int getJsonMongoPort(JsonElement je) throws Exception {
        String mongo_port = FileUtils.getJsonValue(je, "mongo_port");
        return mongo_port.equals("") ? this.mongoPort : Integer.parseInt(mongo_port);
    }

    public String getJsonMongoDB(JsonElement je) throws Exception {
        String mongo_db = FileUtils.getJsonValue(je, "mongo_db");
        return mongo_db.equals("") ? this.mongoDB : mongo_db;
    }

    public String getJsonMysqlDriver(JsonElement je) throws Exception {
        String mysql_driver = FileUtils.getJsonValue(je, "mysql_driver");
        return mysql_driver.equals("") ? this.DEFAULT_MYSQL_DRIVER : mysql_driver;
    }

    public String getJsonMysqlUrl(JsonElement je) throws Exception {
        this.mysqlHost = this.getJsonMysqlHost(je);
        this.mysqlDB = this.getJsonMysqlDB(je);
        this.mysqlOptions = this.getJsonMysqlOptions(je);
        return "jdbc:mysql://" + this.mysqlHost + ":3306/" + this.mysqlDB + this.mysqlOptions;
    }

    public String getJsonMysqlOptions(JsonElement je) throws Exception {
        String mysql_options = FileUtils.getJsonValue(je, "mysql_options");
        return mysql_options.equals("") ? this.mysqlOptions : mysql_options;
    }

    public String getJsonMysqlHost(JsonElement je) throws Exception {
        String mysql_host = FileUtils.getJsonValue(je, "mysql_host");
        return mysql_host.equals("") ? this.mysqlHost : mysql_host;
    }

    public String getJsonMysqlDB(JsonElement je) throws Exception {
        String mysql_db = FileUtils.getJsonValue(je, "mysql_db");
        return mysql_db.equals("") ? this.mysqlDB : mysql_db;
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
    /**
     * Setter method for mongoPort
     */
    public void setMongoPort(int mongoPort) {
        this.mongoPort = mongoPort;
    }
    /**
     * Getter method for mongoPort
     */
    public int getMongoPort() {
        return this.mongoPort;
    }
    /**
     * Setter method for mongoDB
     */
    public void setMongoDB(String mongoDB) {
        this.mongoDB = mongoDB;
    }
    /**
     * Getter method for mongoDB
     */
    public String getMongoDB() {
        return this.mongoDB;
    }
    /**
     * Setter method for hiveHost
     */
    public void setHiveHost(String hiveHost) {
        this.hiveHost = hiveHost;
    }
    /**
     * Getter method for hiveHost
     */
    public String getHiveHost() {
        return this.hiveHost;
    }
    /**
     * Setter method for hiveDB
     */
    public void setHiveDB(String hiveDB) {
        this.hiveDB = hiveDB;
    }
    /**
     * Getter method for hiveDB
     */
    public String getHiveDB() {
        return this.hiveDB;
    }
    /**
     * Setter method for hivePort
     */
    public void setHivePort(int hivePort) {
        this.hivePort = hivePort;
    }
    /**
     * Getter method for hivePort
     */
    public int getHivePort() {
        return this.hivePort;
    }
}
