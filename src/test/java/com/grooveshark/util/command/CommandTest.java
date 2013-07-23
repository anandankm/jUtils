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
        long start = System.currentTimeMillis();
        String[] cmdArray = new String[3];
        cmdArray[0] = "grep" ;
        cmdArray[1] = "chennai";
        cmdArray[2] = cityFilename;
        List<String> output = null;
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
