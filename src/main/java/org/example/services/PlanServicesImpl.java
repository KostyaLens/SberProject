package org.example.services;

import org.example.entity.Plan;
import org.example.repository.PlanRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class PlanServicesImpl implements PlanServices{
    private final PlanRepository planRepository;

    @Autowired
    public PlanServicesImpl(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }
    @Override
    public long save(Plan plan) {
        Plan savedPlan = planRepository.save(plan);

        return savedPlan.getId();
    }

    @Override
    public Optional<Plan> findById(long id) {
        return planRepository.findById(id);
    }

    @Override
    public List<Plan> viewAll() {
        return null;
    }

    @Override
    public boolean update(int id, String name, String description, boolean completed, int rating, String time) {
        return false;
    }

    @Override
    public boolean delete(long id) {
        return false;
    }
}
