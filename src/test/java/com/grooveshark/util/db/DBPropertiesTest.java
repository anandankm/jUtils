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

public class DBPropertiesTest 
{

    public static final Logger log = Logger.getLogger(DBPropertiesTest.class);
    @Test
    public void loadDefaultsTest()
    {
        try {
            DBProperties dbp = new DBProperties();
            dbp.loadDefaults();
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed to load defaults. Excpetion:\n"  + e.getMessage());
        }
    }

    @Test
    public void loadMysqlDsnTest()
    {
        try {
            DBProperties dbp = new DBProperties();
            String[] jsonHeader = {"dsn1"};
            dbp.setMysqlDsn(jsonHeader);
            log.debug("Mysql url: " + dbp.getMysqlURL());
            log.debug("Mysql user: " + dbp.getMysqlUser());
            log.debug("Mysql pass: " + dbp.getMysqlPass());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed to load defaults. Excpetion:\n"  + e.getMessage());
        }
    }

}
