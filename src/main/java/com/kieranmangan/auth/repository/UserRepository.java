package com.kieranmangan.auth.repository;


import com.kieranmangan.auth.db.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
