package org.example.controllers;

import lombok.extern.slf4j.Slf4j;
import org.example.entity.Plan;
import org.example.entity.PlanCategory;
import org.example.services.PlanServices;
import org.example.services.PlanServicesImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.example.Time;

@Controller
@RequestMapping("/ToDO")
public class PlanController {
    private final PlanServicesImpl planServices;
    private final Time time = new Time();
    @Autowired
    public PlanController(PlanServicesImpl planServices) {
        this.planServices = planServices;
    }

    @GetMapping()
    public String viewPlans(Model model) {
        model.addAttribute("plan", planServices.viewAll());
        return "ToDO";
    }

    @GetMapping("/sortName")
    public String viewPlansSortName(Model model) {
        model.addAttribute("plan", planServices.sortByName());
        return "ToDO";
    }

    @GetMapping("/sortPriority")
    public String viewPlansSortPriority(Model model) {
        model.addAttribute("plan", planServices.sortByPriority());
        return "ToDO";
    }

    @GetMapping("/sortDate")
    public String viewPlansSortDate(Model model) {
        model.addAttribute("plan", planServices.sortByDate());
        return "ToDO";
    }

    @GetMapping("/findCategory")
    public String findPlansCategory(
            Model model,
            @RequestParam String category)
    {
        PlanCategory planCategory;
        if (category.equals("any")){
            planCategory = PlanCategory.ANY;
        }else {
            planCategory = PlanCategory.HOUSEHOLD;
        }
        model.addAttribute("plan", planServices.findAllByPlanCategory(planCategory));
        return "ToDO";
    }

    @PostMapping()
    public String addPlan(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam int rating,
            @RequestParam("date") String dateTime,
            @RequestParam(value = "category", defaultValue = "any") String category,
            Model model
    ) {
        Plan p = new Plan();
        p.setName(name);
        p.setDescription(description);
        p.setRating(rating);
        p.setDeadline(time.convertStringToDAteTime(dateTime));
        //
        if (category.equals("any")){
            p.setPlanCategory(PlanCategory.ANY);
            System.out.println("asd");
        }else {
            p.setPlanCategory(PlanCategory.HOUSEHOLD);
        }
        //
        planServices.save(p);
        var plan = planServices.viewAll();
        model.addAttribute("plan", plan);
        return "ToDO";
    }

//    @PostMapping
//    public String addPlan(Model model, Plan plan){
//        model.addAttribute("plan", new Plan());
//        planServices.save(plan);
//        return "ToDO";
//    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("onePlan", planServices.findById(id));
        return "show";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("onePlan", planServices.findById(id));
        return "edit";
    }

    @PostMapping("/{id}/edit")
    public String update(@RequestParam String name,
                         @RequestParam String description,
                         @RequestParam int rating,
                         @RequestParam(required=false, defaultValue = "false") boolean completed,
                         @RequestParam("date") String dateTime,
                         @PathVariable("id") int id) {
//        LocalDateTime localDateTime = time.convertStringToDAteTime(dateTime);
        System.out.println("asdasdsadsa");
        Plan p = new Plan();
        p.setCompleted(completed);
        p.setName(name);
        p.setDescription(description);
        p.setRating(rating);
        p.setDeadline(time.convertStringToDAteTime(dateTime));
        planServices.update(p, id);
        return "redirect:/ToDO";
    }

//    @GetMapping(params = {"sort"})
//    public String sort(Model model){
//        planServices.sorst();
//        var plan = planServices.allPlans();
//        model.addAttribute("plan", plan);
//        System.out.println("Ч тут");
//        return "ToDO.html";
//    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") long id) {
        System.out.println(id);
        planServices.delete(id);
        return "redirect:/ToDO";
    }
}
