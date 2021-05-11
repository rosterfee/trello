package ru.itis.api.dtos.web;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BoardDTO {

    private long id;
    private String name;

    private UserDTO creator;
    private List<ColumnDTO> columns;
    private List<UserDTO> participants;

}
