package com.grooveshark.util;

import org.junit.Test;
import static org.junit.Assert.fail;
import com.grooveshark.util.command.Command;

import org.apache.log4j.Logger;

public class StringUtilsTest 
{

    public static final Logger log = Logger.getLogger(StringUtilsTest.class);

    @Test
    public void testFields()
    {
        try {
            System.out.println("Field names:");
            System.out.println(StringUtils.getFieldNames(Command.class));
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed on field test. Excpetion:\n"  + e.getMessage());
        }
    }

}
