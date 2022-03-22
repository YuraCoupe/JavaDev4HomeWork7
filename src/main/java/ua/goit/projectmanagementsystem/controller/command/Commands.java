package ua.goit.projectmanagementsystem.controller.command;

public enum Commands {
    ADD_COMPANY("1"),
    DELETE_COMPANY("2"),
    UPDATE_COMPANY("3"),
    FIND_COMPANY_BY_NAME("4"),
    GET_SALARY_SUM_BY_PROJECT_ID("5"),
    FIND_DEVELOPERS_BY_PROJECT_ID("6"),
    FIND_JAVA_DEVELOPERS("7"),
    FIND_MIDDLE_DEVELOPERS("8"),
    FIND_ALL_PROJECTS("9"),
    EXIT("exit"),
    HELP("help");

    private String name;

    Commands(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    }
