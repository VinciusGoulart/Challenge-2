package com.example.Challenger2.controllers;

import com.example.Challenger2.entities.DTOs.revenueDTOs.RevenueDTO;
import com.example.Challenger2.entities.Revenue;
import com.example.Challenger2.services.RevenueService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/revenues")
public class RevenueController {

    @Autowired
    private RevenueService service;

    @PostMapping
    public ResponseEntity<RevenueDTO> save(@RequestBody @Valid RevenueDTO revenueDTO, UriComponentsBuilder builder) {
        Revenue insert = service.save(revenueDTO);
        URI uri = builder.path("/{id}").buildAndExpand(insert.getId()).toUri();
        return ResponseEntity.created(uri).body(new RevenueDTO(insert));
    }

    @GetMapping
    public ResponseEntity<List<RevenueDTO>> findAll(@RequestParam(value = "search", required = false) String description) {
        List<RevenueDTO> list;

        if (description != null) {
            list = service.findByDescription(description);
            return ResponseEntity.ok().body(list);
        }

        list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<RevenueDTO> findById(@PathVariable Long id) {
        RevenueDTO revenueDTO = new RevenueDTO(service.findById(id));
        return ResponseEntity.ok(revenueDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<RevenueDTO> update(@PathVariable Long id, @RequestBody @Valid RevenueDTO revenueDTO) {
        revenueDTO = new RevenueDTO(service.update(id, revenueDTO));
        return ResponseEntity.ok(revenueDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{year}/{month}")
    public ResponseEntity<List<RevenueDTO>> findByYearAndMonth(@PathVariable(value = "year") Integer year,
                                                               @PathVariable(value = "month") Integer month) {
        List<RevenueDTO> list = service.findByYearAndMonth(year, month);

        return ResponseEntity.ok().body(list);
    }

}
