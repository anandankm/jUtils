package com.grooveshark.util.db;

import java.sql.Types;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.fail;

import org.apache.log4j.Logger;


public class HiveAccessTest
{
    public DBAccess hiveAccess = null;
    public DBProperties dbProps = null;
    public static final Logger log = Logger.getLogger(HiveAccessTest.class);

    //@Before
    public void setup() {
        try {
            this.dbProps = new DBProperties();
            this.hiveAccess = new DBAccess();
            this.hiveAccess.setUrl(DBProperties.DEFAULT_HIVE_URL);
            this.hiveAccess.setDriver(DBProperties.DEFAULT_HIVE_DRIVER);
            this.hiveAccess.setCheckIfValid(false);
            this.hiveAccess.makeConnection();
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed to setup DB connection" + e.getMessage());
        }

    }

    //@Test
    public void executeQueryTest()
    {
        long start = System.currentTimeMillis();
        log.debug("Executing test query");
        String resultStr = "";
        try {
            ResultSet res = this.hiveAccess.executeQuery(this.dbProps.getHiveTestQuery());
            while (res.next()) {
                resultStr += res.getString(1) + ", ";
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            fail("Failed to execute query: " + this.dbProps.getHiveTestQuery() + "\n" + e.getMessage());
        }
        log.debug("Query result: " + resultStr);
        float elapsed = (System.currentTimeMillis() - start)/(float) 1000;
        log.debug("Done ("+elapsed+" secs).");
    }

}
