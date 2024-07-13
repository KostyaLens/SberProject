package org.example.services;

import org.example.Time;
import org.example.entity.Plan;
import org.example.PlanCategory;
import org.example.repository.PlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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

    public List<Plan> sortByName(){
        return planRepository.findAll(Sort.by("name"));
    }

    public List<Plan> sortByDate(){
        return planRepository.findAll(Sort.by("deadline").ascending());
    }

    public List<Plan> sortByPriority(){
        return planRepository.findAll(Sort.by("rating").descending());
    }

    public List<Plan> findByNameContainingOrDescriptionContaining(String name, String description){
        return planRepository.findByNameContainingOrDescriptionContaining(name, description);
    }

    public List<Plan> findByNameContainingOrDescriptionContainingAndDeadlineBefore(String name, String description, LocalDateTime deadline){
        return planRepository.findByNameContainingOrDescriptionContainingAndDeadlineBefore(name, description, deadline);
    }
    public List<Plan> findAllByPlanCategory(PlanCategory planCategory){
        return planRepository.findAllByPlanCategory(planCategory);
    }
    @Override
    @Transactional
    public void update(Plan plan, long id) {
        plan.setId(id);
        planRepository.save(plan);
    }

    @Override
    @Transactional
    public void delete(long id) {
        planRepository.deleteById(id);
    }
}
