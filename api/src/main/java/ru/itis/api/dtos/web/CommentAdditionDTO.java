package ru.itis.api.dtos.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentAdditionDTO {

    private String text;
    private String authorId;
    //private String authorVkId;
    private String cardId;

}
