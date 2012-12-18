package com.grooveshark.util.db;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import org.apache.hadoop.hive.jdbc.HiveDriver;

public class HiveAccess extends DBAccess
{
    public static final Logger log = Logger.getLogger(DBAccess.class);

    public HiveAccess()
        throws SQLException
    {
        super("org.apache.hadoop.hive.jdbc.HiveDriver", DBAccess.HIVE_URL, "", "", false);
    }

    public static void main(String[] args)
    {
        try {
            HiveAccess hiveAccess = new HiveAccess();
            long start = System.currentTimeMillis();
            System.out.println("Query result: " + hiveAccess.executeUpdate(DBAccess.HIVE_TEST_QUERY));
            float elapsed = (System.currentTimeMillis() - start)/(float) 1000;
            System.out.println("Done ("+elapsed+" secs).");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

    }
}
