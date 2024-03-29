package repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class GenericDaoJpa<T> implements GenericDao<T> {
    private static final String PU_NAME = "TaijitanPU";
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU_NAME);
    protected static final EntityManager em = emf.createEntityManager();
    private final Class<T> type;

    public GenericDaoJpa(Class<T> type) {
        this.type = type;
    }

    public static void closePersistency() {
        em.close();
        emf.close();
    }

    public void startTransaction() {
        em.getTransaction().begin();
    }

    public void commitTransaction() {
        em.getTransaction().commit();
    }

    public void rollbackTransaction() {
        em.getTransaction().rollback();
    }

    @Override
    public List<T> findAll() {
        return em.createQuery("select entity from " + type.getName() + " entity", type).getResultList();
        /*return em.createNamedQuery(type.getName()+".findAll", type).getResultList();*/
    }

    @Override
    public T get(Long id) {
        T entity = em.find(type, id);
        return entity;
    }

    @Override
    public T update(T object) {
        return em.merge(object);
    }

    @Override
    public void delete(T object) {
        em.remove(em.merge(object));
    }

    @Override
    public void insert(T object) {
        em.persist(object);
    }

    @Override
    public boolean exists(Long id) {
        T entity = em.find(type, id);
        return entity != null;
    }
}