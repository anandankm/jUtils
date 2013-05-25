package com.grooveshark.util;

import java.util.List;
import java.util.Set;
import java.util.LinkedList;
import java.util.TreeMap;
import java.io.File;
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

    public static List<String> readFile(String filename) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        List<String> lines = new LinkedList<String>();
        String line = "";
        while ((line = br.readLine()) != null) {
            lines.add(line);
        }
        return lines;
    }

    public static List<String> readFile(String filename, int numLines) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        List<String> lines = new LinkedList<String>();
        String line = "";
        int count = 1;
        while ((line = br.readLine()) != null && count <= numLines) {
            lines.add(line);
            count++;
        }
        return lines;
    }

    public static void writeLine(BufferedWriter writer, String s) throws IOException {
        writer.write(s);
        writer.newLine();
    }

    public static void writeLine(String filename, String s, boolean append) throws Exception {
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

    public static boolean isFileExists(String filename) {
        if (filename == null) return false;
        File f = new File(filename);
        if (!f.exists()) {
            InputStream is = FileUtils.class.getClassLoader().getResourceAsStream(filename);
            if (is == null) return false;
            return true;
        } else {
            return false;
        }
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
}
