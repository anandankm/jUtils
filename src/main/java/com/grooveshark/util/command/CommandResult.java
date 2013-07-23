package com.grooveshark.util.command;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;


public class CommandResult
{

    private String output;
    private List<String> outputList;
    private InputStream inputStream;
    private OutputStream outputStream;

    private int exitValue = -1;

    private Command command;

    public CommandResult()
    {
    }

    public CommandResult(String output)
    {
        this.output = output;
    }

    public CommandResult(List<String> outputList)
    {
        this.outputList = outputList;
    }

    /**
     * Setter method for output
     */
    public void setOutput(String output) {
        this.output = output;
    }
    /**
     * Getter method for output
     */
    public String getOutput() {
        return this.output;
    }
    /**
     * Setter method for outputList
     */
    public void setOutputList(List<String> outputList) {
        this.outputList = outputList;
    }
    /**
     * Getter method for outputList
     */
    public List<String> getOutputList() {
        return this.outputList;
    }
    /**
     * Setter method for command
     */
    public void setCommand(Command command) {
        this.command = command;
    }
    /**
     * Getter method for command
     */
    public Command getCommand() {
        return this.command;
    }
    /**
     * Setter method for exitValue
     */
    public void setExitValue(int exitValue) {
        this.exitValue = exitValue;
    }
    /**
     * Getter method for exitValue
     */
    public int getExitValue() {
        return this.exitValue;
    }
    /**
     * Setter method for inputStream
     */
    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }
    /**
     * Getter method for inputStream
     */
    public InputStream getInputStream() {
        return this.inputStream;
    }
    /**
     * Setter method for outputStream
     */
    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }
    /**
     * Getter method for outputStream
     */
    public OutputStream getOutputStream() {
        return this.outputStream;
    }
}
