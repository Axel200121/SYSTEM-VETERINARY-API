package api.veterinary.system.repositories;

import api.veterinary.system.entities.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,String> {

    Optional<Role> findByName(String name);
    Page<Role> findAll(Pageable pageable);
}
