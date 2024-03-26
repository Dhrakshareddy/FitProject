package com.management.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.management.bean.LoginBean;
import com.management.bean.MedicalHistoryBean;
import com.management.bean.UserBean;
import com.management.bean.UserDto;
import com.management.entity.User;

import jakarta.persistence.Id;

public interface UserService {

	User update(User user);

	User saveUser(User user);

	public List<MedicalHistoryBean> getMedicalHistoryBean(String username);

	User getUserById(Long userId);

	List<User> getAll();

	User deleteById(Long userId);

	User validateLogin(LoginBean loginBean);

	List<User> getUsersByTrainerCode(String trainerCode);

	User getUserByName(String name);

	User updatePassword(String email, String password);

}
