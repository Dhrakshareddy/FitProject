package com.management.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.management.entity.Equipment;
import com.management.exception.ResourceNotFoundException;
import com.management.repository.EquipmentRepository;
import com.management.service.EquipmentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EquipmentServiceImpl implements EquipmentService {

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Override
    public void save(Equipment equipment) {
        equipmentRepository.save(equipment);
        log.info("Saved successfully");
    }

    @Override
    public Equipment update(Equipment equipment) {
        log.info("Updating equipment: {}", equipment);
        Optional<Equipment> optional = equipmentRepository.findById(equipment.getEquipmentId());
        if (optional.isEmpty()) {
            try {
                optional.orElseThrow(() -> new ResourceNotFoundException("No equipment was found to update for the given ID: " + equipment.getEquipmentId()));
            } catch (ResourceNotFoundException e) {
                log.error(e.getMessage());
                throw e;
            }
        }
        equipmentRepository.save(equipment);
        log.info("Updated successfully");
        return equipment;
    }

    @Override
    public Equipment getById(Long id) {
        Optional<Equipment> optional = equipmentRepository.findById(id);
        if (optional.isPresent()) {
            log.info("Fetched equipment successfully");
            return optional.get();
        } else {
            try {
                optional.orElseThrow(() -> new ResourceNotFoundException("No equipment was found to fetch for the given ID: " + id));
            } catch (ResourceNotFoundException e) {
                log.error(e.getMessage());
                throw e;
            }
        }
        return null;
    }

    @Override
    public List<Equipment> getAll() {
        log.info("Fetching all equipment");
        return equipmentRepository.findAll();
    }

    @Override
    public Equipment delete(Long id) {
        Optional<Equipment> optional = equipmentRepository.findById(id);
        if (optional.isPresent()) {
            equipmentRepository.deleteById(id);
            log.info("Deleted equipment successfully");
            return optional.get();
        } else {
            try {
                optional.orElseThrow(() -> new ResourceNotFoundException("No equipment was found to delete for the given ID: " + id));
            } catch (ResourceNotFoundException e) {
                log.error(e.getMessage());
                throw e;
            }
        }
        return null;
    }
}
