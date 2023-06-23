package com.cyberpoint.service.impl;

import com.cyberpoint.domain.Task;
import com.cyberpoint.repository.TaskRepository;
import com.cyberpoint.service.TaskService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Task}.
 */
@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    private final Logger log = LoggerFactory.getLogger(TaskServiceImpl.class);

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task save(Task task) {
        log.debug("Request to save Task : {}", task);
        return taskRepository.save(task);
    }

    @Override
    public Task update(Task task) {
        log.debug("Request to update Task : {}", task);
        return taskRepository.save(task);
    }

    @Override
    public Optional<Task> partialUpdate(Task task) {
        log.debug("Request to partially update Task : {}", task);

        return taskRepository
            .findById(task.getId())
            .map(existingTask -> {
                if (task.getCommand() != null) {
                    existingTask.setCommand(task.getCommand());
                }
                if (task.getFormattedCommand() != null) {
                    existingTask.setFormattedCommand(task.getFormattedCommand());
                }
                if (task.getSubmittedBy() != null) {
                    existingTask.setSubmittedBy(task.getSubmittedBy());
                }
                if (task.getDescription() != null) {
                    existingTask.setDescription(task.getDescription());
                }
                if (task.getAdded() != null) {
                    existingTask.setAdded(task.getAdded());
                }
                if (task.getUpdated() != null) {
                    existingTask.setUpdated(task.getUpdated());
                }
                if (task.getRetrieved() != null) {
                    existingTask.setRetrieved(task.getRetrieved());
                }
                if (task.getFailure() != null) {
                    existingTask.setFailure(task.getFailure());
                }
                if (task.getApproved() != null) {
                    existingTask.setApproved(task.getApproved());
                }

                return existingTask;
            })
            .map(taskRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Task> findAll(Pageable pageable) {
        log.debug("Request to get all Tasks");
        return taskRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Task> findOne(Long id) {
        log.debug("Request to get Task : {}", id);
        return taskRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Task : {}", id);
        taskRepository.deleteById(id);
    }
}
