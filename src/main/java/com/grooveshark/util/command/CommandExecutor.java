package com.grooveshark.util.command;

import com.grooveshark.util.FileUtils;

import java.util.List;
import java.io.IOException;


public class CommandExecutor 
{

    private Command command;

    public CommandExecutor()
    {
    }

    public CommandExecutor(Command command)
    {
        this.command = command;
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

    public CommandResult execute()
        throws CommandExecutionException
    {
        CommandResult result = this.command.execute();
        try {
            result.setOutputList(FileUtils.readInput(result.getInputStream()));
        } catch (IOException e) {
            throw new CommandExecutionException("Error while retrieving output after command execution.", e);
        }
        return result;
    }

    /**
     * LightWeight execution
     */
    public List<String> execute(String[] cmdArray)
        throws CommandExecutionException
    {
        this.command = new Command(cmdArray);
        Process proc = this.command.getExecutedProcess();
        try {
            return FileUtils.readInput(proc.getInputStream());
        } catch (IOException e) {
            throw new CommandExecutionException("Cannot read stdout from the process", e);
        }
    }
}
