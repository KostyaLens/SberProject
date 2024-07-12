package org.example.repository;

import org.example.entity.Plan;
import org.example.entity.PlanCategory;
import org.example.services.PlanServices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {
    List<Plan> findAllByPlanCategory(PlanCategory planCategory);
}
