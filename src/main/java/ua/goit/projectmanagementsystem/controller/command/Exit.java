package ua.goit.projectmanagementsystem.controller.command;

import ua.goit.projectmanagementsystem.exception.ExitException;
import ua.goit.projectmanagementsystem.view.View;

import static ua.goit.projectmanagementsystem.controller.command.Commands.EXIT;

public class Exit implements Command {
    private final View view;

    public Exit(View view) {
        this.view = view;
    }

    @Override
    public boolean canProccess(String input) {
        return input.equals(EXIT.getName());
    }

    @Override
    public void process() {
        throw new ExitException();
    }
}
