package com.grooveshark.util.db;

import java.sql.Types;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.LinkedList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.fail;

import org.apache.log4j.Logger;

public class MysqlAccessTest
{
    public MysqlAccess mysqlAccess = null;

    public static final Logger log = Logger.getLogger(MysqlAccessTest.class);
    public static String MYSQL_TEST_TABLE = "TestTable";
    public static String MYSQL_TEST_QUERY = "select count(*) from TestTable";
    public static String[] MYSQL_LOAD_TEST_VALUES = {
                "2012-12-15	1.4299	29.9366",
                "2012-12-14	1.7842	38.7882",
                "2012-12-13	1.7902	41.1939",
                "2012-12-12	1.7628	40.7379",
                "2012-12-11	1.7605	40.4493",
                "2012-12-10	1.7109	39.1651",
                "2012-12-09	1.3343	28.5789",
                "2012-12-08	1.4189	31.0357",
                "2012-12-07	1.7800	39.7571",
                "2012-12-06	1.7746	40.1025",
                "2012-12-05	1.7695	40.5347",
                "2012-12-04	1.7392	39.2202",
                "2012-12-03	1.6913	36.2728",
                "2012-12-02	1.3507	26.8784",
                "2012-12-01	1.4172	28.6256",
                "2012-11-30	1.7851	37.8371",
                "2012-11-29	1.7677	38.3665",
                "2012-11-28	1.7539	38.5515",
                "2012-11-26	1.6564	36.3669",
                "2012-11-25	1.3111	26.1309",
                "2012-11-24	1.3704	27.9465",
                "2012-11-23	1.5363	33.1854",
                "2012-11-22	1.5274	32.9737",
                "2012-11-21	1.6479	36.2158",
                "2012-11-20	1.7069	36.8516",
                "2012-11-19	1.6620	35.7437",
                "2012-11-18	1.3292	26.5031",
                "2012-11-17	1.4009	28.6328",
                "2012-11-16	1.7568	37.4316",
                "2012-11-15	1.7387	37.7917"
        };

    @Before
    public void setup() {
        try {
            DBProperties dbp = new DBProperties();
            String[] jsonHeader = {"search"};
            dbp.setMysqlDsn(jsonHeader);
            String mysqlUrl = dbp.getMysqlURL();
            String mysqlUser = dbp.getMysqlUser();
            String mysqlPass = dbp.getMysqlPass();
            log.debug("Mysql url: " + mysqlUrl);
            log.debug("Mysql user: " + mysqlUser);
            log.debug("Mysql pass: " + mysqlPass);
            this.mysqlAccess = new MysqlAccess(
                    mysqlUrl,
                    mysqlUser,
                    mysqlPass
                    );
        } catch (Exception e) {
            fail("Failed to setup DB connection" + e.getMessage());
        }

    }

    @Test
    public void executeQueryTest()
    {
        try {
            System.out.println("Running executeQuery test");
            String sql = "select CityID, City, 46.66666 as Latitude, '' as Region, '00' as Country from Cities where City = ? union select CityID, City, 43.333 as Latitude, 'TN' as Region, 'IN' as Country from Cities where City = ?";
            List<String> lis = new LinkedList<String>();
            lis.add("25 de Junho \"A\"");
            lis.add("Bank al Inma' al Almani");
            PreparedStatement ps = this.mysqlAccess.getPreparedStatement(sql, lis, 1);
            boolean resultExists = ps.execute();
            while (resultExists) {
                ResultSet res = ps.getResultSet();
                while (res.next()) {
                    System.out.println("1: " + res.getString(1));
                    System.out.println("2: " + res.getString(2));
                    System.out.println("3: " + res.getString(3));
                    System.out.println("4: " + res.getString(4));
                    System.out.println("5: " + res.getString(5));
                }
                res.close();
                resultExists = ps.getMoreResults();
            }
            ps.close();
        } catch (Exception e) {
            fail("Failed to execute query: " + MYSQL_TEST_QUERY + "; Exception: "  + e.getMessage());
        }

    }

    //Test
    public void inserViaLoadTest() {
        List<String> valuesList = Arrays.asList(MYSQL_LOAD_TEST_VALUES);
        try {
            this.mysqlAccess.insertViaLoad(valuesList, MYSQL_TEST_TABLE);
        } catch (Exception e) {
            fail("Failed to insert via load" + e.getMessage());
        }
    }

}
