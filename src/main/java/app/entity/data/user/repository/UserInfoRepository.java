package app.entity.data.user.repository;

import app.entity.data.user.domain.Info;
import org.springframework.data.repository.CrudRepository;

public interface UserInfoRepository extends CrudRepository<Info, Integer> {
}
