package io.github.bialekmm.bookingapp.repository;

import io.github.bialekmm.bookingapp.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    RoleEntity findByName(String name);
}
