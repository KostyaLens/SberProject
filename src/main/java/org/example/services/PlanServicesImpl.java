package org.example.services;

import org.example.Time;
import org.example.entity.Plan;
import org.example.repository.PlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
@Transactional(readOnly = true)
public class PlanServicesImpl implements PlanServices{
    private final PlanRepository planRepository;
    private final Time time = new Time();
    @Autowired
    public PlanServicesImpl(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    @Override
    @Transactional
    public void save(Plan plan) {
        planRepository.save(plan);
    }

    @Override
    public Plan findById(long id) {
        Optional<Plan> foundPlan = planRepository.findById(id);
        return foundPlan.orElse(null);
    }

    @Override

    public List<Plan> viewAll() {
        return planRepository.findAll();
    }

    @Override
    @Transactional
    public void update(Plan plan) {
        System.out.println("jjjjjdasd");
        planRepository.save(plan);
    }

    @Override
    @Transactional
    public void delete(long id) {
        planRepository.deleteById(id);
    }
}
