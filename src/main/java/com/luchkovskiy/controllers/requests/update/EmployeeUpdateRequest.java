package com.luchkovskiy.controllers.requests.update;

import com.luchkovskiy.controllers.requests.create.EmployeeCreateRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmployeeUpdateRequest extends EmployeeCreateRequest {

    private Long id;

}
