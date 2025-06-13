package uz.pdp.baseAbstractions;

import java.util.List;
import java.util.UUID;

public interface BaseService<E> {
    boolean add(E e);

    void update(UUID id, E e);

    void delete(UUID id);

    List<E> showAll();

    E getById(UUID id);
}
