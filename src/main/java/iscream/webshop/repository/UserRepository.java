package iscream.webshop.repository;

import iscream.webshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Modifying
    @Transactional
    @Query("update User user set user.password = ?1, user.default_pass = ?2 where user.id = ?3")
    void updatePassword(String password, boolean defaultpass, Long id);
}
