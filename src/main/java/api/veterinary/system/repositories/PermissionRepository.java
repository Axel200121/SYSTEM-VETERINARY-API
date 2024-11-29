package api.veterinary.system.repositories;

import api.veterinary.system.entities.Permission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission,String> {
    Page<Permission> findAll(Pageable pageable);
}
