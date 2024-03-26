package com.training.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.training.bean.WorkoutBean;
import com.training.entity.Workout;
import com.training.exception.ResourceNotFoundException;
import com.training.repository.WorkoutRepository;
import com.training.service.WorkoutService;

@Service
public class WorkoutServiceImpl implements WorkoutService {

	@Autowired
	private WorkoutRepository workoutRepository;
	
	@Autowired
	private RestTemplate restTemplate; 
	
	
	@Override
	public void save(Workout workout) {
		workoutRepository.save(workout);
		System.out.println("Save success");
	}


	private WorkoutBean mapToWorkout(Workout workout) {
		WorkoutBean workoutBean = new WorkoutBean();

		workoutBean.setUsername(workout.getUsername());
	
		workoutBean.setWorkoutDate(workout.getWorkoutDate());
		
		return workoutBean;
		
	}

	@Override
	public void update(Workout workout) {
		Optional<Workout> optional=workoutRepository.findById(workout.getWorkoutId());
		if(optional.isPresent()) {
			workoutRepository.save(workout);
			
		}else {
			optional.orElseThrow(()->new ResourceNotFoundException("No Workout found to update for id : "+workout.getWorkoutId()));
		}
		
	}
	
	  @Override
	    public List<Workout> getWorkoutByUsername(String username) {
	       
	        return workoutRepository.findByUsername(username);
	    }
	
	@Override
	public Workout getById(Long id) {
		Optional<Workout> optional=workoutRepository.findById(id);
		if(optional.isPresent()) {
			System.out.println("Fetch success");
			return optional.get();
		}else {
			optional.orElseThrow(()->new ResourceNotFoundException("No Workout found to fetch for id : "+id));
		}
		return null;
	}
	
	
	@Override
	public List<Workout> getAll() {
		 return workoutRepository.findAll();
	}
	
	@Override
	public Workout delete(Long id) {
		Optional<Workout> optional=workoutRepository.findById(id);
		if(optional.isPresent()) {
			workoutRepository.deleteById(id);
			
			return optional.get();
		}else {
			optional.orElseThrow(()->new ResourceNotFoundException("No Workout found to delete for id : "+id));
		}
		return null;
	}

}
