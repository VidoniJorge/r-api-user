package ar.com.jlv.api.user.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ar.com.jlv.api.user.entities.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, String> {
	List<UserEntity> findAll();
	Optional<UserEntity> findByEmailIgnoreCase(final String email);
}
