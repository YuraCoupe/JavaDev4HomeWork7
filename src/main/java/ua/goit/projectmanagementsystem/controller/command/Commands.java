package ua.goit.projectmanagementsystem.controller.command;

public enum Commands {
    GET_SALARY_SUM_BY_PROJECT_ID("getsalarysum"),
    FIND_DEVELOPERS_BY_PROJECT_ID("finddevsbyproject"),
    FIND_JAVA_DEVELOPERS("findjavadevs"),
    FIND_MIDDLE_DEVELOPERS("findmiddledevelopers"),
    FIND_ALL_PROJECTS("findallprojects"),
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
