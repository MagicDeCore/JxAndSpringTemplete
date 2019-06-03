package app.entity.data.user.repository;

import app.entity.data.user.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {
    List<User> findAllByNameContains(String name);
    List<User> findAllByCodeLike(String code);
}
