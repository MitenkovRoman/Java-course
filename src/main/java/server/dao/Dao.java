package server.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {

    List<T> getAll();

    List<T> getAllWhere(String... hqlConditions);

    void insert(T t);

    default Optional<T> findById(int id) {
        return Optional.ofNullable(
                getAllWhere("id=" + id).get(0)
        );
    }

}
