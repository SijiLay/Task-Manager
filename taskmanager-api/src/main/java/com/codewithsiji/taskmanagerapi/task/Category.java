package com.codewithsiji.taskmanagerapi.task;

public enum Category {
    SCHOOL, WORK, PERSONAL, FITNESS, CHURCH, OTHER;

    public static Category fromString(String category){
        return switch (category.trim().toUpperCase()){
            case "SCHOOL"-> SCHOOL;
            case "WORK"->WORK;
            case "PERSONAL"->PERSONAL;
            case "FITNESS"->FITNESS;
            case "CHURCH"->CHURCH;
            case "OTHER"->OTHER;
            default -> throw new IllegalArgumentException("Invalid category value: "+category);
        };
    }
}
