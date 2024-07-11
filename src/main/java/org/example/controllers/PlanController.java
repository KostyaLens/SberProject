package org.example.controllers;

import lombok.extern.slf4j.Slf4j;
import org.example.entity.Plan;
import org.example.services.PlanServices;
import org.example.services.PlanServicesImpl;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.example.Time;

import java.time.LocalDateTime;


@Slf4j
@RestController
@RequestMapping("/ToDO")
public class PlanController {
    private final PlanServices planServices;
    private final Time time = new Time();

    public PlanController(PlanServicesImpl planServices) {
        this.planServices = planServices;
    }

    @GetMapping()
    public String viewPlans(Model model) {
        var plan = planServices.viewAll();
        model.addAttribute("plan", plan);
        System.out.println("sadasdw");
        return "ToDO.html";
    }

    @PostMapping()
    public String addPlan(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam int rating,
            @RequestParam("date") String dateTime,
            Model model
    ) {
        Plan p = new Plan();
        p.setName(name);
        p.setDescription(description);
        p.setRating(rating);
        p.setDeadline(time.convertStringToDAteTime(dateTime));
        planServices.save(p);
        var plan = planServices.viewAll();
        model.addAttribute("plan", plan);

        return "ToDO";
    }

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
                         @RequestParam boolean completed,
                         @RequestParam("date") String dateTime,
                         @PathVariable("id") int id) {
        LocalDateTime localDateTime = time.convertStringToDAteTime(dateTime);
        planServices.update(id, name, description, completed, rating, localDateTime);
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
    public String delete(@PathVariable("id") int id) {
        planServices.delete(id);
        System.out.println("sad");
        return "redirect:/ToDO";
    }

}
