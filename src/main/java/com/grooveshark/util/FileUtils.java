package com.grooveshark.util;

import java.util.List;
import java.util.Set;
import java.util.LinkedList;
import java.util.TreeMap;
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

    public static String parseJson(String filename, String[] jsonPath) throws Exception {
        Gson gson = new Gson();
        BufferedReader br = new BufferedReader( new FileReader(filename) );
        TreeMap file = gson.fromJson(br, TreeMap.class);
        JsonObject jo = gson.toJsonTree(file).getAsJsonObject();
        JsonElement jp = null;
        for (int i = 0; i < jsonPath.length; i++) {
            String p = jsonPath[i];
            if (jo.has(p)) {
                if (i == jsonPath.length - 1) {
                    jp = jo.get(p);
                    break;
                }
                jo = jo.getAsJsonObject(p);
            } else {
                throw new Exception("Json file does not contain the string '" + p + "' specified in the path");
            }
        }
        if (jp.isJsonObject()) {
            return jp.toString();
        }
        return jp.getAsString();
    }
}
