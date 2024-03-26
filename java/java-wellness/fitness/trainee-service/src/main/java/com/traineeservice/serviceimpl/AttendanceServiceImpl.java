package com.traineeservice.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.traineeservice.bean.AttendanceBean;
import com.traineeservice.bean.ResponseDto;
import com.traineeservice.bean.UserBean;
import com.traineeservice.entity.Attendance;
import com.traineeservice.exception.NoSuchRecordFoundException;
import com.traineeservice.repository.AttendanceRepository;
import com.traineeservice.service.AttendanceService;

@Service
public class AttendanceServiceImpl implements AttendanceService {

	private static final Logger log = LoggerFactory.getLogger(AttendanceServiceImpl.class);

	@Autowired
	private AttendanceRepository attendanceRepository;

	@Autowired
	private RestTemplate restTemplate;

	private AttendanceBean mapToAttendance(Attendance attendance) {
		log.info("Entering mapToAttendance method");
		AttendanceBean attendanceBean = new AttendanceBean();
		attendanceBean.setAttendanceId(attendance.getAttendanceId());
		attendanceBean.setDate(attendance.getDate());
		attendanceBean.setStatus(attendance.getStatus());
		attendanceBean.setFeedback(attendance.getFeedback());
		attendanceBean.setUserId(attendance.getUserId());
		log.info("Exiting mapToAttendance method");
		return attendanceBean;
	}

	@Override
	public ResponseDto getAttendance(Long attendanceId) {
		log.info("Entering getAttendance method with ID: {}", attendanceId);
		ResponseDto responseDto = new ResponseDto();
		Attendance attendance = attendanceRepository.findById(attendanceId)
				.orElseThrow(() -> new NoSuchRecordFoundException("No attendance found with ID: " + attendanceId));
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		AttendanceBean attendanceBean = mapToAttendance(attendance);
		HttpEntity<String> httpEntity = new HttpEntity<>(headers);
		ResponseEntity<UserBean> responseEntity = restTemplate.exchange(
				"http://13.48.82.196:8402/api/user/getById/" + attendance.getUserId(), HttpMethod.GET, httpEntity,
				UserBean.class);
		UserBean userBean = responseEntity.getBody();
		responseDto.setAttendanceBean(attendanceBean);
		responseDto.setUserBean(userBean);
		log.info("Exiting getAttendance method");
		return responseDto;
	}

	@Override
	public Attendance saveAttendance(Attendance attendance) {
		log.info("Entering saveAttendance method with attendance: {}", attendance);
		String name = attendance.getName();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		AttendanceBean attendanceBean = mapToAttendance(attendance);
		HttpEntity<String> httpEntity = new HttpEntity<>(headers);
		ResponseEntity<UserBean> responseEntity = restTemplate.exchange(
				"http://13.48.82.196:8402/api/user/getByName/" + attendance.getName(), HttpMethod.GET, httpEntity,
				UserBean.class);
		UserBean userBean = responseEntity.getBody();
		attendance.setUserId(userBean.getUserId());
		Attendance savedAttendance = attendanceRepository.save(attendance);
		log.info("Saved attendance with ID: {}", savedAttendance.getAttendanceId());
		log.info("Exiting saveAttendance method");
		return savedAttendance;
	}

	@Override
	public List<Attendance> getAttendanceByName(String name) {
		log.info("Entering getAttendanceByName method with name: {}", name);
		List<Attendance> result = attendanceRepository.findByName(name);
		log.info("Exiting getAttendanceByName method");
		return result;
	}

	@Override
	public List<Attendance> getAll() {
		log.info("Entering getAll method");
		List<Attendance> result = attendanceRepository.findAll();
		log.info("Exiting getAll method");
		return result;
	}

	@Override
	public String deleteById(Long attendanceId) {
		log.info("Entering deleteById method with ID: {}", attendanceId);
		Optional<Attendance> optional = attendanceRepository.findById(attendanceId);
		if (optional.isEmpty()) {
			throw new NoSuchRecordFoundException("No attendance found with ID: " + attendanceId);
		} else {
			attendanceRepository.deleteById(attendanceId);
		}
		log.info("Exiting deleteById method");
		return "deleted";
	}

	@Override
	public void deleteAll() {
		log.info("Entering deleteAll method");
		attendanceRepository.deleteAll();
		log.info("Exiting deleteAll method");
	}

	@Override
	public Attendance update(Attendance attendance) {
		log.info("Entering update method with attendance: {}", attendance);
		Optional<Attendance> optional = attendanceRepository.findById(attendance.getAttendanceId());
		if (optional.isEmpty()) {
			throw new NoSuchRecordFoundException("No attendance found with ID: " + attendance.getAttendanceId());
		} else {
			attendanceRepository.save(attendance);
		}
		log.info("Exiting update method");
		return attendance;
	}

}