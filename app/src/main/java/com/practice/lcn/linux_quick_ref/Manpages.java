package com.practice.lcn.linux_quick_ref;

import java.util.List;

public class Manpages {
    private List<Command> commands;

    public Manpages(List<Command> commands) {
        this.commands = commands;
    }

    public Command find(String cmdName) {
        for (Command command : commands) {
            if (command.getName().equals(cmdName))
                return command;
        }
        return null;
    }
}
