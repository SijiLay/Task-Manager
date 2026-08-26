package com.codewithsiji.taskmanagerapi.task;

public enum Priority {
    LOW(3), MEDIUM(2), HIGH(1);

    private final int rank;
    Priority(int rank) {
        this.rank=rank;
    }

    public static Priority fromString(String priority){
        return switch (priority.trim().toUpperCase()) {
            case "HIGH" -> HIGH;
            case "MEDIUM" -> MEDIUM;
            case "LOW" -> LOW;
            default -> throw new IllegalArgumentException("Invalid priority value: "+priority);
        };
    }

    public int getRank() {
        return rank;
    }
}
