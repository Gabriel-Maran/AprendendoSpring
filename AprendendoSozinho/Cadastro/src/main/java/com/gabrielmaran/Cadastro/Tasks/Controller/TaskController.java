package com.gabrielmaran.Cadastro.Tasks.Controller;


import com.gabrielmaran.Cadastro.Tasks.Model.TaskModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
public class TaskController {
    //TODO: fazer o controller de tasks completo
    @GetMapping("/tasks")
    public List<TaskModel> boasVindas(){
        return List.of(new TaskModel(), new TaskModel());
    }
}
