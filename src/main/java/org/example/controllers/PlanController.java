package org.example.controllers;

import org.example.entity.Plan;
import org.example.PlanCategory;
import org.example.entity.User;
import org.example.services.ArchivedPlanImpl;
import org.example.services.PlanServicesImpl;
import org.example.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@RequestMapping("/ToDO")
public class PlanController {


    private final UserService userService;
    private final PlanServicesImpl planServices;
    private final ArchivedPlanImpl archivedPlan;

    @Autowired
    public PlanController(UserService userService, PlanServicesImpl planServices, ArchivedPlanImpl archivedPlan) {
        this.userService = userService;
        this.planServices = planServices;
        this.archivedPlan = archivedPlan;
    }

    @GetMapping()
    public String viewPlans(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByUsername(userDetails.getUsername()).orElseThrow(() -> new RuntimeException("User not found"));
        model.addAttribute("plans", planServices.viewAll(user));
        model.addAttribute("plan", new Plan());
        return "ToDO";
    }

    @GetMapping("/archived")
    public String viewPlansArchived(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByUsername(userDetails.getUsername()).orElseThrow(() -> new RuntimeException("User not found"));
        model.addAttribute("plans", archivedPlan.viewAll(user));
        model.addAttribute("plan", new Plan());
        return "ToDO";
    }

    @GetMapping("/sortName")
    public String viewPlansSortName(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByUsername(userDetails.getUsername()).orElseThrow(() -> new RuntimeException("User not found"));
        model.addAttribute("plans", planServices.sortByName(user));
        model.addAttribute("plan", new Plan());
        return "ToDO";
    }

    @GetMapping("/sortPriority")
    public String viewPlansSortPriority(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByUsername(userDetails.getUsername()).orElseThrow(() -> new RuntimeException("User not found"));
        model.addAttribute("plans", planServices.sortByPriority(user));
        model.addAttribute("plan", new Plan());
        return "ToDO";
    }

    @GetMapping("/sortDate")
    public String viewPlansSortDate(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByUsername(userDetails.getUsername()).orElseThrow(() -> new RuntimeException("User not found"));
        model.addAttribute("plans", planServices.sortByDate(user));
        model.addAttribute("plan", new Plan());
        return "ToDO";
    }

    @GetMapping("/findCategory")
    public String findPlansCategory(
            Model model,
            @RequestParam String category,
            @AuthenticationPrincipal UserDetails userDetails)
    {
        PlanCategory planCategory;
        if (category.equals("any")){
            planCategory = PlanCategory.ANY;
        }else {
            planCategory = PlanCategory.HOUSEHOLD;
        }
        User user = userService.findByUsername(userDetails.getUsername()).orElseThrow(() -> new RuntimeException("User not found"));
        model.addAttribute("plans", planServices.findByPlanCategory(planCategory, user));
        model.addAttribute("plan", new Plan());
        return "ToDO";
    }


    @GetMapping("/findByKeyWordOrDate")
    public String findByKeyWordOrDate(
            Model model,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) LocalDateTime date,
            @AuthenticationPrincipal UserDetails userDetails)

    {
        User user = userService.findByUsername(userDetails.getUsername()).orElseThrow(() -> new RuntimeException("User not found"));
        model.addAttribute("plan", new Plan());
        if (date == null){
            model.addAttribute("plans", planServices.findByNameContainingOrDescriptionContaining(name, name, user));
            model.addAttribute("archivedPlans", archivedPlan.findByNameContainingOrDescriptionContaining(name, name, user));
        }else{
            model.addAttribute("plans", planServices.findByNameContainingOrDescriptionContainingAndDeadlineBefore(name, name, date, user));
            model.addAttribute("archivedPlans", archivedPlan.findByNameContainingOrDescriptionContainingAndDeadlineBefore(name, name, date, user));
        }
        return "ToDO";
    }

    @PostMapping()
    public String addPlan(@Valid @ModelAttribute("plan") Plan plan, BindingResult bindingResult, Model model, @AuthenticationPrincipal UserDetails userDetails){
        if (bindingResult.hasErrors()) {
            return "ToDO";
        }
        User user = userService.findByUsername(userDetails.getUsername()).orElseThrow(() -> new RuntimeException("User not found"));
        plan.setUser(user);
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
        archivedPlan.save(planServices.findById(id));
        planServices.delete(id);
        return "redirect:/ToDO";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") long id) {
        System.out.println(id);
        planServices.delete(id);
        return "redirect:/ToDO";
    }

}
