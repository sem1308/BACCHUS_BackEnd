package uos.seclass.bacchus.dtos;

import lombok.*;

@AllArgsConstructor()
@NoArgsConstructor()
@Setter
@Getter
@Builder
public class InsertEmployeeDTO {
    private String id;

    private String pw;

    private String name;

    private String occupation;
}

