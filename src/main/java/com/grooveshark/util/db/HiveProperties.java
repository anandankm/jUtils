package com.grooveshark.util.db;

import com.grooveshark.util.FileUtils;
import com.groovehsark.util.StringUtils;
import com.google.gson.JsonElement;

import org.apache.log4j.Logger;

public class HiveProperties 
{

    public static final Logger log = Logger.getLogger(HiveProperties.class);

    protected String hivePrefix = "/user/hive/warehouse/";
    protected String hiveTable = "";
    protected LinkedList<String> partitionColumns = null;
    protected LinkedList<String> partitionValues = null;
    protected LinkedList<String> partitonTypes = null;
    protected Properties hiveProps = null;

    public HiveProperties() {
    }

    public HiveProperties(Properties props) {
        this.hiveProps = props;
        this.hivePrefix = props.getProperty("HIVE_PREFIX", this.hivePrefix).trim();
        this.hiveTable = props.getProperty("HIVE_TABLE", "").trim();
        this.partitionColumns = StringUtils.splitTrim(props.getProperty("PARTITION_COLUMNS", "").trim());
        this.partitionValues = StringUtils.splitTrim(props.getProperty("PARTITION_VALUES", "").trim());
        this.partitionTypes = StringUtils.splitTrim(props.getProperty("PARTITION_TYPES", "").trim());
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
     * Setter method for partitonTypes
     */
    public void setPartitonTypes(LinkedList<String> partitonTypes) {
        this.partitonTypes = partitonTypes;
    }
    /**
     * Getter method for partitonTypes
     */
    public LinkedList<String> getPartitonTypes() {
        return this.partitonTypes;
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

}
