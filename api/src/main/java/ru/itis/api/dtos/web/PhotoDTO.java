package ru.itis.api.dtos.web;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PhotoDTO {

    private long id;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private CardDTO card;

    private String title;
    private Date loadedAt;
    private String content;

}
