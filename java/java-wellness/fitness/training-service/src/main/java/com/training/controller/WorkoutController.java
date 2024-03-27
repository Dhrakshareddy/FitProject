package com.training.controller;

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

import com.training.bean.WorkoutBean;
import com.training.entity.Workout;
import com.training.service.WorkoutService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(path = "/workout")
@CrossOrigin("*")
public class WorkoutController {

	public static Logger log=LoggerFactory.getLogger(Workout.class.getSimpleName());

	@Autowired
	private WorkoutService workoutService;

	@PostMapping("/save")
	public ResponseEntity<Workout> save(@RequestBody Workout workout) {
		workoutService.save(workout);

		log.info("Workout saved {}", workout);

		ResponseEntity<Workout> responseEntity = new ResponseEntity<>(workout, HttpStatus.CREATED);
		return responseEntity;

	}

	@PutMapping("/updateById")
	public ResponseEntity<Workout> update(@RequestBody Workout workout) {
		workoutService.update(workout);

		log.info("Workout updated {}", workout);

		ResponseEntity<Workout> responseEntity = new ResponseEntity<>(workout, HttpStatus.OK);
		return responseEntity;

	}

	@GetMapping("/getById/{id}")
	public ResponseEntity<Workout> getById(@PathVariable Long id) {
		Workout workout = workoutService.getById(id);

		log.info("Workout fetched {}", id);

		ResponseEntity<Workout> responseEntity = new ResponseEntity<>(workout, HttpStatus.OK);
		return responseEntity;

	}
	


	@GetMapping("/getAll")
	public ResponseEntity<List<Workout>> getAll() {
		List<Workout> workouts = workoutService.getAll();

		log.info("List of workouts : {}", workouts);

		ResponseEntity<List<Workout>> responseEntities = new ResponseEntity<List<Workout>>(workouts, HttpStatus.OK);
		return responseEntities;
	}

	@DeleteMapping("/deleteById/{id}")
	public ResponseEntity<Workout> delete(@PathVariable Long id) {
		Workout workout = workoutService.delete(id);

		log.info("Workout deleted {}", id);

		ResponseEntity<Workout> responseEntity = new ResponseEntity<>(workout, HttpStatus.OK);
		return responseEntity;
	}
	
	
	  @GetMapping("/fetchbyName/{username}")
	    public ResponseEntity<List<Workout>> getWorkoutByUsername(@PathVariable String username) {
	        List<Workout> workouts = workoutService.getWorkoutByUsername(username);
	        return new ResponseEntity<>(workouts, HttpStatus.OK);
	    }
	  
	  
	 

}
