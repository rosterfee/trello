package ru.itis.api.dtos.web;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CommentDTO {

    private long id;
    private String text;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private UserDTO author;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private CardDTO card;

    private Date createdAt;

}
