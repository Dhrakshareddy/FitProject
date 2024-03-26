package com.management.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.management.bean.LoginBean;
import com.management.bean.MedicalHistoryBean;
import com.management.constants.ManagementConstants;
import com.management.entity.Trainer;
import com.management.entity.User;
import com.management.exception.PasswordMismatchException;
import com.management.exception.ResourceNotFoundException;
import com.management.repository.UserRepository;
import com.management.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public User saveUser(User user) {
        user.setRole(ManagementConstants.USERROLE);
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        Optional<User> optional = userRepository.findById(id);
        if (optional.isEmpty()) {
            try {
                optional.orElseThrow(() -> new ResourceNotFoundException("No user was found to fetch for the given ID:" + id));
            } catch (ResourceNotFoundException e) {
                log.error(e.getMessage());
                throw e;
            }
        }
        log.info("Fetched successfully");
        return optional.get();
    }

    @Override
    public List<User> getAll() {
        log.info("Fetching all users");
        return userRepository.findAll();
    }

    @Override
    public List<MedicalHistoryBean> getMedicalHistoryBean(String username) {
        String url = "http://13.48.82.196:8401/medicalHistory/fetchbyName/{username}";
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<List<MedicalHistoryBean>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity,
                new ParameterizedTypeReference<List<MedicalHistoryBean>>() {
                }, username);

        return responseEntity.getBody();
    }

    @Override
    public User validateLogin(LoginBean loginBean) {
        User user = userRepository.findByName(loginBean.getName());
        log.info("User found: {}", user);

        if (user != null) {
            User registrationBean = new User();

            if (user.getPassword().equals(loginBean.getPassword())) {
                log.info("Login successful");
                return user;
            } else {
                try {
                    throw new PasswordMismatchException("Incorrect password");
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
                return user;
            }
        } else {
            try {
                throw new UserNameNotFoundException("Incorrect EmailId");
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        return user;
    }

    @Override
    public User getUserByName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public User update(User user) {
        log.info("Updating user: {}", user);
        Optional<User> optional = userRepository.findById(user.getUserId());
        if (optional.isEmpty()) {
            try {
                optional.orElseThrow(() -> new ResourceNotFoundException("No user was found to update for the given ID:" + user.getUserId()));
            } catch (ResourceNotFoundException e) {
                log.error(e.getMessage());
                throw e;
            }
        }
        userRepository.save(user);
        log.info("Updated successfully");
        return user;
    }

    @Override
    public List<User> getUsersByTrainerCode(String trainerCode) {
        Optional<List<User>> optional = userRepository.findByTrainerCode(trainerCode);
        if (optional.isEmpty()) {
            try {
                optional.orElseThrow(() -> new ResourceNotFoundException("No user was found to fetch for the given ID:" + trainerCode));
            } catch (ResourceNotFoundException e) {
                log.error(e.getMessage());
                throw e;
            }
        }
        log.info("Fetched successfully");
        return optional.get();
    }

    @Override
    public User updatePassword(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            user.setPassword(password);
            userRepository.save(user);
        }
        return user;
    }

    @Override
    public User deleteById(Long userId) {
        Optional<User> optional = userRepository.findById(userId);
        if (optional.isEmpty()) {
            try {
                optional.orElseThrow(() -> new ResourceNotFoundException("No user was found to delete for the given ID:" + userId));
            } catch (ResourceNotFoundException e) {
                log.error(e.getMessage());
                throw e;
            }
        }
        userRepository.deleteById(userId);
        log.info("Deleted successfully");
        return optional.get();
    }
}
