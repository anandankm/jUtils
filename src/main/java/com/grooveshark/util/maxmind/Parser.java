package com.grooveshark.util.maxmind;

import com.grooveshark.util.maxmind.entity.LatLong;
import com.grooveshark.util.FileUtils;

import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.text.ParseException;
import java.io.IOException;

public class Parser
{
    private List<String> cityData;
    private List<String> regionData;
    private Set<String> blocksLocId = new HashSet<String>();

    private Map<String, Object> country = new HashMap<String, Object>();
    private Map<String, String> regionDataMap = new HashMap<String, String>();


    public void setRegionFile(String regionFilename)
        throws IOException
    {
        System.out.println("Reading region file");
        this.regionData = FileUtils.readFile(regionFilename);
    }

    public void setCityFile(String cityFilename)
        throws IOException
    {
        System.out.println("Reading city file");
        this.cityData = FileUtils.readFile(cityFilename);
    }

    public void setBlocksFile(String blocksFile)
        throws IOException
    {
        System.out.println("Reading blocks file");
        FileUtils.readIntoSet(blocksFile, this.blocksLocId);
    }

    public void parseCountryRegion()
        throws ParseException
    {
    }

    public void parseCityData()
        throws ParseException
    {
        int linenumber = 0;
        for (int i = 3; i < cityData.size(); i++) {
            if (i%10000 == 0) {
                System.out.println("City data point: " + i);
            }
            linenumber = i + 1;
            String cityInfo = cityData.get(i);
            String[] infoSplit = cityInfo.split(",");

            if (infoSplit.length <= 6) {
                throw new ParseException("City file has less than or equal to 6 comma separated values instead of 9: linenumber - " + linenumber + "; data - " + cityInfo, linenumber);
            }

            /*
            String locId = infoSplit[0].trim();
            if (!this.blocksLocId.contains(locId)) {
                continue;
            }
            */
            String countryCode = infoSplit[1].replaceAll("\"", "").trim();
            if (countryCode.length() != 2) {
                throw new ParseException("City file has country code length != 2: linenumber - " + linenumber + "; data - " + cityInfo, 1);
            }

            /**
             * discard dummy country code
             */
            if (countryCode.equals("01")) {
                continue;
            }

            Map<String, Object> countryMap = this.getOrCreateMap(countryCode, this.country);
            String regionCode = infoSplit[2].replaceAll("\"", "").trim();
            String city = infoSplit[3].replaceAll("\"", "").trim().toLowerCase();
            LatLong latLong = new LatLong();
            latLong.latitude = Float.parseFloat(infoSplit[5].trim());
            latLong.longitude = Float.parseFloat(infoSplit[6].trim());
            /**
             * If Region and City are empty, then take lat and long
             * for the capital
             */
            if (regionCode.length() == 0 && city.length() == 0) {
                countryMap.put("LatLong", latLong);
                continue;
            }
            if (regionCode.length() != 2 && regionCode.length() != 0) {
                throw new ParseException("City file has region code length != 2: linenumber - " + linenumber + "; data - " + cityInfo, 2);
            }
            Map<String, Object> regionsMap = this.getOrCreateMap("Regions", countryMap);
            Map<String, Object> regionMap = this.getOrCreateMap(regionCode, regionsMap);
            /**
             * If City name is empty, then take lat and long
             * for the region
             */
            if (city.length() == 0) {
                regionMap.put("LatLong", latLong);
                continue;
            }
            Map<String, Object> citiesMap = this.getOrCreateMap("Cities", regionMap);
            Map<String, Object> cityMap = this.getOrCreateMap(city, citiesMap);
            String postalString = infoSplit[4].replaceAll("\"", "").trim();
            /**
             * If Postal/Zip string is empty, then take lat and long
             * for the city
             */
            if (postalString.length() == 0) {
                cityMap.put("LatLong", latLong);
                continue;
            }
            Map<String, Object> postalsMap = this.getOrCreateMap("Postals", cityMap);
            Map<String, Object> postalMap = this.getOrCreateMap(postalString, postalsMap);
            postalMap.put("LatLong", latLong);
        }
    }

    public Map<String, Object> getOrCreateMap(String key, Map<String, Object> source)
    {
            Map<String, Object> result = null;
            if (source.containsKey(key)) {
                result = (Map<String, Object>) source.get(key);
            } else {
                result= new HashMap<String, Object>();
                source.put(key, result);
            }
            return result;
    }

    public String getRegionName(String countryCode, String regionCode)
        throws ParseException
    {
        String key = countryCode + ":" + regionCode;
        if (this.regionDataMap.containsKey(key)) {
            return this.regionDataMap.get(key).toLowerCase();
        } else {
            throw new ParseException("Country, Region codes does not exist in region database. CC: " + countryCode + "; RC: " + regionCode, 2);
        }
    }

    public String getRegionName(String countryCode, String regionCode, int linenumber, String cityInfo)
        throws ParseException
    {
        String key = countryCode + ":" + regionCode;
        if (this.regionDataMap.containsKey(key)) {
            return this.regionDataMap.get(key).toLowerCase();
        } else {
            throw new ParseException("City file has invalid country, region codes: linenumber - " + linenumber + "; data - " + cityInfo, linenumber);
        }
    }

    public void parseRegionData()
        throws ParseException
    {
        int linenumber = 0;
        for (String regionInfo : this.regionData) {
            linenumber++;
            String[] infoSplit = regionInfo.split(",");
            if (infoSplit.length <= 2) {
                throw new ParseException("Region file has less than or equal to 2 comma separated values instead of 3: linenumber - " + linenumber + "; data - " + regionInfo, linenumber);
            }
            String cc = infoSplit[0].trim();
            String rc = infoSplit[1].trim();
            String regionName = infoSplit[2].replaceAll("\"", "").trim();
            if (cc.length() <= 0 || rc.length() <= 0 || regionName.length() <= 0) {
                throw new ParseException("Region file has invalid data: linenumber - " + linenumber + "; data - " + regionInfo, linenumber);
            }
            this.regionDataMap.put(cc + ":" + rc, regionName);
        }
    }
}
