package org.example.repository;

import org.example.entity.Plan;
import org.example.entity.PlanCategory;
import org.example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {
    List<Plan> findByPlanCategoryAndUser(PlanCategory planCategory, User user);
    List<Plan> findByUser(User user);
    List<Plan> findByUserOrderByCompleted(User user);

    List<Plan> findByUserOrderByRatingDesc(User user);
    List<Plan> findByUserOrderByName(User user);
    List<Plan> findByUserOrderByDeadline(User user);
    @Query(value = "SELECT p FROM Plan p WHERE (p.name LIKE %?1% OR p.description LIKE %?2%) AND p.user = ?3")
    List<Plan> findByNameContainingOrDescriptionContaining(String name, String description, User user);
    @Query(value = "SELECT p FROM Plan p WHERE ((p.name LIKE %?1% OR p.description LIKE %?2%) AND p.deadline <= ?3) AND p.user = ?4")
    List<Plan> findByNameContainingOrDescriptionContainingAndDeadlineBefore(String name, String description, LocalDateTime deadline,User user);
    @Query(value = "SELECT p FROM Plan p WHERE p.dateTimeEndPlan <= ?1")
    List<Plan> findByDateTimeEndPlanBefore(LocalDateTime now);
}
