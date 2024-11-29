package api.veterinary.system.repositories;

import api.veterinary.system.entities.Specialty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpecialtyRepository extends JpaRepository<Specialty,String> {

    Optional<Specialty> findByName(String name);
}
