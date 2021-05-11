package ru.itis.api.dtos.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangeTaskStatusDTO {

    private String taskId;
    private boolean done;

}
