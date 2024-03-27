package com.traineeservice.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.traineeservice.bean.ResponseDto;
import com.traineeservice.entity.ClassScheduling;
import com.traineeservice.service.ClassSchedulingService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(path = "classSchedular")
@CrossOrigin("*")

public class ClassSechedularController {

	private static final Logger log = LoggerFactory.getLogger(ClassSechedularController.class);

	@Autowired
	private ClassSchedulingService classSchedulingService;

	/**
	 * 
	 * @return
	 */
	@GetMapping("/weekly")
	public ResponseEntity<List<ClassScheduling>> getOneWeekClassScheduling() {
		log.info("Fetching one week class scheduling");
		List<ClassScheduling> oneWeekClassScheduling = classSchedulingService.getOneWeekClassScheduling();
		log.info("Fetched one week class scheduling successfully");
		return ResponseEntity.ok(oneWeekClassScheduling);
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<ResponseDto> getClassScheduling(@PathVariable("id") Long classId) {
		log.info("Fetching class scheduling by ID: {}", classId);
		ResponseDto responseDto = classSchedulingService.getClassscheduling(classId);
		log.info("Fetched class scheduling successfully");
		return ResponseEntity.ok(responseDto);
	}

	@PostMapping(path = "/save")
	public ResponseEntity<ClassScheduling> saveAchievement(@RequestBody ClassScheduling classScheduling) {
		log.info("Saving class scheduling: {}", classScheduling);
		ClassScheduling classScheduling2 = classSchedulingService.saveclClassScheduling(classScheduling);
		log.info("Class scheduling saved successfully");

		ResponseEntity<ClassScheduling> responseEntity = new ResponseEntity<>(classScheduling2, HttpStatus.CREATED);
		return responseEntity;
	}

	@GetMapping("/byName/{name}")
	public ResponseEntity<List<ClassScheduling>> getClassesByName(@PathVariable String name) {
		log.info("Fetching classes by name: {}", name);
		List<ClassScheduling> classList = classSchedulingService.getClassesByName(name);
		if (classList.isEmpty()) {
			log.info("No classes found for name: {}", name);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		log.info("Fetched classes by name successfully");
		return new ResponseEntity<>(classList, HttpStatus.OK);
	}

	@GetMapping(path = "/getAll")
	public ResponseEntity<List<ClassScheduling>> getAll() {
		log.info("Getting all schedules");
		List<ClassScheduling> scheduling = classSchedulingService.getAll();
		log.info("Fetched all schedules successfully");
		ResponseEntity<List<ClassScheduling>> responseEntity = new ResponseEntity<>(scheduling, HttpStatus.OK);
		return responseEntity;
	}

	@DeleteMapping(path = "/deleteById/{id}")
	public String deleteById(@PathVariable Long id) {
		log.info("Deleting schedule with ID: {}", id);
		classSchedulingService.deleteById(id);
		log.info("Deleted schedule successfully");
		return "Deleted successfully";
	}

	@DeleteMapping(path = "/deleteAll")
	public void deleteAll() {
		log.info("Deleting all schedules");
		classSchedulingService.deleteAll();
		log.info("Deleted all schedules successfully");
	}

	@PutMapping(path = "/updateById/{id}")
	public ResponseEntity<ClassScheduling> update(@RequestBody ClassScheduling classScheduling) {
		log.info("Updating class scheduling with ID: {}", classScheduling.getClassId());
		ClassScheduling scheduleUpdate = classSchedulingService.update(classScheduling);
		log.info("Updated class scheduling successfully");
		ResponseEntity<ClassScheduling> responseEntity = new ResponseEntity<>(scheduleUpdate, HttpStatus.OK);
		return responseEntity;
	}
}