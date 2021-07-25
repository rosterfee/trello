package ru.itis.api.dtos.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CommentAdditionReturnDTO {

    private Long id;
    private long date;

}
