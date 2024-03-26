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
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.healthservice.bean.NutritionBean;
import com.healthservice.bean.ResponseDto;
import com.healthservice.bean.UserBean;
import com.healthservice.entity.Nutrition;
import com.healthservice.exception.ResourceNotFoundException;
import com.healthservice.repository.UserNutritionRepository;
import com.healthservice.service.serviceImpl.UserNutritionServiceImpl;

public class NutritionServiceImplTest {

	@InjectMocks
	private UserNutritionServiceImpl userNutritionService;

	@Mock
	private RestTemplate restTemplate;

	@Mock
	private UserNutritionRepository repository;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testSaveNewNutrition() {
		Nutrition nutrition = new Nutrition();
		when(repository.save(any(Nutrition.class))).thenReturn(nutrition);

		Nutrition result = userNutritionService.saveNewNutrition(nutrition);

		assertNotNull(result);
		assertEquals(nutrition, result);
	}

	@Test
	void testGetAllNutrition() {
		List<Nutrition> nutritionList = Arrays.asList(new Nutrition(), new Nutrition());
		when(repository.findAll()).thenReturn(nutritionList);

		List<Nutrition> result = userNutritionService.getAllNutrition();

		assertEquals(nutritionList, result);
	}

	@Test
	void testUpdateNutrition() {
		Integer nutritionId = 1;
		Nutrition updatedNutrition = new Nutrition();
		Nutrition existingNutrition = new Nutrition();
		existingNutrition.setNutritionId(nutritionId);

		when(repository.findById(nutritionId)).thenReturn(Optional.of(existingNutrition));
		when(repository.save(any(Nutrition.class))).thenReturn(updatedNutrition);

		Nutrition result = userNutritionService.updateNutrition(nutritionId, updatedNutrition);

		assertEquals(updatedNutrition, result);
	}

	@Test
	void testDeleteById() {
		Integer nutritionId = 1;
		Nutrition nutrition = new Nutrition();
		nutrition.setNutritionId(nutritionId);

		when(repository.findById(nutritionId)).thenReturn(Optional.of(nutrition));

		userNutritionService.deleteById(nutritionId);

		verify(repository, times(1)).deleteById(eq(nutritionId));
	}

	@Test
	void testDeleteByIdNotFound() {
		Integer nutritionId = 1;

		when(repository.findById(nutritionId)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> userNutritionService.deleteById(nutritionId));
	}

	@Test
	void testGetByIdNotFound() {
		Integer nutritionId = 1;

		when(repository.findById(nutritionId)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> userNutritionService.getById(nutritionId));
	}

	@Test
	void testGetUserBean() {
		List<UserBean> userBeans = Arrays.asList(new UserBean(), new UserBean());
		when(restTemplate.exchange(any(String.class), eq(HttpMethod.GET), any(HttpEntity.class),
				eq(new ParameterizedTypeReference<List<UserBean>>() {
				}))).thenReturn(ResponseEntity.ok(userBeans));

		List<UserBean> result = userNutritionService.getUserBean();

		assertEquals(userBeans, result);
	}

	@Test
	void testGetByUsername() {
		String username = "TestUser";
		List<Nutrition> nutritionList = Arrays.asList(new Nutrition(), new Nutrition());

		when(repository.findByUsername(username)).thenReturn(Optional.of(nutritionList));

		List<Nutrition> result = userNutritionService.getByUsername(username);

		assertEquals(nutritionList, result);
	}
}