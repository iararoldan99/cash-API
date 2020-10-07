package ar.com.ada.api.cash.services.base;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

public class GenericService<T> implements IService<T> {

    @Autowired
    protected JpaRepository<T, Integer> repository;

    @Override
    public boolean create(T entity) {
        repository.save(entity);
        return true;
    }

    @Override
    public boolean delete(T entity) {
        repository.delete(entity);
        return true;
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public T findById(Integer id) {
        Optional<T> u = repository.findById(id);
        // Elvis operator ?:
        return (u.isPresent() ? u.get() : null);
    }

    @Override
    public T update(T entity) {
        repository.save(entity);
        return entity;
    }

    @Override
    public long count() {
        return repository.count();
    }

}