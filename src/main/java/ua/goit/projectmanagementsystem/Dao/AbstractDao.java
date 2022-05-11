package ua.goit.projectmanagementsystem.Dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import ua.goit.projectmanagementsystem.config.PersistenceProvider;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

public abstract class AbstractDao<T> implements Dao<T> {

    private final Class<T> entityType;
    protected EntityManager em = PersistenceProvider.getEntityManager();

    public AbstractDao(Class<T> entityType) {
        this.entityType = entityType;
    }

    public AbstractDao()  {
        Type daoType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) daoType).getActualTypeArguments();
        this.entityType = ((Class<T>) params[0]);
    }

    @Override
    public T create(T entity) {
        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();
        return entity;
    }

    @Override
    public void update(T entity) {
        em.getTransaction().begin();
        em.merge(entity);
        em.getTransaction().commit();
    }

    @Override
    public void delete(T entity) {
        em.getTransaction().begin();
        em.remove(em.contains(entity) ? entity : em.merge(entity));
        em.getTransaction().commit();
    }

    @Override
    public List<T> findAll() {
        TypedQuery<T> getAll = em.createQuery("from " + entityType.getSimpleName(), entityType);
        return getAll.getResultList();
    }

    @Override
    public Optional<T> findById(Integer id) {
        em.clear();
        T entity = em.find(entityType, id);
        return Optional.of(entity);
    }
}
