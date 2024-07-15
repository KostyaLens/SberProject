package org.example.controllers;

import org.example.entity.Plan;
import org.example.PlanCategory;
import org.example.services.ArchivedPlanImpl;
import org.example.services.PlanServicesImpl;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/ToDO")
public class PlanController {
    private final PlanServicesImpl planServices;
    private final ArchivedPlanImpl archivedPlan;

    @Autowired
    public PlanController(PlanServicesImpl planServices, ArchivedPlanImpl archivedPlan) {
        this.planServices = planServices;
        this.archivedPlan = archivedPlan;
    }

    @GetMapping()
    public String viewPlans(Model model) {
        model.addAttribute("plans", planServices.viewAll());
        model.addAttribute("plan", new Plan());
        return "ToDO";
    }

    @GetMapping("/archived")
    public String viewPlansArchived(Model model) {
        model.addAttribute("plans", archivedPlan.viewAll());
        model.addAttribute("plan", new Plan());
        return "ToDO";
    }

    @GetMapping("/sortName")
    public String viewPlansSortName(Model model) {
        model.addAttribute("plans", planServices.sortByName());
        model.addAttribute("plan", new Plan());
        return "ToDO";
    }

    @GetMapping("/sortPriority")
    public String viewPlansSortPriority(Model model) {
        model.addAttribute("plans", planServices.sortByPriority());
        model.addAttribute("plan", new Plan());
        return "ToDO";
    }

    @GetMapping("/sortDate")
    public String viewPlansSortDate(Model model) {
        model.addAttribute("plans", planServices.sortByDate());
        model.addAttribute("plan", new Plan());
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
        model.addAttribute("plans", planServices.findAllByPlanCategory(planCategory));
        model.addAttribute("plan", new Plan());
        return "ToDO";
    }


    @GetMapping("/findByKeyWordOrDate")
    public String findByKeyWordOrDate(
            Model model,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) LocalDateTime date)

    {
        model.addAttribute("plan", new Plan());
        if (date == null){
            model.addAttribute("plans", planServices.findByNameContainingOrDescriptionContaining(name, name));
            model.addAttribute("archivedPlans", archivedPlan.findByNameContainingOrDescriptionContaining(name, name));
            System.out.println("и тут я был");
        }else{
            model.addAttribute("plans", planServices.findByNameContainingOrDescriptionContainingAndDeadlineBefore(name, name, date));
            model.addAttribute("archivedPlans", archivedPlan.findByNameContainingOrDescriptionContainingAndDeadlineBefore(name, name, date));
        }
        return "ToDO";
    }


//    @PostMapping()
//    public String addPlan(
//            @RequestParam String name,
//            @RequestParam String description,
//            @RequestParam int rating,
//            @RequestParam("date") String dateTime,
//            @RequestParam(value = "category", defaultValue = "any") String category,
//            Model model
//    ) {
//        Plan p = new Plan();
//        p.setName(name);
//        p.setDescription(description);
//        p.setRating(rating);
//        p.setDeadline(time.convertStringToDAteTime(dateTime));
//        //переделать этот костыль на чтото нормальное
//        if (category.equals("any")){
//            p.setPlanCategory(PlanCategory.ANY);
//            System.out.println("asd");
//        }else {
//            p.setPlanCategory(PlanCategory.HOUSEHOLD);
//        }
//        }ы
//        //
//        planServices.save(p);
//        var plan = planServices.viewAll();
//        model.addAttribute("plan", plan);
//        return "ToDO";
//    }


    @PostMapping()
    public String addPlan(@Valid @ModelAttribute("plan") Plan plan, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            return "ToDO";
        }
        planServices.save(plan);
        return "redirect:/ToDO";
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

//    @PostMapping("/{id}/edit")
//    public String update(@RequestParam String name,
//                         @RequestParam String description,
//                         @RequestParam int rating,
//                         @RequestParam() boolean completed,
//                         @RequestParam("date") String dateTime,
//                         @PathVariable("id") int id)
//    {
//        Plan p = new Plan();
//        p.setCompleted(completed);
//        p.setName(name);
//        p.setDescription(description);
//        p.setRating(rating);
//        p.setDeadline(time.convertStringToDAteTime(dateTime));
//        planServices.update(p, id);
//        return "redirect:/ToDO";
//    }

    @PostMapping("/{id}/completed")
    private String completed(@PathVariable("id") long id){
        Plan plan = planServices.findById(id);
        plan.setCompleted(true);
        planServices.save(plan);
        return "redirect:/ToDO";
    }
    @PostMapping("/{id}/edit")
    public String update(@PathVariable("id") long id, @Valid @ModelAttribute("onePlan") Plan plan, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return "/edit";
        }
        planServices.update(plan, id);
        return "redirect:/ToDO";
    }

    @PostMapping("/{id}/archived")
    public String archivingPlan(@PathVariable("id") long id){
//        if (bindingResult.hasErrors()) {
//            return "/edit";
//        }
        archivedPlan.save(planServices.findById(id));
        System.out.println("я засейвил");
        planServices.delete(id);
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

//    @GetMapping("/{id}/delete")
//    public String delete(@PathVariable("id") long id) {
//        System.out.println(id);
//        planServices.delete(id);
//        return "redirect:/ToDO";
//    }
}
