package com.luchkovskiy.controllers.requests.update;

import com.luchkovskiy.controllers.requests.create.PositionCreateRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PositionUpdateRequest extends PositionCreateRequest {

    private Long id;

}
