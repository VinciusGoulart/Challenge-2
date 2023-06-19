package com.example.Challenger2.services;

import com.example.Challenger2.entities.DTOs.revenueDTOs.RevenueDTO;
import com.example.Challenger2.entities.Revenue;
import com.example.Challenger2.repositories.RevenueRepository;
import com.example.Challenger2.services.exceptions.BadRequestException;
import com.example.Challenger2.services.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RevenueService {

    @Autowired
    private RevenueRepository revenueRepository;

    public Revenue save(RevenueDTO revenue) {
        if (checkDescription(revenue)) {
            throw new BadRequestException("This revenue already exists");
        }
        return revenueRepository.save(new Revenue(revenue));
    }

    public List<RevenueDTO> findAll() {
        return revenueRepository.findAll().stream().map(RevenueDTO::new).toList();
    }

    public Revenue findById(Long id) {
        return revenueRepository.findById(id).orElseThrow(() -> new NotFoundException("ID Not Found"));
    }

    public Revenue update(Long id, RevenueDTO revenueDTO) {
        Revenue revenue = findById(id);

        updateData(revenueDTO, revenue);

        return revenueRepository.save(revenue);
    }

    public void delete(Long id) {
        Revenue revenue = findById(id);

        revenueRepository.delete(revenue);
    }

    public List<RevenueDTO> findByDescription(String description) {
        List<RevenueDTO> list = revenueRepository.findByDescriptionIgnoreCase(
                description).stream().map(RevenueDTO::new).toList();

        if (list.isEmpty()) {
            throw new NotFoundException("There is no revenue with this description");
        }

        return list;
    }

    public List<RevenueDTO> findByYearAndMonth(Integer year, Integer month) {
        List<RevenueDTO> list = revenueRepository.findByYearAndMonth(year, month).stream().map(RevenueDTO::new).toList();

        if (list.isEmpty()) {
            throw new NotFoundException("There is no revenue on this date");
        }

        return list;
    }

    private Boolean checkDescription(RevenueDTO revenueDTO) {
        Integer monthValue = revenueDTO.getDate().getMonthValue();
        Integer yearValue = revenueDTO.getDate().getYear();
        List<Revenue> list = revenueRepository.findByDescriptionAndDateMonthAndDateYearAllIgnoreCase(revenueDTO.getDescription(), monthValue, yearValue);
        return !list.isEmpty();
    }

    private void updateData(RevenueDTO revenueDTO, Revenue revenue) {
        revenue.setDescription(revenueDTO.getDescription());
        revenue.setPrice(revenueDTO.getPrice());
        revenue.setDate(revenueDTO.getDate());
    }
}
