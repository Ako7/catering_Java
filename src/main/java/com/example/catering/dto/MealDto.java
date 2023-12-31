package com.example.catering.dto;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MealDto {
    private Long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String picture;
    @NotEmpty
    private String description;
    @NotEmpty
    private String category;
    private double price;
    private Long calories;
}
