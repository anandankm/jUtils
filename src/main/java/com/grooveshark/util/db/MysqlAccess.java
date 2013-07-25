package com.grooveshark.util.db;

import java.util.List;
import java.util.Arrays;
import java.sql.Types;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import com.mysql.jdbc.Statement;
import org.apache.log4j.Logger;

public class MysqlAccess extends DBAccess
{

    public static final Logger log = Logger.getLogger(MysqlAccess.class);

    public MysqlAccess()
        throws SQLException
    {
        super(DBProperties.DEFAULT_MYSQL_DRIVER,
               DBProperties.DEFAULT_MYSQL_URL,
               DBProperties.DEFAULT_MYSQL_USER,
               DBProperties.DEFAULT_MYSQL_PASS);
    }

    public MysqlAccess(String url, String user, String pass)
        throws SQLException
    {
        super(DBProperties.DEFAULT_MYSQL_DRIVER, url, user, pass);
    }

    public void insertValues(List<String> values, String table)
        throws SQLException
    {
        boolean[] stringTypes = this.findStringTypes(table);
        StringBuffer sql = new StringBuffer("INSERT INTO " + table + " VALUES ");
        int numRows = values.size();
        for (String row : values) {
            String[] columns = row.split("\t");
            if (columns.length != stringTypes.length) {
                throw new SQLException("Row (" + row + ") to be inserted does not match number of columns");
            }
            sql.append("(");
            for (int i = 0; i < columns.length; i++) {
                // Quote the string type values
                if (stringTypes[i]) {
                    columns[i] = "'" + columns[i] + "'";
                }
                sql.append(columns[i] + ",");
            }
            sql.setCharAt(sql.length() - 1, ')');
            sql.append(",");
        }
        if (values.size() > 0) {
            sql.setCharAt(sql.length() - 1, ';');
        }
    }

    public void insertViaLoad(List<String> values, String table)
        throws SQLException
    {
        StringBuilder strBuilder = new StringBuilder();
        for (String value : values) {
            strBuilder.append(value);
            strBuilder.append("\n");
        }
        ByteArrayInputStream bis = new ByteArrayInputStream(strBuilder.toString().getBytes());
        this.insertViaLoad(bis, table);
    }

    public void insertViaLoad(InputStream inputStream, String table)
        throws SQLException
    {
        this.makeConnection();
        Statement statement = (com.mysql.jdbc.Statement) this.connection.createStatement();
        String sql = "LOAD DATA LOCAL INFILE 'file.txt' INTO TABLE " + table + ";";
        statement.setLocalInfileInputStream(inputStream);
        statement.executeUpdate(sql);
    }

    public void loadDataLocal(String filename, String table, String setString)
        throws SQLException
    {
        String sql = "LOAD DATA LOCAL INFILE '" + filename + "' INTO TABLE " + table + setString + ";";
        this.executeUpdate(sql);
    }

    public PreparedStatement getPreparedStatement(String sql, List<String> values, int fetchSize)
        throws SQLException
    {
        this.makeConnection();
        PreparedStatement ps = this.connection.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, ResultSet.CLOSE_CURSORS_AT_COMMIT);
        for (int i = 0; i < values.size(); i++) {
            ps.setString(i+1, values.get(i));
        }
        ps.setFetchSize(fetchSize);
        return ps;
    }

}
