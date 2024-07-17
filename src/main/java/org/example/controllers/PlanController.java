package org.example.controllers;

import jakarta.transaction.Transactional;
import org.example.entity.*;
import org.example.services.ArchivedPlanImpl;
import org.example.services.PlanServicesImpl;
import org.example.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.validation.Valid;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

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
        User user = userService.findByEmail(userDetails.getUsername()).orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        model.addAttribute("plans", planServices.viewAll(user));
        model.addAttribute("plan", new Plan());
        return "ToDO";
    }

    @GetMapping("/archived")
    public String viewPlansArchived(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByEmail(userDetails.getUsername()).orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        model.addAttribute("archivedPlans", archivedPlan.viewAll(user));
        model.addAttribute("plan", new Plan());
        return "ToDO";
    }

    @GetMapping("/sort")
    public String sortPlans(Model model, @AuthenticationPrincipal UserDetails userDetails, @RequestParam String sortCriteria) {
        User user = userService.findByEmail(userDetails.getUsername()).orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        model.addAttribute("plans", planServices.sortPlans(user, sortCriteria));
        model.addAttribute("plan", new Plan());
        return "ToDO";
    }

    @GetMapping("/findCategory")
    public String findPlansCategory(Model model, @RequestParam PlanCategory category, @AuthenticationPrincipal UserDetails userDetails) {
        PlanCategory planCategory;
        User user = userService.findByEmail(userDetails.getUsername()).orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        model.addAttribute("plans", planServices.findByPlanCategory(category, user));
        model.addAttribute("plan", new Plan());
        return "ToDO";
    }

    @GetMapping("/findByKeyWordOrDate")
    public String findByKeyWordOrDate(
            Model model,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) LocalDateTime date,
            @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByEmail(userDetails.getUsername()).orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        model.addAttribute("plan", new Plan());
        model.addAttribute("plans", findPlans(name, date, user));
        model.addAttribute("archivedPlans", findArchivedPlans(name, date, user));

        return "ToDO";
    }

    private List<Plan> findPlans(String name, LocalDateTime date, User user) {
        return date == null
                ? planServices.findByNameContainingOrDescriptionContaining(name, name, user)
                : planServices.findByNameContainingOrDescriptionContainingAndDeadlineBefore(name, name, date, user);
    }

    private List<ArchivedPlan> findArchivedPlans(String name, LocalDateTime date, User user) {
        return date == null
                ? archivedPlan.findByNameContainingOrDescriptionContaining(name, name, user)
                : archivedPlan.findByNameContainingOrDescriptionContainingAndDeadlineBefore(name, name, date, user);
    }

    @PostMapping()
    public String addPlan(@Valid @ModelAttribute("plan") Plan plan, BindingResult bindingResult, Model model, @AuthenticationPrincipal UserDetails userDetails, @RequestParam Frequency frequency) {
        if (bindingResult.hasErrors()) {
            return "ToDO";
        }
        User user = userService.findByEmail(userDetails.getUsername()).orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        plan.setUser(user);
        if (frequency != Frequency.NEVER) {
            plan.setFrequency(frequency);
            plan.setDateTimeEndPlan(planServices.plusTime(frequency));
        }
        planServices.save(plan);
        return "redirect:/ToDO";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByEmail(userDetails.getUsername()).orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        Plan plan = planServices.findByIdAndUser(id, user);
        if (plan == null){
            return "redirect:/ToDO";
        }
        model.addAttribute("onePlan", plan);
        return "show";
    }

    @GetMapping("/{id}/arch")
    public String showArchivedPlan(@PathVariable("id") int id, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByEmail(userDetails.getUsername()).orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        ArchivedPlan plan = archivedPlan.findByIdAndUser(id, user);
        if (plan == null){
            return "redirect:/ToDO";
        }
        model.addAttribute("onePlan", plan);
        return "showAch";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByEmail(userDetails.getUsername()).orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        Plan plan = planServices.findByIdAndUser(id, user);
        if (plan == null){
            return "redirect:/ToDO";
        }
        model.addAttribute("onePlan", plan);
        return "edit";
    }

    @PostMapping("/{id}/edit")
    public String update(@PathVariable("id") long id, @Valid @ModelAttribute("onePlan") Plan plan, BindingResult bindingResult, @AuthenticationPrincipal UserDetails userDetails) {
        if (bindingResult.hasErrors()) {
            return "/edit";
        }
        User user = userService.findByEmail(userDetails.getUsername()).orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        if (planServices.findByIdAndUser(id, user) == null){
            return "redirect:/ToDO";
        }
        plan.setUser(planServices.findById(id).getUser());
        plan.setFrequency(planServices.findById(id).getFrequency());
        plan.setDateTimeEndPlan(planServices.findById(id).getDateTimeEndPlan());
        planServices.update(plan, id);
        return "redirect:/ToDO";
    }

    @PostMapping("/{id}/archived")
    public String archivingPlan(@PathVariable("id") long id, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByEmail(userDetails.getUsername()).orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        Plan plan = planServices.findByIdAndUser(id, user);
        if (plan.getUser() != user) {
            return "redirect:/ToDO";
        }
        archivedPlan.save(planServices.findById(id));
        planServices.delete(id);
        return "redirect:/ToDO";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") long id, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByEmail(userDetails.getUsername()).orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        if (planServices.findByIdAndUser(id, user).getUser() != user) {
            return "redirect:/ToDO";
        }
        planServices.delete(id);
        return "redirect:/ToDO";
    }

    @Transactional
    @Scheduled(fixedDelay = 60000)
    public void monitoringRepeatablePlan() {
        List<Plan> repeatablePlans = planServices.findByDateTimeEndPlanBefore(LocalDateTime.now());
        for (Plan repeatablePlan : repeatablePlans) {
            repeatablePlan.setCompleted(false);
            repeatablePlan.setDeadline(planServices.plusTime(repeatablePlan.getFrequency()));
            planServices.update(repeatablePlan, repeatablePlan.getId());
        }
    }
}


