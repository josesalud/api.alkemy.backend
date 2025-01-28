package com.alkemy.technical.test.service;

import com.alkemy.technical.test.dtos.responses.UserResponseDTO;
import com.alkemy.technical.test.entities.Task;
import com.alkemy.technical.test.entities.Users;
import com.alkemy.technical.test.repository.ITaskRepository;
import com.alkemy.technical.test.dtos.requests.TaskRequestDTO;
import com.alkemy.technical.test.dtos.responses.TaskResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class TaskService {

    private final ITaskRepository taskRepository;

    public List<TaskResponseDTO> getAll(){
        var listTask = this.taskRepository.findAll();
        var listTaskResponse = listTask.stream().map(task -> {
            return new TaskResponseDTO(task.getId(),task.getName(),task.getDescription(),task.getStatus());
        }).toList();
        return  listTaskResponse;
    }

    public TaskResponseDTO save(TaskRequestDTO taskRequestDTO){
        var newTask = Task.builder().build();
        var taskSave = Task.builder().build();
        if(taskRequestDTO.id() != null){
            var taskFind = this.taskRepository.findById(taskRequestDTO.id()).orElseThrow();
            taskFind.setName(taskRequestDTO.name());
            taskFind.setDescription(taskRequestDTO.description());
            taskFind.setUpdateAt(LocalDateTime.now());
            taskSave = this.taskRepository.save(taskFind);
        }else{
            newTask.setName(taskRequestDTO.name());
            newTask.setDescription(taskRequestDTO.description());
            newTask.setStatus(true);
            newTask.setCreateAt(LocalDateTime.now());
            newTask.setUpdateAt(LocalDateTime.now());
            taskSave = this.taskRepository.save(newTask);
        }
        return new TaskResponseDTO(taskSave.getId(),taskSave.getName(),taskSave.getDescription(),taskSave.getStatus());
    }

    public TaskResponseDTO delete(Long id){
        var taskFind = this.taskRepository.findById(id).orElseThrow();
        taskFind.setStatus(false);
        taskFind.setUpdateAt(LocalDateTime.now());

        var taskSave = this.taskRepository.save(taskFind);
        return new TaskResponseDTO(taskSave.getId(),taskSave.getName(),taskSave.getDescription(),taskSave.getStatus());
    }



}
