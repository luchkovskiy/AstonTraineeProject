package com.luchkovskiy.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Employee {

    private Long id;

    private String name;

    private Integer age;

    private Timestamp created;

    private Timestamp changed;

}
