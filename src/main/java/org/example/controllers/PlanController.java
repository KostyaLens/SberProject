package org.example.controllers;

import org.example.model.Plan;
import org.example.services.PlanServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.example.Time;


@Controller
@RequestMapping("/ToDO")
public class PlanController {

    private final PlanServices planServices;

    public PlanController(PlanServices planServices) {
        this.planServices = planServices;
    }
    private Time time = new Time();

    @GetMapping()
    public String viewPlans(Model model) {
        var plan = planServices.allPlans();
        model.addAttribute("plan", plan);
        System.out.println("sadasdw");
        return "ToDO.html";
    }

    @PostMapping()
    public String addProduct(
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
        var products = planServices.allPlans();
        model.addAttribute("plan", products);

        return "ToDO";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("onePlan", planServices.show(id));
        return "show";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("onePlan", planServices.show(id));
        return "edit";
    }
    @PostMapping("/{id}/edit")
    public String update(@RequestParam String name,
                         @RequestParam String description,
                         @RequestParam int rating,
                         @RequestParam boolean completed,
                         @RequestParam("date") String dateTime,
                         @PathVariable("id") int id) {
        planServices.update(id, name, description, completed, rating, dateTime);
        return "ToDO/{id}";
    }

//    @GetMapping(params = {"sort"})
//    public String sort(Model model){
//        planServices.sorst();
//        var plan = planServices.allPlans();
//        model.addAttribute("plan", plan);
//        System.out.println("Ч тут");
//        return "ToDO.html";
//    }

    @PostMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        planServices.delete(id);
        System.out.println("sad");
        return "redirect:/ToDO";
    }

}
