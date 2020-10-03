package ar.com.ada.api.cash.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.com.ada.api.cash.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    public User findByEmail(String email);

}
