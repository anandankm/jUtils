package com.grooveshark.util.db;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapred.lib.db.DBWritable;

public class MysqlWritable implements Writable, DBWritable
{
    // Record Number
    public int recordNumber;
    // delimited column fields
    public String fields = "";

    public void write(DataOutput out)
        throws IOException
    {
        out.writeInt(this.recordNumber);
        out.writeUTF(this.fields);
    }

    public void readFields(DataInput in)
        throws IOException
    {
        this.recordNumber = in.readInt();
        this.fields = in.readUTF();
    }

    public void write(PreparedStatement ps)
        throws SQLException
    {
    }

    public void readFields(ResultSet res)
        throws SQLException
    {
        this.fields = "";
        ResultSetMetaData metaData = res.getMetaData();
        int columnCount = metaData.getColumnCount();
        for (int i = 1; i <= columnCount; i++) {
            this.fields += res.getString(i);
            if (i != columnCount) {
                this.fields += "\t";
            }
        }
    }
}
