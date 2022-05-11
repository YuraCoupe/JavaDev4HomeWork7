package ua.goit.projectmanagementsystem.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class PersistenceProvider {

    private static final EntityManagerFactory emf;

    static {
        try {
            emf = Persistence
                    .createEntityManagerFactory("JavaDev4HomeWork7");

        } catch (Throwable t) {
            t.printStackTrace();
            throw t;
        }
    }

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
