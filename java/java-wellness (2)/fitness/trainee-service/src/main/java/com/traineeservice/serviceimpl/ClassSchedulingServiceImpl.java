package com.traineeservice.serviceimpl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.traineeservice.bean.ClassSchedulingBean;
import com.traineeservice.bean.ResponseDto;
import com.traineeservice.bean.TrainerBean;
import com.traineeservice.entity.ClassScheduling;
import com.traineeservice.exception.NoSuchRecordFoundException;
import com.traineeservice.repository.ClassSchedulingRepository;
import com.traineeservice.service.ClassSchedulingService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ClassSchedulingServiceImpl implements ClassSchedulingService {

	@Autowired
	private ClassSchedulingRepository classSchedulingRepository;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public List<ClassScheduling> getOneWeekClassScheduling() {
		log.info("Fetching class scheduling for the next week.");
		
		Date currentDate = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);

		calendar.add(Calendar.DAY_OF_YEAR, 7);
		Date oneWeekLater = calendar.getTime();

		
		List<ClassScheduling> result = classSchedulingRepository.findByDateBetween(currentDate, oneWeekLater);
		log.info("Fetched {} class scheduling entries for the next week.", result.size());
		return result;
	}

	private ClassSchedulingBean maptoClassScheduling(ClassScheduling classScheduling) {
		ClassSchedulingBean classSchedulingBean = new ClassSchedulingBean();
		classSchedulingBean.setClassId(classScheduling.getClassId());
		classSchedulingBean.setName(classScheduling.getName());
		classSchedulingBean.setClassName(classScheduling.getClassName());
		classSchedulingBean.setDate(classScheduling.getDate());
		classSchedulingBean.setDuration(classScheduling.getDuration());
		classSchedulingBean.setEnrolled(classScheduling.getEnrolled());
		classSchedulingBean.setTrainerId(classScheduling.getTrainerId());
		return classSchedulingBean;
	}

	@Override
	public ResponseDto getClassscheduling(Long classId) {
		log.info("Fetching class scheduling details for class ID: {}", classId);
		ResponseDto responseDto = new ResponseDto();
		ClassScheduling classScheduling = classSchedulingRepository.findById(classId).get();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		ClassSchedulingBean classSchedulingBean = maptoClassScheduling(classScheduling);
		HttpEntity<String> httpEntity = new HttpEntity<String>(headers);
		ResponseEntity<TrainerBean> responseEntity = restTemplate.exchange(
				"http://13.48.82.196:8405/trainer/getById/" + classScheduling.getTrainerId(), HttpMethod.GET, httpEntity,
				TrainerBean.class);
		TrainerBean trainerBean = responseEntity.getBody();
		responseDto.setTrainerBean(trainerBean);
		responseDto.setClassSchedulingBean(classSchedulingBean);
		log.info("Fetched class scheduling details successfully.");
		return responseDto;
	}

	@Override
	public ClassScheduling saveclClassScheduling(ClassScheduling classScheduling) {
		log.info("Saving class scheduling entry: {}", classScheduling);
		ClassScheduling savedClassScheduling = classSchedulingRepository.save(classScheduling);
		log.info("Saved class scheduling entry: {}", savedClassScheduling);
		return savedClassScheduling;
	}

	@Override
	public List<ClassScheduling> getAll() {
		log.info("Fetching all class scheduling entries.");
		List<ClassScheduling> result = classSchedulingRepository.findAll();
		log.info("Fetched {} class scheduling entries.", result.size());
		return result;
	}

	@Override
	public String deleteById(Long id) {
		log.info("Deleting class scheduling entry with ID: {}", id);
		Optional<ClassScheduling> optional = classSchedulingRepository.findById(id);
		if (optional.isEmpty()) {
			try {
				optional.orElseThrow(() -> new NoSuchRecordFoundException("Provide correct details "));
			} catch (NoSuchRecordFoundException e) {
				log.error("Error deleting class scheduling entry. {}", e.getMessage());
				throw e;
			}
		} else {
			classSchedulingRepository.deleteById(id);
			log.info("Deleted class scheduling entry successfully.");
		}

		return "deleted";
	}

	@Override
	public void deleteAll() {
		log.info("Deleting all class scheduling entries.");
		classSchedulingRepository.deleteAll();
		log.info("Deleted all class scheduling entries successfully.");
	}

	@Override
	public ClassScheduling update(ClassScheduling scheduling) {
		log.info("Updating class scheduling entry: {}", scheduling);
		Optional<ClassScheduling> optional = classSchedulingRepository.findById(scheduling.getClassId());
		if (optional.isEmpty()) {
			try {
				optional.orElseThrow(() -> new NoSuchRecordFoundException("Provide correct details "));
			} catch (NoSuchRecordFoundException e) {
				log.error("Error updating class scheduling entry. {}", e.getMessage());
				throw e;
			}
		} else {
			classSchedulingRepository.save(scheduling);
			log.info("Updated class scheduling entry successfully.");
		}
		return scheduling;
	}

	@Override
	public List<ClassScheduling> getClassesByName(String name) {
		log.info("Fetching class scheduling entries by name: {}", name);
		List<ClassScheduling> result = classSchedulingRepository.getClassesByName(name);
		log.info("Fetched {} class scheduling entries by name: {}", result.size(), name);
		return result;
	}
}