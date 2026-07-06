public enum Category {
    SCHOOL, WORK, PERSONAL, FITNESS, CHURCH, OTHER;

    public static Category fromString(String priority){
        return switch (priority){
            case "SCHOOL"-> SCHOOL;
            case "WORK"->WORK;
            case "PERSONAL"->PERSONAL;
            case "FITNESS"->FITNESS;
            case "CHURCH"->CHURCH;
            default -> OTHER;
        };
    }
}
