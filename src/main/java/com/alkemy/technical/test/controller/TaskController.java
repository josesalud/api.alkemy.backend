package com.alkemy.technical.test.controller;

import com.alkemy.technical.test.dtos.requests.TaskRequestDTO;
import com.alkemy.technical.test.dtos.responses.TaskResponseDTO;
import com.alkemy.technical.test.service.TaskService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
@AllArgsConstructor
@Slf4j
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> getALl(){
        var listTask = this.taskService.getAll();
        return ResponseEntity.ok(listTask);
    }

    @PostMapping
    public ResponseEntity<TaskResponseDTO> save(@RequestBody TaskRequestDTO taskRequestDTO){
         final TaskResponseDTO task = this.taskService.save(taskRequestDTO);
        return ResponseEntity.ok(task);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<TaskResponseDTO> delete(@PathVariable Long id){
        final TaskResponseDTO task = this.taskService.delete(id);
        return ResponseEntity.ok(task);
    }
}
