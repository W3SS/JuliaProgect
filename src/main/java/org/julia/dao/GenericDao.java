package org.julia.dao;

import java.util.List;

/**
 * User: Миша
 * Date: 10.01.15
 */
public interface GenericDao<T> {
    public List<T> findAll();
    public T findById(long id, boolean proxy);
    public T makePersistent(T item);
    public void makeTransient(T item);

}
