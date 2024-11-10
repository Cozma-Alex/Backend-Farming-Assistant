package com.proiect.colectiv.server.Persistence;

import com.proiect.colectiv.server.Models.User;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RepositoryUser extends JpaRepository<User, UUID> {

    @Query(
            nativeQuery = true,
            value = """
            SELECT * FROM users u 
            WHERE u.email = :email 
            AND u.password_hash = crypt(:password, u.password_hash)
        """
    )
    Optional<User> findByEmailAndPassword(String email, String password);

    @Override
    @Transactional
    @Query(nativeQuery = true, value = """
        INSERT INTO users (id, email, password_hash)
        VALUES (gen_random_uuid(), :#{#user.email}, crypt(:#{#user.password_hash}, gen_salt('bf')))
        returning *
    """)
    User save(User user);
}
