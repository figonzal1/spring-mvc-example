package guru.springframework.services;

import guru.springframework.domain.DomainObject;

import java.util.List;

public interface CRUDService<T> {
    List<?> listAll();

    T getById(Integer id);

    T saveOrUpdate(T domainObject);

    void delete(Integer id);
}
