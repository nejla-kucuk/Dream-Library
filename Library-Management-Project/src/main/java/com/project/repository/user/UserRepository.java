package com.project.repository.user;

import com.project.entity.enums.RoleType;
import com.project.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmailEquals(String email);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    @Query("SELECT COUNT (u) FROM User u INNER JOIN u.userRole r WHERE r.roleType =?1")
    long countAdmin(RoleType roleType);
}
