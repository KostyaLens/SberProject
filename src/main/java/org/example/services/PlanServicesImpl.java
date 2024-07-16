package org.example.services;

import org.example.entity.*;
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
        return null;
    }

    public List<Plan> viewAll(User user) {
        return planRepository.findByUser(user);
    }

    public List<Plan> sortByName(User user){
        return planRepository.findByUserOrderByName(user);
    }

    public List<Plan> sortByDate(User user){
        return planRepository.findByUserOrderByDeadline(user);
    }

    public List<Plan> sortByPriority(User user){
        return planRepository.findByUserOrderByRatingDesc(user);
    }

    public List<Plan> findByNameContainingOrDescriptionContaining(String name, String description, User user){
        return planRepository.findByNameContainingOrDescriptionContaining(name, description, user);
    }

    public List<Plan> findByNameContainingOrDescriptionContainingAndDeadlineBefore(String name, String description, LocalDateTime deadline, User user){
        return planRepository.findByNameContainingOrDescriptionContainingAndDeadlineBefore(name, description, deadline, user);
    }
    public List<Plan> findByPlanCategory(PlanCategory planCategory, User user){
        return planRepository.findByPlanCategoryAndUser(planCategory, user);
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

    public List<Plan> findByDateTimeEndPlanBefore(LocalDateTime now){
        return planRepository.findByDateTimeEndPlanBefore(now);
    }

    public List<Plan> findByUserOrderByCompleted(User user){
        return planRepository.findByUserOrderByCompleted(user);
    }
    public LocalDateTime plusTime(Frequency frequency){
        switch (frequency) {
            case ONEMINUTE:
                return LocalDateTime.now().plusMinutes(1);

            case ONEHOUR:
                return LocalDateTime.now().plusHours(1);
            case ONEDAY:
                return LocalDateTime.now().plusDays(1);
            case ONEWEEK:
                return LocalDateTime.now().plusWeeks(1);
            case ONEMOUTH:
                return LocalDateTime.now().plusMonths(1);
            case SIXMOUTH:
                return LocalDateTime.now().plusMonths(6);
            case ONEYEAR:
                return LocalDateTime.now().plusYears(1);
        }
        return LocalDateTime.now();
    }
}
