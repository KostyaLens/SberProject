package org.example.services;

import org.example.entity.Plan;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public interface PlanServices {
    long save(Plan plan);

    Optional<PlanServices> findById(long id);

    List<Plan> viewAll();

    boolean update(int id, String name, String description, boolean completed, int rating, String time);

    boolean delete(long id);
}
