package com.grooveshark.util.command;

import java.io.IOException;


public class Command
{
    private String cmd;
    private String[] args;
    private String[] cmdArray;
    private String cmdString;

    public Command() {
        this.cmd = "";
        this.args = new String[1];
    }

    public Command(String cmd, String[] args) {
        this.cmd = cmd;
        this.args = args;
    }

    public Command(String[] cmdArray) {
        this.setCmdArray(cmdArray);
    }


    public CommandResult execute()
        throws CommandExecutionException
    {
        if (this.cmd.equals("")) {
            throw new CommandExecutionException("Command is not set yet");
        }
        if (this.cmdArray == null) {
            int cmdLen = 1;
            if (this.args != null) {
                cmdLen += this.args.length;
            }
            this.cmdArray = new String[cmdLen];
            this.cmdArray[0] = this.cmd;
            for (int i = 0; i < this.args.length; i++) {
                this.cmdString += this.args[i];
                this.cmdArray[i+1] = this.args[i];
            }
        }
        Process proc = this.getExecutedProcess();

        CommandResult result = new CommandResult();
        result.setInputStream(proc.getInputStream());
        result.setOutputStream(proc.getOutputStream());
        result.setExitValue(proc.exitValue());
        return result;
    }

    public Process getExecutedProcess()
        throws CommandExecutionException
    {
        if (this.cmdArray == null || this.cmdArray.length < 1) {
            throw new CommandExecutionException("Command array is not set");
        }
        Runtime runtime = Runtime.getRuntime();
        Process proc = null;
        try {
            proc = runtime.exec(this.cmdArray);
        } catch (IOException e) {
            throw new CommandExecutionException("Error executing command: " + this.toString(), e);
        }

        try {
            proc.waitFor();
        } catch (InterruptedException e) {
            throw new CommandExecutionException("Error while executing and waiting for command: " + this.toString(), e);
        }
        return proc;
    }

    public String toString() {
        if (this.cmdString == null || this.cmdString.isEmpty()) {
            if (this.cmdArray != null) {
                this.cmdString = "";
                for (String arg : this.cmdArray) {
                    this.cmdString += arg;
                }
            }
        }
        return this.cmdString;
    }

    /**
     * Setter method for cmd
     */
    public void setCmd(String cmd) {
        this.cmd = cmd;
    }
    /**
     * Getter method for cmd
     */
    public String getCmd() {
        return this.cmd;
    }
    /**
     * Setter method for args
     */
    public void setArgs(String[] args) {
        this.args = args;
    }
    /**
     * Getter method for args
     */
    public String[] getArgs() {
        return this.args;
    }
    /**
     * Setter method for cmdArray
     */
    public void setCmdArray(String[] cmdArray) {
        if (cmdArray.length > 0) {
            this.cmd = cmdArray[0];
        }
        this.cmdArray = cmdArray;
    }
    /**
     * Getter method for cmdArray
     */
    public String[] getCmdArray() {
        return this.cmdArray;
    }
}
