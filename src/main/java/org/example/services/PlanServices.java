package org.example.services;

import org.example.entity.Plan;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Service
public interface PlanServices {
     void save(Plan plan);

    Optional<Plan> findById(long id);

    List<Plan> viewAll();

    void update(int id, String name, String description, boolean completed, int rating, LocalDateTime time);

    void delete(long id);
}
