package ar.com.ada.api.cash.services;

import org.springframework.stereotype.Service;

import ar.com.ada.api.cash.entities.User;
import ar.com.ada.api.cash.repos.UserRepository;
import ar.com.ada.api.cash.services.base.GenericService;

@Service
public class UserService extends GenericService<User> {

    private UserRepository repo() {
        return (UserRepository) repository;
    }

    public boolean exists(String email) {

        return (this.repo().findByEmail(email) != null);

    }

    public User create(String email, String firstName, String lastName) {

        User user = new User();
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);

        boolean userExists = exists(email);

        if (!userExists) {

            this.create(user);
            return user;

        } else {

            return null;
        }

    }

}
