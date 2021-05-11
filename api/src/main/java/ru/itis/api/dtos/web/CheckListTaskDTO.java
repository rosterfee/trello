package ru.itis.api.dtos.web;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CheckListTaskDTO {

    private long id;
    private String title;
    private boolean done;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private CheckListDTO checkList;

}
