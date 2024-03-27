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
import com.training.entity.Exercise;
import com.training.entity.Workout;
import com.training.service.ExerciseService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(path = "/exercise")
@CrossOrigin("*")
public class ExerciseController {

	public static Logger log=LoggerFactory.getLogger(Exercise.class.getSimpleName());

	@Autowired
	private ExerciseService exerciseService;

	@PostMapping(path = "/save")
	public ResponseEntity<Exercise> save(@RequestBody Exercise exercise) {
		exerciseService.save(exercise);

		log.info("Exercise saved {}", exercise);

		ResponseEntity<Exercise> responseEntity = new ResponseEntity<>(exercise, HttpStatus.CREATED);
		return responseEntity;

	}

	@PutMapping(path = "/updateById")
	public ResponseEntity<Exercise> update(@RequestBody Exercise exercise) {
		exerciseService.update(exercise);

		log.info("Exercise updated {}", exercise);

		ResponseEntity<Exercise> responseEntity = new ResponseEntity<>(exercise, HttpStatus.OK);
		return responseEntity;
		
	}

	@GetMapping(path = "/getById/{id}")
	public ResponseEntity<Exercise> getById(@PathVariable Long id) {
		Exercise exercise = exerciseService.getById(id);

		log.info("Exercise fetched {}", id);

		ResponseEntity<Exercise> responseEntity = new ResponseEntity<>(exercise, HttpStatus.OK);
		return responseEntity;

	}

	@GetMapping(path = "/getAll")
	public ResponseEntity<List<Exercise>> getAll() {
		List<Exercise> exercises = exerciseService.getAll();
		
		log.info("List of exercises : {}", exercises);
		
		ResponseEntity<List<Exercise>> responseEntities = new ResponseEntity<List<Exercise>>(exercises, HttpStatus.OK);
		return responseEntities;
	}

	@DeleteMapping(path = "/deleteById/{id}")
	public ResponseEntity<Exercise> delete(@PathVariable Long id) {
		Exercise exercise = exerciseService.delete(id);

		log.info("Exercise deleted {}", id);

		ResponseEntity<Exercise> responseEntity = new ResponseEntity<>(exercise, HttpStatus.OK);
		return responseEntity;

	}
	
	  @GetMapping("/fetchbyName/{username}")
	    public ResponseEntity<List<Exercise>> getWorkoutByUsername(@PathVariable String username) {
	        List<Exercise> exercise = exerciseService.getExerciseByUsername(username);
	        return new ResponseEntity<>(exercise, HttpStatus.OK);
	    }
	
	
}
