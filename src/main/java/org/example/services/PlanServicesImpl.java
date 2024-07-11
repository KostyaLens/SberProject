package org.example.services;

import org.example.entity.Plan;
import org.example.repository.PlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Service
@Transactional(readOnly = true)
public class PlanServicesImpl implements PlanServices{
    private final PlanRepository planRepository;
    @Autowired
    public PlanServicesImpl(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    @Override
    public void save(Plan plan) {
        planRepository.save(plan);
    }

    @Override
    public Optional<Plan> findById(long id) {
        return planRepository.findById(id);
    }

    @Override
    public List<Plan> viewAll() {
        return planRepository.findAll();
    }

    @Override
    public void update(int id, String name, String description, boolean completed, int rating, LocalDateTime time) {
        planRepository.save(new Plan(name, description, time, completed, id, rating));
    }

    @Override
    public void delete(long id) {
        planRepository.deleteById(id);
    }
}
