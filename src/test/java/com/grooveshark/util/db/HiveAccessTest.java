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

    //@Before
    public void setup() {
        try {
            this.hiveAccess = new DBAccess(
                    DBAccess.DEFAULT_HIVE_DRIVER,
                    DBAccess.HIVE_URL, "", "", false);
        } catch (Exception e) {
            fail("Failed to setup DB connection" + e.getMessage());
        }

    }

    //@Test
    public void executeQueryTest()
    {
        try {
            long start = System.currentTimeMillis();
            System.out.println("Query result: " + this.hiveAccess.executeQuery(DBAccess.HIVE_TEST_QUERY));
            float elapsed = (System.currentTimeMillis() - start)/(float) 1000;
            System.out.println("Done ("+elapsed+" secs).");
        } catch (Exception e) {
            fail("Failed to execute query: " + DBAccess.HIVE_TEST_QUERY + "; Exception: "  + e.getMessage());
        }
    }

}
