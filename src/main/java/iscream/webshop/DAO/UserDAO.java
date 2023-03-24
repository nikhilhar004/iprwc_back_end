package iscream.webshop.DAO;

import iscream.webshop.model.User;
import iscream.webshop.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserDAO {
    private final UserRepository userRepository;

    public UserDAO(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User updatePassword(Long id, User newUser) {
        userRepository.updatePassword(newUser.getPassword(),  id);
        return newUser;

    }
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
}
