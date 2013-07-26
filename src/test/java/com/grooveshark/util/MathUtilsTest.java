package com.grooveshark.util;

import org.junit.Test;
import static org.junit.Assert.fail;

import org.apache.log4j.Logger;

public class MathUtilsTest 
{

    public static final Logger log = Logger.getLogger(MathUtilsTest.class);

    @Test
    public void testDistance()
    {
        try {
            // Gainesville
            Float gainLat = 29.65163f;
            Float gainLong = -82.32483f;
            // Jacksonville
            Float jackLat = 30.33218f;
            Float jackLong = -81.65565f;
            // Miami
            Float miamiLat = 25.77427f;
            Float miamiLong = -80.19366f;
            System.out.println("Gainesville - Jacksonville distance in km");
            System.out.println("");
            System.out.println("Spherical: " + MathUtils.sphericalDistance(gainLat, gainLong, jackLat, jackLong));
            System.out.println("Haversine: " + MathUtils.haversineDistance(gainLat, gainLong, jackLat, jackLong));
            System.out.println("");
            System.out.println("Gainesville - Miami distance in km");
            System.out.println("");
            System.out.println("Spherical: " + MathUtils.sphericalDistance(gainLat, gainLong, miamiLat, miamiLong));
            System.out.println("Haversine: " + MathUtils.haversineDistance(gainLat, gainLong, miamiLat, miamiLong));
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed on distance test. Excpetion:\n"  + e.getMessage());
        }
    }

}
