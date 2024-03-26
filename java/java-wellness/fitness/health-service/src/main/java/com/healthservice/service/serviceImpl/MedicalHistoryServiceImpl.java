package com.healthservice.service.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.healthservice.bean.MedicalHistoryBean;
import com.healthservice.bean.ResponseDto;
import com.healthservice.bean.UserBean;
import com.healthservice.entity.MedicalHistory;
import com.healthservice.exception.ResourceNotFoundException;
import com.healthservice.repository.MedicalHistoryRepository;
import com.healthservice.service.MedicalHistoryService;

@Service
public class MedicalHistoryServiceImpl implements MedicalHistoryService {

	private static final Logger log = LoggerFactory.getLogger(MedicalHistoryServiceImpl.class);

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private MedicalHistoryRepository repository;

	@Override
	public List<MedicalHistory> getAllMedicalHistory() {
		log.info("Fetching all medical history records.");
		return repository.findAll();
	}

	@Override
	public MedicalHistory updateMedicalHistory(MedicalHistory bean) {
		log.info("Updating medical history with ID: {}", bean.getMedicalHistoryId());
		Optional<MedicalHistory> optional = repository.findById(bean.getMedicalHistoryId());
		if (optional.isPresent()) {
			repository.save(bean);
			log.info("Medical history updated successfully.");
		}

		return bean;
	}

	private MedicalHistoryBean mapToMedicalHistory(MedicalHistory medicalHistory) {
		MedicalHistoryBean medicalHistoryBean = new MedicalHistoryBean();
		medicalHistoryBean.setMedicalHistoryId(medicalHistory.getMedicalHistoryId());
		medicalHistoryBean.setDateOfAssessment(medicalHistory.getDateOfAssessment());

		return medicalHistoryBean;
	}

	@Override
	public MedicalHistory saveNewMedicalHistory(MedicalHistory medicalHistory) {
		log.info("Saving new medical history.");
		return repository.save(medicalHistory);
	}

	@Override
	public ResponseDto getById(Long medicalHistoryId) {
		log.info("Fetching medical history by ID: {}", medicalHistoryId);
		ResponseDto responseDto = new ResponseDto();

		MedicalHistory medicalHistory = repository.findById(medicalHistoryId).orElseThrow(
				() -> new ResourceNotFoundException("Medical history not found with ID: " + medicalHistoryId));

		org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		MedicalHistoryBean medicalHistoryBean = mapToMedicalHistory(medicalHistory);
		HttpEntity<String> httpEntity = new HttpEntity<>(headers);
		ResponseEntity<UserBean> responseEntity = restTemplate.exchange(
				"http://13.48.82.196:8402/api/user/getByName/" + medicalHistory.getUsername(), HttpMethod.GET, httpEntity,
				UserBean.class);
		UserBean userBean = responseEntity.getBody();
		medicalHistory.setUsername(userBean.getName());

		log.info("User details fetched successfully. Status code: {}", responseEntity.getStatusCode());

		responseDto.setMedicalHistory(medicalHistoryBean);
		responseDto.setUserBean(userBean);

		return responseDto;
	}

	@Override
	public void deleteById(Long medicalHistoryId) {
		
		log.info("Deleting medical history by ID: {}", medicalHistoryId);
		Optional<MedicalHistory> optional = repository.findById(medicalHistoryId);
		if (optional.isPresent()) {
			repository.deleteById(medicalHistoryId);
			log.info("Medical history deleted successfully.");
		} else {
			optional.orElseThrow(() -> new ResourceNotFoundException(
					"No medical history record found with ID: " + medicalHistoryId));
		}
	}

	@Override
	public List<MedicalHistory> getMedicalHistoryByName(String username) {
		log.info("Fetching medical history records by username: {}", username);
		return repository.findByUsername(username);
	}
}