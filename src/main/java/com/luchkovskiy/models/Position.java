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
public class Position {

    private Long id;

    private String name;

    private Employee employee;

    private Timestamp created;

    private Timestamp changed;

}
