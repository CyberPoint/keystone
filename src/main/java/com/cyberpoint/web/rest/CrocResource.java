package com.cyberpoint.web.rest;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import com.cyberpoint.domain.Task;
import com.cyberpoint.domain.TaskResult;
import com.cyberpoint.repository.TaskRepository;
import com.cyberpoint.service.AgentService;
import com.cyberpoint.service.CallBackService;
import com.cyberpoint.service.PlatformService;
import com.cyberpoint.service.RegistrationEventService;
import com.cyberpoint.service.RegistrationSecretService;
import com.cyberpoint.service.TaskResultService;
import com.cyberpoint.service.TaskService;

import jakarta.servlet.http.HttpServletRequest;
import tech.jhipster.web.util.PaginationUtil;

/**
 * CrocResource controller
 */
@RestController
@RequestMapping("/api/crocs")
public class CrocResource {

    private final Logger log = LoggerFactory.getLogger(CrocResource.class);
    
 
    private TaskResultService taskResultService;
    
    private TaskService taskService;

    private TaskRepository taskRepository;


	private CallBackService callBackService;


	private AgentService agentService;


	private PlatformService platformService;


	private RegistrationSecretService registrationSecretService;


	private RegistrationEventService registrationEventService;
	
	@Autowired
    private TaskResultService taskResultService1;
    /**
     * GET defaultAction
     */
    @GetMapping("/debug")
    public String defaultAction() {
        return "Hello world";
    }

    @GetMapping("/keroppi")
    public ResponseEntity<List<Task>> getAllTasks(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Tasks");
        Page<Task> page = taskService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
    
    @PostMapping("/results")
    public ResponseEntity<Task> updateTask(@RequestBody Task taskResult, HttpServletRequest request) {
        Optional<Task> existingTask = taskService.findOne(taskResult.getId());
        if (existingTask.isPresent()) {
            Task task = existingTask.get();
            task.setApproved(taskResult.getApproved());
            task.setFailure(taskResult.getFailure());
            task.setRetrieved(taskResult.getRetrieved());

            String clientIp = request.getRemoteAddr();
            task.setDescription(clientIp);

            taskService.save(task);
            return ResponseEntity.ok(task);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    
    public CrocResource(
    	    CallBackService callBackService,
    	    RegistrationSecretService registrationSecretService,
    	    RegistrationEventService registrationEventService,
    	    TaskService taskService,
    	    TaskResultService taskResultService,
    	    AgentService agentService,
    	    PlatformService platformService) {
    	    this.callBackService = callBackService;
    	    this.taskService = taskService;
    	    this.taskResultService = taskResultService;
    	    this.agentService = agentService;
    	    this.platformService = platformService;
    	    this.registrationSecretService = registrationSecretService;
    	    this.registrationEventService = registrationEventService;
    	}


}


