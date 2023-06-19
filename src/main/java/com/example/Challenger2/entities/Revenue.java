package com.example.Challenger2.entities;

import com.example.Challenger2.entities.DTOs.revenueDTOs.RevenueDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "tb_revenues")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Revenue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String description;
    private BigDecimal price;
    @Column(name = "date_time")
    private LocalDate date;

    public Revenue(RevenueDTO revenueDTO) {
        description = revenueDTO.getDescription();
        price = revenueDTO.getPrice();
        date = revenueDTO.getDate();
    }
}


