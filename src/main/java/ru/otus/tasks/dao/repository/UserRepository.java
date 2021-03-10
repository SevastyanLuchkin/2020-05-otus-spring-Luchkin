package ru.otus.tasks.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.tasks.dao.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
