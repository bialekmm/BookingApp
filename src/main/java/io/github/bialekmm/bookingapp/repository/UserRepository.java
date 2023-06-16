package io.github.bialekmm.bookingapp.repository;

import io.github.bialekmm.bookingapp.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);
}
