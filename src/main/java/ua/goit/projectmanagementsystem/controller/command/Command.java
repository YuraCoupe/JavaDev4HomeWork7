package ua.goit.projectmanagementsystem.controller.command;

public interface Command {

    boolean canProccess (String input);

    void process();
}
