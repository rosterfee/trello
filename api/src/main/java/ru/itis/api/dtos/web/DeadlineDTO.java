package ru.itis.api.dtos.web;


import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DeadlineDTO {

    private long id;
    private Date date;
    private boolean done;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private CardDTO card;

}
