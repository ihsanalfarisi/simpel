package com.burat.simpel.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.apache.catalina.mbeans.UserMBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.burat.simpel.model.AccountModel;
import com.burat.simpel.model.TrainingModel;
import com.burat.simpel.model.AccountModel;
import com.burat.simpel.model.TrainingModel;
import com.burat.simpel.model.TrainingPlanModel;
import com.burat.simpel.model.UserModel;
import com.burat.simpel.repository.TrainingPlanDb;
import com.burat.simpel.service.TrainingPlanService;
import com.burat.simpel.service.TrainingService;
import com.burat.simpel.service.UserService;
import com.burat.simpel.service.TrainingService;
import com.burat.simpel.service.UserService;

@Controller
public class TrainingPlanController {
    @Autowired
    TrainingPlanService trainingPlanService;

    @Autowired
    UserService userService;

    @Autowired
    TrainingService trainingService;

    @GetMapping("/training-plan")
    public String viewallTrainingPlan(Model model) {
        List<TrainingPlanModel> listTrainingPlan = trainingPlanService.getAllTrainingPlan();
        model.addAttribute("listTrainingPlan", listTrainingPlan);
        return "viewall-training-plan";
    }

    @GetMapping("/training-plan/{id}")
    public String viewTrainingPlan(@PathVariable Long id, Model model) {
        TrainingPlanModel trainingPlanModel = trainingPlanService.getTrainingPlanById(id);
        model.addAttribute("listUserTrainingPlan", trainingPlanModel.getUserInTrainingPlan());
        model.addAttribute("idTrainingPlan", id);
        model.addAttribute("idTraining", trainingPlanModel.getIdTraining());
        model.addAttribute("deskripsi", trainingPlanModel.getDeskripsi());
        model.addAttribute("dateStart", trainingPlanModel.getDateStart());
        model.addAttribute("dateEnd", trainingPlanModel.getDateEnd());
        model.addAttribute("budget", trainingPlanModel.getBudget());
        model.addAttribute("status", trainingPlanModel.getStatus());
        return "view-training-plan";
    }

    @GetMapping(value = "/training-plan/add")
    public String addTrainingPlan(Model model) {
        TrainingPlanModel trainingPlanModel = new TrainingPlanModel();
        UserModel user = new UserModel();
        List<UserModel> listUser = new ArrayList<>();
        List<UserModel> listUserExisting = userService.getAllUser();
        List<TrainingModel> listTraining = trainingService.getSomeTraining();

        listUser.add(user);
        trainingPlanModel.setUserInTrainingPlan(listUser);

        model.addAttribute("listTraining", listTraining);
        model.addAttribute("trainingPlanModel", trainingPlanModel);
        model.addAttribute("listUserExisting", listUserExisting);
        return "form-add-training-plan";
    }

    @PostMapping(value = "/training-plan/add", params = { "addRowUser" })
    private String addRowTrainingPlan(@ModelAttribute TrainingPlanModel trainingPlanModel, Model model) {
        if (trainingPlanModel.getUserInTrainingPlan() == null
                || trainingPlanModel.getUserInTrainingPlan().size() == 0) {
            trainingPlanModel.setUserInTrainingPlan(new ArrayList<>());
        }
        trainingPlanModel.getUserInTrainingPlan().add(new UserModel());
        model.addAttribute("trainingPlanModel", trainingPlanModel);

        List<TrainingModel> listTraining = trainingService.getListTraining();
        List<UserModel> listUserExisting = userService.getAllUser();
        model.addAttribute("listTraining", listTraining);
        model.addAttribute("listUserExisting", listUserExisting);
        return "form-add-training-plan";
    }

    @PostMapping(value = "/training-plan/add", params = { "deleteRowTraining" })
    private String deleteRowTrainingPlan(@ModelAttribute TrainingPlanModel trainingPlanModel,
            @RequestParam("deleteRowTraining") Integer row, Model model) {
        final Integer rowId = Integer.valueOf(row);
        trainingPlanModel.getUserInTrainingPlan().remove(rowId.intValue());

        List<TrainingModel> listTraining = trainingService.getListTraining();
        List<UserModel> listUserExisting = userService.getAllUser();

        model.addAttribute("listTraining", listTraining);
        model.addAttribute("listUserExisting", listUserExisting);
        model.addAttribute("trainingPlanModel", trainingPlanModel);
        return "form-add-training-plan";
    }

    @PostMapping(value = "/training-plan/add", params = { "save" })
    public String addTrainingPlanSubmit(@ModelAttribute TrainingPlanModel trainingPlanModel, Model model,
            RedirectAttributes redirectAttributes) {
        if (trainingPlanModel.getUserInTrainingPlan() == null
                || trainingPlanModel.getUserInTrainingPlan().size() == 0) {
            trainingPlanModel.setUserInTrainingPlan(new ArrayList<>());
        }
        trainingPlanModel.setStatus(0);
        trainingPlanService.addTrainingPlan(trainingPlanModel);

        List<TrainingPlanModel> listTrainingPlan = trainingPlanService.getAllTrainingPlan();
        model.addAttribute("listTrainingPlan", listTrainingPlan);
        redirectAttributes.addFlashAttribute("successtext", "Selamat! Training Plan berhasil ditambahkan");
        return "redirect:/training-plan";
    }

