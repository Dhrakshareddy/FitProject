package com.healthservice;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.healthservice.bean.ResponseDto;
import com.healthservice.bean.UserBean;
import com.healthservice.entity.MedicalHistory;
import com.healthservice.exception.ResourceNotFoundException;
import com.healthservice.repository.MedicalHistoryRepository;
import com.healthservice.service.serviceImpl.MedicalHistoryServiceImpl;

public class MedicalHistoryServiceImplTest {

	@InjectMocks
	private MedicalHistoryServiceImpl medicalHistoryService;

	@Mock
	private RestTemplate restTemplate;

	@Mock
	private MedicalHistoryRepository repository;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
    void testGetAllMedicalHistory() {
        when(repository.findAll()).thenReturn(Arrays.asList(new MedicalHistory(), new MedicalHistory()));

        List<MedicalHistory> result = medicalHistoryService.getAllMedicalHistory();

        assertEquals(2, result.size());
    }

	@Test
	void testUpdateMedicalHistory() {
		MedicalHistory medicalHistory = new MedicalHistory();
		medicalHistory.setMedicalHistoryId(1L);

		when(repository.findById(1L)).thenReturn(Optional.of(medicalHistory));
		when(repository.save(any(MedicalHistory.class))).thenReturn(medicalHistory);

		MedicalHistory result = medicalHistoryService.updateMedicalHistory(medicalHistory);

		assertEquals(medicalHistory, result);
	}

	@Test
	void testSaveNewMedicalHistory() {
		MedicalHistory medicalHistory = new MedicalHistory();

		when(repository.save(any(MedicalHistory.class))).thenReturn(medicalHistory);

		MedicalHistory result = medicalHistoryService.saveNewMedicalHistory(medicalHistory);

		assertEquals(medicalHistory, result);
	}

	@Test
	void testGetById() {
		Long medicalHistoryId = 1L;
		MedicalHistory medicalHistory = new MedicalHistory();
		medicalHistory.setMedicalHistoryId(medicalHistoryId);

		when(repository.findById(medicalHistoryId)).thenReturn(Optional.of(medicalHistory));

		UserBean expectedUserBean = new UserBean();
		expectedUserBean.setName("TestUser");

		ResponseEntity<UserBean> responseEntity = ResponseEntity.ok(expectedUserBean);

		when(restTemplate.exchange(any(String.class), eq(HttpMethod.GET), any(HttpEntity.class), eq(UserBean.class)))
				.thenReturn(responseEntity);

		ResponseDto responseDto = medicalHistoryService.getById(medicalHistoryId);

		assertNotNull(responseDto);
		assertEquals(medicalHistoryId, responseDto.getMedicalHistory().getMedicalHistoryId());
		assertEquals("TestUser", responseDto.getUserBean().getName());
	}

	@Test
	void testGetByIdNotFound() {
		Long medicalHistoryId = 1L;

		when(repository.findById(medicalHistoryId)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> medicalHistoryService.getById(medicalHistoryId));
	}

	@Test
	void testGetMedicalHistoryByName() {
		String username = "TestUser";
		List<MedicalHistory> medicalHistoryList = Arrays.asList(new MedicalHistory(), new MedicalHistory());

		when(repository.findByUsername(username)).thenReturn(medicalHistoryList);

		List<MedicalHistory> result = medicalHistoryService.getMedicalHistoryByName(username);

		assertEquals(2, result.size());
	}
}