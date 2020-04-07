package com.aoher.util;

public enum Priority {
    HIGH("high"),
    LOW("low");

    private String level;

    Priority(String level) {
        this.level = level;
    }

    public String getLevel() {
        return level;
    }
}