    @GetMapping("/training-plan/update/{id}")
    public String updateTrainingPlan(@PathVariable(value = "id") Long id, Model model) {
        TrainingPlanModel trainingPlanModel = trainingPlanService.getTrainingPlanById(id);
        List<UserModel> listUserExisting = userService.getAllUser();
        List<TrainingModel> listTraining = trainingService.getListTraining();
        model.addAttribute("listUserExisting", listUserExisting);
        model.addAttribute("listTraining", listTraining);
        model.addAttribute("trainingPlanModel", trainingPlanModel);
        return "form-update-training-plan";
    }

    @PostMapping(value = "/training-plan/update", params = { "addRow" })
    public String addRowTrainingPlanUpdate(@ModelAttribute TrainingPlanModel trainingPlanModel, Model model) {
        if (trainingPlanModel.getUserInTrainingPlan() == null
                || trainingPlanModel.getUserInTrainingPlan().size() == 0) {
            trainingPlanModel.setUserInTrainingPlan(new ArrayList<>());
        }
        trainingPlanModel.getUserInTrainingPlan().add(new UserModel());
        trainingPlanModel
                .setIdTraining(trainingService.getTrainingById(trainingPlanModel.getIdTraining().getTrainingId()));
        model.addAttribute("trainingPlanModel", trainingPlanModel);

        List<UserModel> listUserExisting = userService.getAllUser();
        model.addAttribute("listUserExisting", listUserExisting);
        return "form-update-training-plan";
    }

    @PostMapping(value = "/training-plan/update", params = { "deleteRow" })
    private String deleteRowTrainingPlanUpdate(@ModelAttribute TrainingPlanModel trainingPlanModel,
            @RequestParam("deleteRow") Integer row, Model model) {
        final Integer rowId = Integer.valueOf(row);
        trainingPlanModel.getUserInTrainingPlan().remove(rowId.intValue());
        List<UserModel> listUserExisting = userService.getAllUser();
        trainingPlanModel
                .setIdTraining(trainingService.getTrainingById(trainingPlanModel.getIdTraining().getTrainingId()));
        model.addAttribute("idTraining", trainingPlanModel.getIdTraining());
        model.addAttribute("listUserExisting", listUserExisting);
        model.addAttribute("trainingPlanModel", trainingPlanModel);
        return "form-update-training-plan";
    }

    @PostMapping(value = "/training-plan/update", params = { "save" })
    public String updateTrainingPlanSubmit(@ModelAttribute TrainingPlanModel trainingPlanModel, Model model,
            RedirectAttributes redirectAttributes) {
        if (trainingPlanModel.getUserInTrainingPlan() == null
                || trainingPlanModel.getUserInTrainingPlan().size() == 0) {
            trainingPlanModel.setUserInTrainingPlan(new ArrayList<>());
        }
        trainingPlanModel.setStatus(0);
        trainingPlanModel.setUserInTrainingPlan(trainingPlanModel.getUserInTrainingPlan());
        trainingPlanService.addTrainingPlan(trainingPlanModel);

        model.addAttribute("listTrainingPlan", trainingPlanModel.getUserInTrainingPlan());
        redirectAttributes.addFlashAttribute("successtext", "Selamat! Training Plan berhasil diubah");
        return "redirect:/training-plan";
    }

    @GetMapping("/training-plan/delete/{id}")
    public String deleteTrainingPlan(@PathVariable(value = "id") Long id, Model model) {
        TrainingPlanModel trainingPlanModel = trainingPlanService.getTrainingPlanById(id);
        trainingPlanService.deleteTrainingPlan(trainingPlanModel);
        model.addAttribute("listTrainingPlan", trainingPlanService.getAllTrainingPlan());
        return "redirect:/training-plan";
    }

    @PostMapping(value = "/training-plan/{id}", params = { "confirm" })
    public String confirmTrainingPlan(@PathVariable Long id, Model model, Principal principal, RedirectAttributes redirectAttributes) {
        TrainingPlanModel trainingPlanModel = trainingPlanService.getTrainingPlanById(id);
        switch (trainingPlanModel.getStatus()) {
            case 1:
                trainingPlanModel.setStatus(2);
                break;
            case 2:
                trainingPlanModel.setStatus(3);
                break;
            case 3:
                trainingPlanModel.setStatus(4);
                break;
            default:
                trainingPlanModel.setStatus(1);
                break;
        }
        trainingPlanService.addTrainingPlan(trainingPlanModel);
        redirectAttributes.addFlashAttribute("successtext", "Status Training berhasil diubah!");
        model.addAttribute("trainingPlanModel", trainingPlanModel);
        return "redirect:/training-plan/" + id;
    }
}
