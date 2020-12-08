package land.bruinkool.garzweiler.api.role;

import land.bruinkool.garzweiler.entity.Role;
import land.bruinkool.garzweiler.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {
    private final RoleRepository repository;

    @Autowired
    public RoleService(RoleRepository repository) {
        this.repository = repository;
    }

    public Role getRole(RoleEnum roleEnum) {
        Optional<Role> roleOptional = repository.findByName(roleEnum);

        if (roleOptional.isPresent()) {
            return roleOptional.get();
        }

        Role role = new Role(roleEnum);
        repository.save(role);

        return role;
    }
}
