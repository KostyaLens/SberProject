package org.example.repository;

import org.example.entity.ArchivedPlan;
import org.example.entity.Plan;
import org.example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface ArchivedPlanRepository extends JpaRepository<ArchivedPlan, Long> {
    List<ArchivedPlan> findByUser(User user);
    @Query(value = "SELECT p FROM ArchivedPlan p WHERE (p.name LIKE %?1% OR p.description LIKE %?2%) AND p.user = ?3")
    List<ArchivedPlan> findByNameContainingOrDescriptionContaining(String name, String description, User user);
    @Query(value = "SELECT p FROM ArchivedPlan p WHERE (p.name LIKE %?1% OR p.description LIKE %?2%) AND p.deadline <= ?3")
    List<ArchivedPlan> findByNameContainingOrDescriptionContainingAndDeadlineBefore(String name, String description, LocalDateTime deadline, User user);
}
