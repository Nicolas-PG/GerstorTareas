package com.gestor.app.rest.Repository;

import com.gestor.app.rest.Entity.Task;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITodoRepository extends JpaRepository<Task, Long> {
}
