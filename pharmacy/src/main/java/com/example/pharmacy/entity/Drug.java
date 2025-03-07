package com.example.pharmacy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Timestamp;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Drug {
    private Integer id;
    private String name;
    private String category;
    private String manufacturer;
    private String efficacy;
    private Double price;
    private Integer stock;
    private Boolean insuranceCovered;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
