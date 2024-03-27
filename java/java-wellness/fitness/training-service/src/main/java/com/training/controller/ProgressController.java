package com.training.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.training.bean.ProgressCalculationRequest;
import com.training.entity.Exercise;
import com.training.entity.Progress;
import com.training.entity.Workout;
import com.training.service.ExerciseService;
import com.training.service.ProgressService;
import com.training.service.WorkoutService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(path = "/progress")
@CrossOrigin("*")
public class ProgressController {

	public static Logger log=LoggerFactory.getLogger(Progress.class.getSimpleName());

	@Autowired
	private ProgressService progressService;
	
	@Autowired
	private WorkoutService workoutService;
	
	@Autowired
	private ExerciseService exerciseService;

	@PostMapping("/save")
	public ResponseEntity<Progress> save(@RequestBody Progress progress) {

		log.info("Progress saved {}", progress);
		progressService.save(progress);
	


		ResponseEntity<Progress> responseEntity = new ResponseEntity<>(progress, HttpStatus.CREATED);
		return responseEntity;

	}

	 

	@PostMapping("/calculate")
    public ResponseEntity<Double> calculateProgress(@RequestBody ProgressCalculationRequest request) {
        try {
            double progress = progressService.calculateProgress(request.getExercises(), request.getWorkouts());
            return new ResponseEntity<>(progress, HttpStatus.OK);
        } catch (Exception e) {
            
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	    
	
	  
	  @GetMapping("/forworkoutbyname/{username}")
		public ResponseEntity<List<Workout>> getWorkoutByName(@PathVariable String username) {
		  
		  System.out.println(username);
		    List<Workout> workout = workoutService.getWorkoutByUsername(username);
		    if (workout.isEmpty()) {
		        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		    }
		    return new ResponseEntity<>(workout, HttpStatus.OK);
		}

	  
	  @GetMapping("/forexercisebyname/{username}")
		public ResponseEntity<List<Exercise>> getExerciseByName(@PathVariable String username) {
		  
		  System.out.println(username);
		    List<Exercise> exercise = exerciseService.getExerciseByUsername(username);
		    System.out.println(exercise);
		    if (exercise.isEmpty()) {
		        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		    }
		    return new ResponseEntity<>(exercise, HttpStatus.OK);
		}

	  
	  @GetMapping("/fetchbyName/{username}")
	    public ResponseEntity<List<Progress>> getProgressByUsername(@PathVariable String username) {
	        List<Progress> progress = progressService.getProgressByUsername(username);
	        return new ResponseEntity<>(progress, HttpStatus.OK);
	    }
	  
}
