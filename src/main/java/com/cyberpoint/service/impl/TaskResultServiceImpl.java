package com.cyberpoint.service.impl;

import com.cyberpoint.domain.TaskResult;
import com.cyberpoint.repository.TaskResultRepository;
import com.cyberpoint.service.TaskResultService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TaskResult}.
 */
@Service
@Transactional
public class TaskResultServiceImpl implements TaskResultService {

    private final Logger log = LoggerFactory.getLogger(TaskResultServiceImpl.class);

    private final TaskResultRepository taskResultRepository;

    public TaskResultServiceImpl(TaskResultRepository taskResultRepository) {
        this.taskResultRepository = taskResultRepository;
    }

    @Override
    public TaskResult save(TaskResult taskResult) {
        log.debug("Request to save TaskResult : {}", taskResult);
        return taskResultRepository.save(taskResult);
    }

    @Override
    public TaskResult update(TaskResult taskResult) {
        log.debug("Request to update TaskResult : {}", taskResult);
        return taskResultRepository.save(taskResult);
    }

    @Override
    public Optional<TaskResult> partialUpdate(TaskResult taskResult) {
        log.debug("Request to partially update TaskResult : {}", taskResult);

        return taskResultRepository
            .findById(taskResult.getId())
            .map(existingTaskResult -> {
                if (taskResult.getEmbeddeddata() != null) {
                    existingTaskResult.setEmbeddeddata(taskResult.getEmbeddeddata());
                }
                if (taskResult.getEmbeddeddataContentType() != null) {
                    existingTaskResult.setEmbeddeddataContentType(taskResult.getEmbeddeddataContentType());
                }
                if (taskResult.getAdded() != null) {
                    existingTaskResult.setAdded(taskResult.getAdded());
                }
                if (taskResult.getReviewed() != null) {
                    existingTaskResult.setReviewed(taskResult.getReviewed());
                }
                if (taskResult.getIpAddress() != null) {
                    existingTaskResult.setIpAddress(taskResult.getIpAddress());
                }
                if (taskResult.getHeaders() != null) {
                    existingTaskResult.setHeaders(taskResult.getHeaders());
                }
                if (taskResult.getUrl() != null) {
                    existingTaskResult.setUrl(taskResult.getUrl());
                }
                if (taskResult.getContent() != null) {
                    existingTaskResult.setContent(taskResult.getContent());
                }

                return existingTaskResult;
            })
            .map(taskResultRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TaskResult> findAll(Pageable pageable) {
        log.debug("Request to get all TaskResults");
        return taskResultRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TaskResult> findOne(Long id) {
        log.debug("Request to get TaskResult : {}", id);
        return taskResultRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TaskResult : {}", id);
        taskResultRepository.deleteById(id);
    }
}
