package ua.goit.projectmanagementsystem.controller.command;

public interface Command {

    boolean canProcess(String input);

    void process();
}
