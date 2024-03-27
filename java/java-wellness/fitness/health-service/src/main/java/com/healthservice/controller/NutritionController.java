package com.healthservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.healthservice.bean.ResponseDto;
import com.healthservice.bean.UserBean;
import com.healthservice.entity.Nutrition;
import com.healthservice.service.UserNutritionService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/nutrition")
@CrossOrigin("*")
public class NutritionController {

	@Autowired
	UserNutritionService service;

	@PostMapping("/save")
	public ResponseEntity<Nutrition> saveNewNutrition(@RequestBody Nutrition nutrition) {
		log.info("Saving nutrition: {}", nutrition);
		Nutrition saveNutrition = service.saveNewNutrition(nutrition);

		ResponseEntity<Nutrition> responseEntity = new ResponseEntity<>(saveNutrition, HttpStatus.CREATED);
		log.info("Saved nutrition successfully");
		return responseEntity;
	}

	@GetMapping("/getById/{nutritionId}")
	public ResponseEntity<ResponseDto> getById(@PathVariable Integer nutritionId) {
		log.info("Fetching nutrition by ID: {}", nutritionId);
		ResponseDto responseDto = service.getById(nutritionId);
		log.info("Fetched nutrition successfully");
		return ResponseEntity.ok(responseDto);
	}

	@GetMapping("/getAll")
	public List<Nutrition> getAll() {
		log.info("Fetching all nutrition entries");
		List<Nutrition> nutritionList = service.getAllNutrition();
		log.info("Fetched all nutrition entries successfully");
		return nutritionList;
	}

	@PutMapping("/updateById/{nutritionId}")
	public ResponseEntity<?> updateNutrition(@PathVariable Integer nutritionId, @RequestBody Nutrition nutrition) {
		try {
			log.info("Updating nutrition with ID: {}", nutritionId);
			Nutrition updatedNutrition = service.updateNutrition(nutritionId, nutrition);
			log.info("Updated nutrition successfully");
			return ResponseEntity.ok(updatedNutrition);
		} catch (IllegalArgumentException e) {
			log.error("Error updating nutrition: {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nutrition with ID " + nutritionId + " not found.");
		} catch (Exception e) {
			log.error("Error updating nutrition: {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while updating nutrition.");
		}
	}

	@DeleteMapping("/deleteById/{nutritionId}")
	public void deleteNutrition(@PathVariable Integer nutritionId) {
		log.info("Deleting nutrition with ID: {}", nutritionId);
		service.deleteById(nutritionId);
		log.info("Deleted nutrition successfully");
	}

	@GetMapping(path = "/getUsers")
	public ResponseEntity<List<UserBean>> getAllUsers() {
		log.info("Fetching all users");
		List<UserBean> userBeans = service.getUserBean();
		log.info("Fetched all users successfully");
		return new ResponseEntity<>(userBeans, HttpStatus.OK);
	}

	@GetMapping("/byName/{username}")
	public ResponseEntity<List<Nutrition>> getByUsername(@PathVariable String username) {
		log.info("Fetching nutrition entries by username: {}", username);
		List<Nutrition> nutritionList = service.getByUsername(username);
		log.info("Fetched nutrition entries by username successfully");
		return new ResponseEntity<>(nutritionList, HttpStatus.OK);
	}
}