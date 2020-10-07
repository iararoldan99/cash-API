package ar.com.ada.api.cash.services.base;

import java.util.*;

public interface IService<T> {

    boolean create(T entity);

    T update(T entity);

    T findById(Integer id);

    List<T> findAll();

    boolean delete(T entity);

    long count();

}
