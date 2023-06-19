package com.example.Challenger2.entities.DTOs.revenueDTOs;

import com.example.Challenger2.entities.Revenue;
import jakarta.persistence.Column;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class RevenueDTO {

    @NotBlank(message = "Description is mandatory")
    private String description;

    @NotNull(message = "Price is mandatory")
    @DecimalMin(value = "0.01", message = "Price must be greater than or equals to 0.01")
    private BigDecimal price;

    @Column(name = "date_time")
    @NotNull(message = "Date is mandatory")
    @PastOrPresent(message = "Date must be in the past or present")
    private LocalDate date;

    public RevenueDTO(Revenue revenue) {
        this.description = revenue.getDescription();
        this.price = revenue.getPrice();
        this.date = revenue.getDate();
    }

}
