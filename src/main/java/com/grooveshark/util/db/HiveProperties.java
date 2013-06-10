package com.grooveshark.util.db;

import java.util.LinkedList;
import java.util.Properties;
import com.grooveshark.util.FileUtils;
import com.grooveshark.util.StringUtils;
import com.google.gson.JsonElement;

import org.apache.log4j.Logger;

public class HiveProperties 
{

    public static final Logger log = Logger.getLogger(HiveProperties.class);

    protected String hivePrefix = "/user/hive/warehouse/";
    protected String hiveTable = "";
    protected LinkedList<String> partitionColumns = null;
    protected LinkedList<String> partitionValues = null;
    protected LinkedList<String> partitionTypes = null;
    protected String outputPath = "";
    protected Properties hiveProps = null;

    public HiveProperties() {
    }

    public HiveProperties(Properties props) {
        this.hiveProps = props;
        this.hivePrefix = props.getProperty("HIVE_PREFIX", this.hivePrefix).trim();
        this.hiveTable = props.getProperty("HIVE_TABLE", "").trim();
        this.partitionColumns = StringUtils.splitTrim(props.getProperty("PARTITION_COLUMNS", "").trim(), ",");
        this.partitionValues = StringUtils.splitTrim(props.getProperty("PARTITION_VALUES", "").trim(), ",");
        this.partitionTypes = StringUtils.splitTrim(props.getProperty("PARTITION_TYPES", "").trim(), ",");
        this.outputPath = props.getProperty("OUTPUT_PATH", "").trim();
    }

    /**
     * Setter method for hiveProps
     */
    public void setHiveProps(Properties hiveProps) {
        this.hiveProps = hiveProps;
    }
    /**
     * Getter method for hiveProps
     */
    public Properties getHiveProps() {
        return this.hiveProps;
    }
    /**
     * Setter method for partitionColumns
     */
    public void setPartitionColumns(LinkedList<String> partitionColumns) {
        this.partitionColumns = partitionColumns;
    }
    /**
     * Getter method for partitionColumns
     */
    public LinkedList<String> getPartitionColumns() {
        return this.partitionColumns;
    }
    /**
     * Setter method for partitionValues
     */
    public void setPartitionValues(LinkedList<String> partitionValues) {
        this.partitionValues = partitionValues;
    }
    /**
     * Getter method for partitionValues
     */
    public LinkedList<String> getPartitionValues() {
        return this.partitionValues;
    }
    /**
     * Setter method for partitionTypes
     */
    public void setPartitionTypes(LinkedList<String> partitionTypes) {
        this.partitionTypes = partitionTypes;
    }
    /**
     * Getter method for partitionTypes
     */
    public LinkedList<String> getPartitionTypes() {
        return this.partitionTypes;
    }
    /**
     * Setter method for hivePrefix
     */
    public void setHivePrefix(String hivePrefix) {
        this.hivePrefix = hivePrefix;
    }
    /**
     * Getter method for hivePrefix
     */
    public String getHivePrefix() {
        return this.hivePrefix;
    }
    /**
     * Setter method for hiveTable
     */
    public void setHiveTable(String hiveTable) {
        this.hiveTable = hiveTable;
    }
    /**
     * Getter method for hiveTable
     */
    public String getHiveTable() {
        return this.hiveTable;
    }
    /**
     * Setter method for outputPath
     */
    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }
    /**
     * Getter method for outputPath
     */
    public String getOutputPath() {
        return this.outputPath;
    }

}
