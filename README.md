jUtils
======

Java utilities - DB access (Singleton pattern for mysql, hive, mongo), File, Date general utilities.

Dependencies:

1. Morphia Repository  

    * package: com.google.code.morphia  
        - version: 0.99

2. Cloudera Repositories  

    * package: org.apache.hadoop  
        - name: hadoop-core; version: 2.0.0-mr1-cdh4.1.2  
        - name: hadoop-common; version: 2.0.0-cdh4.1.2  
        - name: hadoop-auth; version: 2.0.0-cdh4.1.2  
        - name: hadoop-annotations; version: 2.0.0-cdh4.1.2  
    * package: org.apache.hive  
        - name: hive-jdbc; version: 0.9.0-cdh4.1.2  


References:

1. Maxmind downloads
    * http://dev.maxmind.com/static/csv/codes/maxmind/region.csv
    * http://geolite.maxmind.com/download/geoip/database/GeoLiteCity_CSV/GeoLiteCity-latest.zip

2. Geonames downloads
    * http://www.geonames.org/
    * http://download.geonames.org/export/dump/

3. Latitude Longitude distance reference:

   * http://www.movable-type.co.uk/scripts/latlong.html
   * Example test output:
      Running com.grooveshark.util.MathUtilsTest

      Gainesville - Jacksonville distance in kms

      Spherical: 99.3965072749399
      Haversine: 99.39650727499613

      Gainesville - Miami distance in kms

      Spherical: 479.4423493165074
      Haversine: 479.442349316516
      Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.133 sec

