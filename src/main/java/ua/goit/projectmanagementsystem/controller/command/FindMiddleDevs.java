package ua.goit.projectmanagementsystem.controller.command;

public class FindMiddleDevs implements Command{
    @Override
    public boolean canProccess(String input) {
        return false;
    }

    @Override
    public void process() {

    }
}
