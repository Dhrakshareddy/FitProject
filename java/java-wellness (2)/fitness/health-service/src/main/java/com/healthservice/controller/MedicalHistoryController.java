package com.healthservice.controller;

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

import com.healthservice.bean.ResponseDto;
import com.healthservice.entity.MedicalHistory;
import com.healthservice.service.MedicalHistoryService;

@RestController
@RequestMapping("/medicalHistory")
@CrossOrigin(origins = "http://13.48.82.196:4200")
public class MedicalHistoryController {

	private static final Logger log = LoggerFactory.getLogger(MedicalHistoryController.class);

	@Autowired
	MedicalHistoryService service;

	@PostMapping("/save")
	public ResponseEntity<MedicalHistory> saveNewMedicalHistory(@RequestBody MedicalHistory medicalHistory) {
		log.info("Starting saveNewMedicalHistory method");
		MedicalHistory saveMedicalHistory = service.saveNewMedicalHistory(medicalHistory);
		log.info("Ending saveNewMedicalHistory method");
		return new ResponseEntity<>(saveMedicalHistory, HttpStatus.CREATED);
	}

	@GetMapping("/getById/{medicalHistoryId}")
	public ResponseEntity<ResponseDto> getById(@PathVariable Long medicalHistoryId) {
		log.info("Starting getById method");
		ResponseDto responseDto = service.getById(medicalHistoryId);
		log.info("Ending getById method");
		return ResponseEntity.ok(responseDto);
	}

	@GetMapping("/getAll")
	public List<MedicalHistory> getAll() {
		log.info("Starting getAll method");
		List<MedicalHistory> historyList = service.getAllMedicalHistory();
		log.info("Ending getAll method");
		return historyList;
	}

	@PutMapping("/updateById")
	public MedicalHistory updateMedicalHistory(@RequestBody MedicalHistory bean) {
		log.info("Starting updateMedicalHistory method");
		MedicalHistory updatedMedicalHistory = service.updateMedicalHistory(bean);
		log.info("Ending updateMedicalHistory method");
		return updatedMedicalHistory;
	}

	@DeleteMapping("/deleteById/{medicalHistoryId}")
	public void deleteMedicalHistory(@PathVariable Long medicalHistoryId) {
		log.info("Starting deleteMedicalHistory method");
		service.deleteById(medicalHistoryId);
		log.info("Ending deleteMedicalHistory method");
	}

	@GetMapping("/fetchbyName/{username}")
	public ResponseEntity<List<MedicalHistory>> getMedicalHistoryByName(@PathVariable String username) {
		log.info("Starting getMedicalHistoryByName method");
		List<MedicalHistory> historyList = service.getMedicalHistoryByName(username);
		log.info("Ending getMedicalHistoryByName method");
		if (historyList.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(historyList, HttpStatus.OK);
	}

}