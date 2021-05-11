package ru.itis.api.dtos.web;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CheckListDTO {

    private long id;
    private String title;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private CardDTO card;

    private List<CheckListTaskDTO> checkListTasks;

}
