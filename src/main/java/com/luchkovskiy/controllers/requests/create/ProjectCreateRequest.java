package com.luchkovskiy.controllers.requests.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProjectCreateRequest {

    private String name;

    private Timestamp startTime;

}
