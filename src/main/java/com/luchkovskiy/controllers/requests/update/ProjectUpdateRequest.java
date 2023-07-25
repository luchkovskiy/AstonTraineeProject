package com.luchkovskiy.controllers.requests.update;

import com.luchkovskiy.controllers.requests.create.ProjectCreateRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProjectUpdateRequest extends ProjectCreateRequest {

    private Long id;

}
