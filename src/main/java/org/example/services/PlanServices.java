package org.example.services;

import org.example.entity.Plan;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PlanServices {
     void save(Plan plan);

    Plan findById(long id);

    List<Plan> viewAll();

    void update(Plan plan, long id);

    void delete(long id);

}
