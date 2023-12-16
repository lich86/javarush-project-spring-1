package com.chervonnaya.controller;

import com.chervonnaya.dto.TaskDTO;
import com.chervonnaya.model.Task;
import com.chervonnaya.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.IntStream;

import static java.util.Objects.isNull;

@Slf4j
@Controller
@RequestMapping("/")
public class TaskController {

    private final TaskService taskService;
    private final ModelMapper mapper;

    @Autowired
    public TaskController(TaskService taskService, ModelMapper mapper) {
        this.taskService = taskService;
        this.mapper = mapper;
    }

    @GetMapping("/")
    public String tasks(Model model,
                            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                            @RequestParam(value = "limit", required = false, defaultValue = "10") int limit) {
        Page<Task> tasksPage = taskService.getAll(page, limit);
        model.addAttribute("tasks", tasksPage.getContent());
        model.addAttribute("current_page", page);
        int totalPages = (int) Math.ceil(1.0 * taskService.getAllCount() / limit);
        if(totalPages > 1) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed().toList();
            model.addAttribute("page_numbers", pageNumbers);
        }
        return "index";
    }

    @PostMapping("/")
    public String add(Model model,
                    @RequestBody TaskDTO taskDTO) {
        Task task = mapper.map(taskDTO, Task.class);
        taskService.save(task);
        log.info("Task N{}: \"{}\", with status \"{}\" is added", task.getId(), task.getDescription(), task.getStatus());
        return tasks(model, 1, 10);
    }

    @PutMapping("/{id}")
    public String update(Model model,
                     @PathVariable(name = "id") Integer id,
                     @RequestBody TaskDTO taskDTO) {
        if(isNull(id) || id <= 0) {
            throw new RuntimeException("Invalid id");
        }
        String oldStatus = taskService.getById(id).getStatus().toString();
        Task task = mapper.map(taskDTO, Task.class);
        taskService.save(task);
        log.info("Task N{} was updated, old status was \"{}\", new status is \"{}\", description is \"{}\"", id, oldStatus, task.getStatus(), task.getDescription());
        return tasks(model, 1,10);
    }

    @DeleteMapping("/{id}")
    public String delete(Model model,
                         @PathVariable(name = "id") Integer id) {
        if(isNull(id) || id <= 0) {
            throw new RuntimeException("Invalid id");
        }
        taskService.delete(taskService.getById(id));
        log.warn("Task N{} was deleted", id);
        return tasks(model, 1, 10);
    }

}
