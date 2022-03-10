package ua.goit.projectmanagementsystem.controller.command;

public class FindDevsByProject implements Command{
    @Override
    public boolean canProccess(String input) {
        return false;
    }

    @Override
    public void process() {

    }
}
