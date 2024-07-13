package org.example.repository;

import org.example.entity.Plan;
import org.example.entity.PlanCategory;
import org.example.services.PlanServices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {
    List<Plan> findAllByPlanCategory(PlanCategory planCategory);

    List<Plan> findByNameContainingOrDescriptionContaining(String name, String description);
    @Query(value = "SELECT p FROM Plan p WHERE (p.name LIKE %?1% OR p.description LIKE %?2%) AND p.deadline <= ?3")
    List<Plan> findByNameContainingOrDescriptionContainingAndDeadlineBefore(String name, String description, LocalDateTime deadline);
}
