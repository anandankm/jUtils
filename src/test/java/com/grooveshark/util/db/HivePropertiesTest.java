package com.grooveshark.util.db;

import java.sql.Types;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.List;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.fail;

import com.grooveshark.util.FileUtils;
import org.apache.log4j.Logger;

public class HivePropertiesTest 
{

    public static final Logger log = Logger.getLogger(HivePropertiesTest.class);
    public HiveProperties hiveProperties = null;
    public static String propertiesFile = "hive-table.properties";

    @Test
    public void loadProperties()
    {
        try {
            Properties props = FileUtils.getProperties(this.propertiesFile);
            this.hiveProperties = new HiveProperties(props);
            log.debug("HIVE_PREFIX = "+ this.hiveProperties.getHivePrefix());
            log.debug("HIVE_TABLE = "+ this.hiveProperties.getHiveTable());
            log.debug("PARTITION_COLUMNS = "+ this.hiveProperties.getPartitionColumns());
            log.debug("PARTITION_VALUES = "+ this.hiveProperties.getPartitionValues());
            log.debug("PARTITION_TYPES = "+ this.hiveProperties.getPartitionTypes());
            log.debug("OUTPUT_PATH = "+ this.hiveProperties.getOutputPath());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed to load defaults. Excpetion:\n"  + e.getMessage());
        }
    }

}
