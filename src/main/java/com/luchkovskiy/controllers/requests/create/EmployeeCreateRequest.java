package com.luchkovskiy.controllers.requests.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmployeeCreateRequest {

    private String name;

    private Integer age;

}
