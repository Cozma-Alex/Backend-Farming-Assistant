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
                    """)
    Optional<User> findByEmailAndPassword(String email, String password);

    @Override
    @Transactional
    @Query(nativeQuery = true, value = """
                INSERT INTO users (id, email, password_hash, farm_name, name)
                VALUES (gen_random_uuid(), :#{#user.email}, crypt(:#{#user.passwordHash}, gen_salt('bf')), :#{#user.farmName}, :#{#user.name})
                returning *
            """)
    User save(User user);

    @Transactional
    @Query(nativeQuery = true, value = """
                update users set email = :#{#user.email}, password_hash = crypt(:#{#user.passwordHash}, gen_salt('bf')), farm_name = :#{#user.farmName}, name = :#{#user.name}
                where id = :#{#user.id}
                returning *
            """)
    User update(User user);

}
