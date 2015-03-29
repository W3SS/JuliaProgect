package org.julia.dao.jpa;

import org.julia.dao.GenericDao;
import org.julia.domain.BusinessEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * User: Миша
 * Date: 10.01.15
 */
public abstract class AbstractGenericDaoJPA<T extends BusinessEntity> implements GenericDao<T> {
    private final static Logger log = LoggerFactory.getLogger(AbstractGenericDaoJPA.class);

    private Class<T> persistenceClass;

    @PersistenceContext
    protected EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public AbstractGenericDaoJPA(){
        this.persistenceClass = (Class<T>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public List<T> findAll() {
        CriteriaQuery<T> query = entityManager.getCriteriaBuilder().createQuery(persistenceClass);
        query.from(persistenceClass);
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public T findById(long id, boolean proxy) {
        return proxy ? entityManager.getReference(persistenceClass, id)  :entityManager.find(persistenceClass, id);
    }

    @Override
    public T makePersistent(T item) {
        if(item.getId()!=0){
            item = entityManager.merge(item);
        }else{
            entityManager.persist(item);
        }
        return item;
    }

    @Override
    public void makeTransient(T item){
        entityManager.remove(item);
    }

}
