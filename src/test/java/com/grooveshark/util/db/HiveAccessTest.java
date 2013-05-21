package com.grooveshark.util.db;

import java.sql.Types;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.fail;


public class HiveAccessTest
{
    public DBAccess hiveAccess = null;
    public DBProperties dbProps = null;

    @Before
    public void setup() {
        try {
            this.dbProps = new DBProperties();
            this.hiveAccess = new DBAccess();
            this.hiveAccess.setUrl(this.dbProps.getHiveUrl());
            this.hiveAccess.setDriver(this.dbProps.DEFAULT_HIVE_DRIVER);
            this.hiveAccess.setCheckIfValid(false);
            this.hiveAccess.makeConnection();
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed to setup DB connection" + e.getMessage());
        }

    }

    @Test
    public void executeQueryTest()
    {
        try {
            long start = System.currentTimeMillis();
            System.out.println("Query result: " + this.hiveAccess.executeQuery(this.dbProps.getHiveTestQuery()));
            float elapsed = (System.currentTimeMillis() - start)/(float) 1000;
            System.out.println("Done ("+elapsed+" secs).");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
