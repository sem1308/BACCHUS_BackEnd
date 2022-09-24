package uos.seclass.bacchus.dtos;

import lombok.*;

@AllArgsConstructor()
@NoArgsConstructor()
@Setter
@Getter
@Builder
public class UpdateEmployeeDTO {
    private String id;

    private String pw;
}
