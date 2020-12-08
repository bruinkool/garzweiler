package land.bruinkool.garzweiler.repository;

import land.bruinkool.garzweiler.api.role.RoleEnum;
import land.bruinkool.garzweiler.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleEnum name);
}
