package ua.goit.projectmanagementsystem.repository;

import ua.goit.projectmanagementsystem.config.DatabaseManager;

public class SkillRepository {

    private final DatabaseManager databaseManager;

    public SkillRepository(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }
}
