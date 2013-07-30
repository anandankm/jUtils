package com.grooveshark.util;


public class MathUtils 
{
    /**
     * Earth's Radius in km
     */
    public static final int R = 6371;

    /**
     * Spherical Law of Cosines:
     * Find distance in km between 2 latitude-longitude co-ordinates
     * latitudes and longitudes as input are in decimal degrees wgs84.
     *
     * d = acos ( sin(lat1)*sin(lat2) + cos(lat1)*cos(lat2)*cos(long2-long1) ) * R
     *
     * Reference: http://www.movable-type.co.uk/scripts/latlong.html
     */
    public static double sphericalDistance(float latitude1, float longitude1, float latitude2, float longitude2)
    {
        double lat1 = Math.toRadians(latitude1);
        double lat2 = Math.toRadians(latitude2);
        double long1 = Math.toRadians(longitude1);
        double long2 = Math.toRadians(longitude2);
        return Math.acos(
                (Math.sin(lat1) * Math.sin(lat2)) +
                (Math.cos(lat1) * Math.cos(lat2) * Math.cos(long2-long1))
                ) * MathUtils.R;
    }

    /**
     * Haversine formula:
     *
     * Find distance in km between 2 latitude-longitude co-ordinates
     * latitudes and longitudes as input are in decimal degrees wgs84.
     *
     * dlong = (long2 - long1)
     * dlat = (lat2 - lat1)
     * a = ( sin(dlat/2) )^2 + cos(lat1)*cos(lat2)*( sin(dlong/2) )^2
     * c = 2 * atan2(sqrt(a), sqrt(1-a))
     * d = c * R
     *
     * Reference: http://www.movable-type.co.uk/scripts/latlong.html
     */
    public static double haversineDistance(float latitude1, float longitude1, float latitude2, float longitude2)
    {
        double lat1 = Math.toRadians(latitude1);
        double lat2 = Math.toRadians(latitude2);
        double dlat = Math.toRadians(latitude2 - latitude1);
        double dlong = Math.toRadians(longitude2 - longitude1);
        double a = ( Math.sin(dlat/2) * Math.sin(dlat/2) ) +
                   ( Math.sin(dlong/2) * Math.sin(dlong/2) * Math.cos(lat1) * Math.cos(lat2) );
        double c = 2 * Math.atan2( Math.sqrt(a), Math.sqrt(1 - a) );

        return c * MathUtils.R;
    }

    public static int compareDouble(double i1, double i2)
    {
        if (i1 > i2) {
            return 1;
        } else if (i1 < i2) {
            return -1;
        } else {
            return 0;
        }
    }

    public static int compareFloat(float i1, float i2)
    {
        if (i1 > i2) {
            return 1;
        } else if (i1 < i2) {
            return -1;
        } else {
            return 0;
        }
    }

    public static int compareInt(int i1, int i2)
    {
        if (i1 > i2) {
            return 1;
        } else if (i1 < i2) {
            return -1;
        } else {
            return 0;
        }
    }

}
