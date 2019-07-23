package com.practice.lcn.linux_quick_ref;

import java.util.List;

public class Command {
    private String name;
    private String summary;
    private List<CommandExample> examples;
    private List<String> tips;
    private List<String> relatedCommands;

    public String getName() {
        return name;
    }

    public String getSummary() {
        return summary;
    }

    public List<CommandExample> getExamples() {
        return examples;
    }

    public List<String> getTips() {
        return tips;
    }

    public List<String> getRelatedCommands() {
        return relatedCommands;
    }
}
