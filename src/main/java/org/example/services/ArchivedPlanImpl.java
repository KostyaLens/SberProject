package org.example.services;

import org.example.entity.ArchivedPlan;
import org.example.entity.Plan;
import org.example.entity.User;
import org.example.repository.ArchivedPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
@Service
@Transactional(readOnly = true)
public class ArchivedPlanImpl implements PlanServices<ArchivedPlan>{
    private final ArchivedPlanRepository planRepository;
    @Autowired
    public ArchivedPlanImpl(ArchivedPlanRepository planRepository) {
        this.planRepository = planRepository;
    }
    @Override
    public void save(ArchivedPlan plan) {
        planRepository.save(plan);
    }

    @Override
    public ArchivedPlan findById(long id) {
        return null;
    }

    @Override
    public void update(ArchivedPlan plan, long id) {

    }

    @Override
    public void delete(long id) {
        planRepository.deleteById(id);
    }

    public void save(Plan plan) {
        ArchivedPlan plan1 = new ArchivedPlan();
        plan1.setName(plan.getName());
        plan1.setPlanCategory(plan.getPlanCategory());
        plan1.setRating(plan.getPriority());
        plan1.setDeadline(plan.getDeadline());
        plan1.setDescription(plan.getDescription());
        plan1.setUser(plan.getUser());
        save(plan1);
    }
    @Override
    public ArchivedPlan findByIdAndUser(long id, User user){
        return planRepository.findByIdAndUser(id, user);
    }
    @Override
    public List<ArchivedPlan> viewAll(User user) {
        return planRepository.findByUser(user);
    }

    public List<ArchivedPlan> findByNameContainingOrDescriptionContaining(String name, String description, User user){
        return planRepository.findByNameContainingOrDescriptionContaining(name, description, user);
    }

    public List<ArchivedPlan> findByNameContainingOrDescriptionContainingAndDeadlineBefore(String name, String description, LocalDateTime deadline, User user){
        return planRepository.findByNameContainingOrDescriptionContainingAndDeadlineBefore(name, description, deadline, user);
    }
}
