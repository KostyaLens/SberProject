package org.example.services;


import org.example.Time;
import org.example.entity.Plan;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.ArrayList;
import java.util.List;

@Service
public class PlanServicesss {
    private List<Plan> plans = new ArrayList<>();
    private Time timeDate = new Time();
    private int id = -1;
    public List<Plan> allPlans(){
//        System.out.println(plans.get(0).getDeadline());
        return this.plans;
    }
    public void save(Plan plan){
        this.id++;
        plan.setId(this.id);
        plans.add(plan);
    }

    public Plan show(int id) {
        return plans.stream().filter(plan -> plan.getId() == id).findAny().orElse(null);
    }

    public void sorst(){
        if (!this.plans.isEmpty())
            this.plans.stream().sorted(Comparator.comparingInt(Plan::getRating)).toList();
    }

//    public void update(int id, String name, String description, boolean completed, int rating, String time) {
//        Plan planToBeUpdated = show(id);
//        planToBeUpdated.setName(name);
//        planToBeUpdated.setDescription(description);
//        planToBeUpdated.setCompleted(completed);
//        planToBeUpdated.setRating(rating);
//        planToBeUpdated.setDeadline(timeDate.convertStringToDAteTime(time));
//    }
    public void delete(int id){
        plans.removeIf(p -> p.getId() == id);
    }


}
