package org.julia.dao.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.julia.dao.GenericDao;
import org.julia.domain.BusinessEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * User: Миша
 * Date: 05.02.15
 */
public abstract class AbstractGenericDaoHibernate <T extends BusinessEntity> implements GenericDao<T> {

    private Class<T> persistenceClass;

    @SuppressWarnings("unchecked")
    public AbstractGenericDaoHibernate(){
        this.persistenceClass = (Class<T>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
    @Autowired
    protected SessionFactory sessionFactory;

    @Override
    public List<T> findAll() {
        Session session = sessionFactory.getCurrentSession();
        session.createCriteria(persistenceClass).list();
        return session.createCriteria(persistenceClass).list();
    }

    @Override
    public T findById(long id, boolean proxy) {
        Session session = sessionFactory.getCurrentSession();
        return proxy ? (T) session.load(persistenceClass, id) : (T) session.get(persistenceClass, id);
    }

    @Override
    public T makePersistent(T item) {
        Session session = sessionFactory.getCurrentSession();
        if (item.getId() != 0){
            session.merge(item);
        } else{
            session.saveOrUpdate(item);
        }

        return item;
    }

    @Override
    public void makeTransient(T item) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(item);
    }
}
