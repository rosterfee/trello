package ru.itis.api.dtos.web;

import lombok.*;
import ru.itis.api.enums.BoardType;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BoardDTO {

    private long id;
    private String name;

    private UserDTO creator;
    private List<ColumnDTO> columns;
    private Set<UserDTO> participants;

    private BoardType boardType;

}
