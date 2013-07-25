package com.grooveshark.util.command;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.fail;
import java.util.List;

import org.apache.log4j.Logger;

public class CommandTest 
{

    public static final Logger log = Logger.getLogger(CommandTest.class);

    public static final String cityFilename = "data/GeoLiteCity_latest/allCountries.txt";

    private CommandExecutor executor = null;

    @Before 
    public void setup()
    {
        this.executor = new CommandExecutor();
    }

    @Test
    public void testCommandExecutor()
    {
        String[] cmdArray = new String[3];
        cmdArray[0] = "wc";
        cmdArray[1] = "-l";
        cmdArray[2] = "./src/main/resources/dsn/allCountries.txt";
        //this.testCmdArray(cmdArray);
        cmdArray = new String[4];
        cmdArray[0] = "/bin/sh";
        cmdArray[1] = "wc";
        cmdArray[2] = "-l";
        cmdArray[3] = "hive-table.properties";
        //this.testCmdArray(cmdArray);
    }

    public void testCmdArray(String[] cmdArray)
    {
        List<String> output = null;
        long start = System.currentTimeMillis();
        try {
            output = this.executor.execute(cmdArray);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed to execute command. Excpetion:\n"  + e.getMessage());
        }
        float elapsed = (System.currentTimeMillis() - start)/(float) 1000;
        System.out.println("Done ["+elapsed+" secs].");
        System.out.println("Output: " + output);
    }
}
