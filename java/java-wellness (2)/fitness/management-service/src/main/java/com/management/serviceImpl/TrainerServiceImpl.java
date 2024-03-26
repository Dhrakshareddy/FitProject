package com.management.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.management.bean.TrainerLoginBean;
import com.management.constants.ManagementConstants;
import com.management.entity.Trainer;
import com.management.exception.PasswordMismatchException;
import com.management.exception.ResourceNotFoundException;
import com.management.repository.TrainerRepository;
import com.management.service.TrainerService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TrainerServiceImpl implements TrainerService {

    @Autowired
    private TrainerRepository trainerRepository;

    @Override
    public void save(Trainer trainer) {
        trainer.setRole(ManagementConstants.TRAINERROLE);
        trainerRepository.save(trainer);
        log.info("Saved successfully");
    }

    @Override
    public Trainer get(Long trainerId) {
        Optional<Trainer> optional = trainerRepository.findById(trainerId);
        if (optional.isEmpty()) {
            try {
                optional.orElseThrow(() -> new ResourceNotFoundException("No trainer was found to fetch for the given ID:" + trainerId));
            } catch (ResourceNotFoundException e) {
                log.error(e.getMessage());
                throw e;
            }
        }
        log.info("Fetched successfully");
        return optional.get();
    }

    @Override
    public List<Trainer> getAll() {
        log.info("Fetching all trainers");
        return trainerRepository.findAll();
    }

    @Override
    public Trainer deleteById(Long trainerId) {
        Optional<Trainer> optional = trainerRepository.findById(trainerId);
        if (optional.isEmpty()) {
            try {
                optional.orElseThrow(() -> new ResourceNotFoundException("No trainer was found to delete for the given ID:" + trainerId));
            } catch (ResourceNotFoundException e) {
                log.error(e.getMessage());
                throw e;
            }
        }
        trainerRepository.deleteById(trainerId);
        log.info("Deleted successfully");
        return optional.get();
    }

    @Override
    public Trainer update(Trainer trainer) {
        Optional<Trainer> optional = trainerRepository.findById(trainer.getTrainerId());
        if (optional.isEmpty()) {
            try {
                optional.orElseThrow(() -> new ResourceNotFoundException("No trainer was found to update for the given ID:" + trainer.getTrainerId()));
            } catch (ResourceNotFoundException e) {
                log.error(e.getMessage());
                throw e;
            }
        }
        trainerRepository.save(trainer);
        log.info("Updated successfully");
        return trainer;
    }

    @Override
    public Trainer validateLogin(TrainerLoginBean trainerLoginBean) {
        Trainer trainer = trainerRepository.findByName(trainerLoginBean.getName());
        log.info("Trainer found: {}", trainer);

        if (trainer != null) {
            Trainer registrationBean = new Trainer();

            if (trainer.getPassword().equals(trainerLoginBean.getPassword())) {
                log.info("Login successful");
                return trainer;
            } else {
                try {
                    throw new PasswordMismatchException("Incorrect password");
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
                return trainer;
            }
        } else {
            try {
                throw new UserNameNotFoundException("Incorrect EmailId");
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        return trainer;
    }

    @Override
    public Trainer findByTrainerCode(String trainerCode) {
        Optional<Trainer> optional = trainerRepository.findByTrainerCode(trainerCode);
        if (optional.isPresent()) {
            Trainer trainer = optional.get();
            log.info("Trainer found by code: {}", trainer);
            return trainer;
        } else {
            log.info("Trainer not found by code: {}", trainerCode);
            return null;
        }
    }

    @Override
    public Trainer getByTrainerCode(String trainerCode) {
        Optional<Trainer> optional = trainerRepository.findByTrainerCode(trainerCode);
        if (optional.isEmpty()) {
            try {
                optional.orElseThrow(() -> new ResourceNotFoundException("No trainer was found to fetch for the given ID:" + trainerCode));
            } catch (ResourceNotFoundException e) {
                log.error(e.getMessage());
                throw e;
            }
        }
        log.info("Fetched successfully");
        return optional.get();
    }
}
