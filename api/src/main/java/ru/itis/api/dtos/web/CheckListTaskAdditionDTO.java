package ru.itis.api.dtos.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CheckListTaskAdditionDTO {

    private String title;
    private String checkListId;

}
