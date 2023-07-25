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
public class Project {

    private Long id;

    private String name;

    private Timestamp startTime;

    private Timestamp created;

    private Timestamp changed;
}
