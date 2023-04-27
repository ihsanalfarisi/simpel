package com.burat.simpel.controller;

import com.burat.simpel.model.*;
import com.burat.simpel.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class AssessmentController {
    final String BASE_URL = "/do-assessment";

    @Autowired
    UserService userService;

    @Autowired
    AssessorService assessorService;
    @Autowired
    AssessmentService assessmentService;
    @Autowired
    AssessmentLevelService assessmentLevelService;

    @Autowired
    CompetencyLevelService competencyLevelService;

    @Autowired
    EventPeriodService eventPeriodService;

    @Autowired
    ReviewAssessmentService reviewAssessmentService;

    @GetMapping(BASE_URL)
    private String selectUser(Model model, RedirectAttributes redirectAttributes){
        List<UserModel> listuser = userService.getAllUser();
        List<EventPeriodModel> listEventPeriod = eventPeriodService.getAllEventPeriod();
        model.addAttribute("listUser",listuser);
        model.addAttribute("eventPeriod", listEventPeriod.get(0));
        return "assessment-list-user";
    }

    @GetMapping(BASE_URL+"/{uuid}")
    private String assessmentForm(Model model, RedirectAttributes redirectAttributes, HttpServletRequest httpServletRequest, @PathVariable String uuid){
        Optional<UserModel> userModel = userService.getByUuid(uuid);
        if (!userModel.isPresent()){
            redirectAttributes.addFlashAttribute("errortext", "User dengan uuid " +uuid +" tidak ada");
            return "redirect:"+BASE_URL;
        }
        UserModel userModelget = userModel.get();


        // Create empty assessment model
        AssessmentModel assessmentModel = new AssessmentModel();
        AssessorModel currentAssessor = assessorService.getByUsername(httpServletRequest.getRemoteUser());

        if (currentAssessor == null){
            redirectAttributes.addFlashAttribute("errortext", "Akun ini bukan assessor, tidak bisa melakukan assessment");
            return "redirect:"+BASE_URL;
        }

        // Attribute to show Event Period
        List<EventPeriodModel> listEventPeriod = eventPeriodService.getAllEventPeriod();

        assessmentModel.setAssessor(currentAssessor);
        assessmentModel.setUser(userModelget);
        assessmentModel.setDate(LocalDate.now());
        assessmentModel.setEvent(listEventPeriod.get(0));
        assessmentModel.setListAssessment(new ArrayList<>());

        // Check duplicate assessor user and time
        if (assessmentService.checkDuplicate(assessmentModel)){
            redirectAttributes.addFlashAttribute("errortext", "Anda sudah pernah melakukan assessment ke user  " + "'" + assessmentModel.getUser().getUsername() + "'" + " untuk periode ini");
            return "redirect:"+BASE_URL;
        }

        // Max Level for each comptency
        List<Integer> maxLevelOfCompetency = new ArrayList<>();
        // Assessment level empty list
        List<AssessmentLevelModel> listAssessmentLevel = new ArrayList<>();


        // Get all Competency of this user
        List<CompetencyModel> listCompetency = userModelget.getTitleModel().getListCompetencyModel();
        if (listCompetency.isEmpty()){
            // Error if current user doesn't have competency model
            redirectAttributes.addFlashAttribute("errortext", "User dengan uuid " +uuid +" tidak memiliki competency model, buat terlebih dahulu");
            return "redirect:"+BASE_URL;
        }

        // Append new of AssessmentLevel objects according to already existing competency model
        for (CompetencyLevel x : listCompetency.get(0).getListCompetencyLevel()){
            // setup the new object
            AssessmentLevelModel assessmentLevelModel = new AssessmentLevelModel();
            assessmentLevelModel.setAssessment(assessmentModel);
            assessmentLevelModel.setCompetencyLevel(x);
            assessmentLevelModel.setGap(Long.valueOf(x.getLevel())); // input the current level of competency
            assessmentLevelModel.setResult(0L); // default is 0

            // Append new entry of max level for each competency
            maxLevelOfCompetency.add(competencyLevelService.countLevelOfCompetency(Long.valueOf(x.getLevel())));
            // save to the temp list
            listAssessmentLevel.add(assessmentLevelModel);
        }
        // System.out.println(maxLevelOfCompetency);
        // save list to the model
        assessmentModel.setListAssessment(listAssessmentLevel);
        // save model
        model.addAttribute("usermodel",userModelget);
        model.addAttribute("assessment",assessmentModel);
        model.addAttribute("eventPeriod", listEventPeriod.get(0));
        model.addAttribute("listMaxLevelCompetency",maxLevelOfCompetency);
        return "assessment-form";
    }

    @PostMapping(value = BASE_URL+ "/{uuid}", params = {"save"})
    public String assessmentFormSubmit(@ModelAttribute @Valid AssessmentModel assessmentModel, Model model, RedirectAttributes redirectAttributes){
        // Check duplicate assessor user and time
        if (assessmentService.checkDuplicate(assessmentModel)){
            redirectAttributes.addFlashAttribute("errortext", "Anda sudah pernah melakukan assessment ke user  " + "'" + assessmentModel.getUser().getUsername() + "'" + " untuk periode ini");
            return "redirect:"+BASE_URL;
        }
        // Check whether the all competency levels have been assessed
        for (AssessmentLevelModel x : assessmentModel.getListAssessment()){
            if (x.getResult() == null || x.getResult() == 0){
                redirectAttributes.addFlashAttribute("errortext", "Tidak boleh ada nilai level yang kosong");
                return "redirect:"+BASE_URL+"/"+assessmentModel.getUser().getAccountUuid();
            }
        }

        // Save the model
        assessmentService.add(assessmentModel);
        // Setup for every single assessmentLevel
        for (AssessmentLevelModel x : assessmentModel.getListAssessment()){
            x.setAssessment(assessmentModel);

            // Setting Gap
            x.setGap(x.getResult() - x.getGap());

            // save the model
            assessmentLevelService.add(x);
        }
        reviewAssessmentService.setReview(assessmentModel);
        // success notif
        redirectAttributes.addFlashAttribute("successtext", "User dengan username "+ assessmentModel.getUser().getUsername()+" sudah berhasil dilakukan assessment");
        return "redirect:"+BASE_URL;
    }
}
