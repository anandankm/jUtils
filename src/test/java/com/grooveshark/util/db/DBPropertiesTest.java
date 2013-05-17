package com.grooveshark.util.db;

import java.sql.Types;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.fail;


public class DBPropertiesTest 
{

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

}
