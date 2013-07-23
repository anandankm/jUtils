package com.grooveshark.util;

import java.util.List;
import java.util.Set;
import java.util.LinkedList;
import java.util.TreeMap;
import java.util.HashMap;
import java.util.Properties;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.mapred.JobConf;

public class FileUtils
{

    public static List<String> readFile(String filename) throws IOException {
        BufferedReader br = FileUtils.getReader(filename);
        List<String> lines = new LinkedList<String>();
        String line = "";
        while ((line = br.readLine()) != null) {
            lines.add(line);
        }
        return lines;
    }

    public static List<String> readFile(String filename, int numLines) throws IOException {
        BufferedReader br = FileUtils.getReader(filename);
        List<String> lines = new LinkedList<String>();
        String line = "";
        int count = 1;
        while ((line = br.readLine()) != null && count <= numLines) {
            lines.add(line);
            count++;
        }
        return lines;
    }

    public static List<String> readInput(InputStream is) throws IOException {
        BufferedReader br = FileUtils.getReader(is);
        List<String> lines = new LinkedList<String>();
        String line = "";
        while ((line = br.readLine()) != null) {
            line = line.trim();
            if (!line.isEmpty()) {
                lines.add(line);
            }
        }
        return lines;
    }

    public static void readIntoSet(String filename, Set<String> stringSet) throws IOException {
        BufferedReader br = FileUtils.getReader(filename);
        String line = "";
        int count = 1;
        while ((line = br.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) {
                continue;
            }
            stringSet.add(line);
            count++;
        }
    }

    public static BufferedReader getReader(InputStream is) throws IOException {
        return new BufferedReader(new InputStreamReader(is));
    }

    public static BufferedReader getReader(String filename) throws IOException {
        return new BufferedReader(new InputStreamReader(FileUtils.getInputStream(filename)));
    }

    public static BufferedWriter getWriter(String filename) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        return writer;
    }

    public static void writeLine(BufferedWriter writer, String s) throws IOException {
        writer.write(s);
        writer.newLine();
        writer.flush();
    }

    public static void writeLine(String filename, String s, boolean append) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename, append));
        writer.write(s);
        writer.newLine();
        writer.flush();
        writer.close();
    }

    public static boolean isHDFSFileExists(Path path, JobConf conf) throws IOException {
        FileSystem fs = path.getFileSystem(conf);
        if (fs.exists(path)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean deleteHDFSFile(Path path, JobConf conf) throws IOException {
        FileSystem fs = path.getFileSystem(conf);
        if (fs.delete(path, true)) {
            return true;
        } else {
            return false;
        }
    }

    public static InputStream getInputStream(String filename) {
        InputStream is = null;
        if (filename == null) return is;
        File f = new File(filename);
        if (!f.exists()) {
            is = FileUtils.class.getClassLoader().getResourceAsStream(filename);
        } else {
            try {
                is = new FileInputStream(f);
            } catch (FileNotFoundException e) {
                return null;
            }
        }
        return is;
    }

    public static BufferedReader getReaderFromResource(String filename) throws Exception {
        InputStream is = FileUtils.class.getClassLoader().getResourceAsStream(filename);
        InputStreamReader isr = new InputStreamReader(is);
        return new BufferedReader(isr);
    }

    public static JsonElement parseJson(BufferedReader br, String[] jsonPath) throws Exception {
        Gson gson = new Gson();
        TreeMap file = gson.fromJson(br, TreeMap.class);
        JsonObject jo = gson.toJsonTree(file).getAsJsonObject();
        JsonElement je = null;
        for (int i = 0; i < jsonPath.length; i++) {
            String p = jsonPath[i];
            if (jo.has(p)) {
                if (i == jsonPath.length - 1) {
                    je = jo.get(p);
                    break;
                }
                jo = jo.getAsJsonObject(p);
            } else {
                throw new Exception("Json file does not contain the string '" + p + "' specified in the path");
            }
        }
        return je;
    }

    public static HashMap getJsonMap(String filename, String[] header, String[] keys) throws Exception
    {
        JsonElement je = FileUtils.parseJson(filename, header);
        HashMap<String, String> jsonMap = new HashMap<String, String>();
        for (String key : keys) {
            jsonMap.put(key, FileUtils.getJsonValue(je, key));
        }
        return jsonMap;
    }

    public static String getJsonValue(JsonElement je, String key)
    {
        JsonElement resultJe = null;
        if (je.isJsonObject()) {
            JsonObject jo = je.getAsJsonObject();
            if (jo.has(key)) {
                resultJe = jo.get(key);
            }
        }
        if (resultJe == null) {
            return "";
        }
        if (resultJe.isJsonObject()) {
            return resultJe.toString();
        }
        return resultJe.getAsString();
    }

    public static JsonElement parseJson(String filename, String[] jsonPath) throws Exception {
        BufferedReader br = null;
        File f = new File(filename);
        if (!f.exists()) {
            br = FileUtils.getReaderFromResource(filename);
        } else {
            br = new BufferedReader( new FileReader(filename) );
        }
        return FileUtils.parseJson(br, jsonPath);
    }

    public static Properties getProperties(String filename) throws IOException {
        Properties props = new Properties();
        InputStream is = FileUtils.getInputStream(filename);
        if (is != null) {
            props.load(is);
        }
        return props;
    }
}
