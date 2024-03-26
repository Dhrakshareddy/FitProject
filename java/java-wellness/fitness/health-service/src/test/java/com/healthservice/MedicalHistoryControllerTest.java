package com.healthservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.healthservice.bean.ResponseDto;
import com.healthservice.controller.MedicalHistoryController;
import com.healthservice.entity.MedicalHistory;
import com.healthservice.service.MedicalHistoryService;

@ExtendWith(MockitoExtension.class)
class MedicalHistoryControllerTest {

	@Mock
	private MedicalHistoryService medicalHistoryService;

	@InjectMocks
	private MedicalHistoryController medicalHistoryController;

	@Test
	void saveNewMedicalHistoryTest() {
		MedicalHistory medicalHistory = new MedicalHistory();
		when(medicalHistoryService.saveNewMedicalHistory(any(MedicalHistory.class))).thenReturn(medicalHistory);

		ResponseEntity<MedicalHistory> response = medicalHistoryController.saveNewMedicalHistory(medicalHistory);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(medicalHistory, response.getBody());
		verify(medicalHistoryService, times(1)).saveNewMedicalHistory(medicalHistory);
	}

	@Test
	void getByIdTest() {
		Long medicalHistoryId = 1L;
		when(medicalHistoryService.getById(medicalHistoryId)).thenReturn(new ResponseDto());

		ResponseEntity<ResponseDto> response = medicalHistoryController.getById(medicalHistoryId);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		verify(medicalHistoryService, times(1)).getById(medicalHistoryId);
	}

	@Test
    void getAllTest() {
        when(medicalHistoryService.getAllMedicalHistory()).thenReturn(Collections.emptyList());

        List<MedicalHistory> historyList = medicalHistoryController.getAll();

        assertEquals(Collections.emptyList(), historyList);
        verify(medicalHistoryService, times(1)).getAllMedicalHistory();
    }

	@Test
	void updateMedicalHistoryTest() {
		MedicalHistory medicalHistory = new MedicalHistory();
		when(medicalHistoryService.updateMedicalHistory(any(MedicalHistory.class))).thenReturn(medicalHistory);

		MedicalHistory updatedMedicalHistory = medicalHistoryController.updateMedicalHistory(medicalHistory);

		assertEquals(medicalHistory, updatedMedicalHistory);
		verify(medicalHistoryService, times(1)).updateMedicalHistory(medicalHistory);
	}

	@Test
	void deleteMedicalHistoryTest() {
		Long medicalHistoryId = 1L;

		medicalHistoryController.deleteMedicalHistory(medicalHistoryId);

		verify(medicalHistoryService, times(1)).deleteById(medicalHistoryId);
	}

	@Test
	void getMedicalHistoryByNameTest() {
		String username = "testUser";
		List<MedicalHistory> medicalHistoryList = Collections
				.singletonList(new MedicalHistory(/* Set your test data here */));

		when(medicalHistoryService.getMedicalHistoryByName(username)).thenReturn(medicalHistoryList);

		ResponseEntity<List<MedicalHistory>> response = medicalHistoryController.getMedicalHistoryByName(username);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(medicalHistoryList, response.getBody());
		verify(medicalHistoryService, times(1)).getMedicalHistoryByName(username);
	}

}