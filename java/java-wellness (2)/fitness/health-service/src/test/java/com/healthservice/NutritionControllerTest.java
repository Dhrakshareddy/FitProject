package com.healthservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import com.healthservice.bean.UserBean;
import com.healthservice.controller.NutritionController;
import com.healthservice.entity.Nutrition;
import com.healthservice.service.UserNutritionService;

@ExtendWith(MockitoExtension.class)
class NutritionControllerTest {

	@Mock
	private UserNutritionService userNutritionService;

	@InjectMocks
	private NutritionController nutritionController;

	@Test
	void saveNewNutritionTest() {
		Nutrition nutrition = new Nutrition();
		when(userNutritionService.saveNewNutrition(nutrition)).thenReturn(nutrition);

		ResponseEntity<Nutrition> responseEntity = nutritionController.saveNewNutrition(nutrition);

		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals(nutrition, responseEntity.getBody());
		verify(userNutritionService, times(1)).saveNewNutrition(nutrition);
	}

	@Test
	void getByIdTest() {
		Integer nutritionId = 1;
		ResponseDto responseDto = new ResponseDto();
		when(userNutritionService.getById(nutritionId)).thenReturn(responseDto);

		ResponseEntity<ResponseDto> responseEntity = nutritionController.getById(nutritionId);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(responseDto, responseEntity.getBody());
		verify(userNutritionService, times(1)).getById(nutritionId);
	}

	@Test
	void getAllTest() {
		List<Nutrition> nutritionList = Collections.singletonList(new Nutrition());
		when(userNutritionService.getAllNutrition()).thenReturn(nutritionList);

		List<Nutrition> result = nutritionController.getAll();

		verify(userNutritionService, times(1)).getAllNutrition();
	}

	@Test
	void updateNutritionTest() {
		Integer nutritionId = 1;
		Nutrition nutrition = new Nutrition();
		when(userNutritionService.updateNutrition(nutritionId, nutrition)).thenReturn(nutrition);

		ResponseEntity<?> responseEntity = nutritionController.updateNutrition(nutritionId, nutrition);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(nutrition, responseEntity.getBody());
		verify(userNutritionService, times(1)).updateNutrition(nutritionId, nutrition);
	}

	@Test
	void getAllUsersTest() {
		List<UserBean> userBeans = Collections.singletonList(new UserBean());
		when(userNutritionService.getUserBean()).thenReturn(userBeans);

		ResponseEntity<List<UserBean>> responseEntity = nutritionController.getAllUsers();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(userBeans, responseEntity.getBody());
		verify(userNutritionService, times(1)).getUserBean();
	}

	@Test
	void getByUsernameTest() {
		String username = "testUser";
		List<Nutrition> nutritionList = Collections.singletonList(new Nutrition());
		when(userNutritionService.getByUsername(username)).thenReturn(nutritionList);

		ResponseEntity<List<Nutrition>> responseEntity = nutritionController.getByUsername(username);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(nutritionList, responseEntity.getBody());
		verify(userNutritionService, times(1)).getByUsername(username);
	}
}
