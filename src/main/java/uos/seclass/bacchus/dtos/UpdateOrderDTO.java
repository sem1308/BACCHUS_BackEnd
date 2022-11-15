package uos.seclass.bacchus.dtos;

import lombok.*;

import java.util.Date;

@AllArgsConstructor()
@NoArgsConstructor()
@Setter
@Getter
@Builder
public class UpdateOrderDTO {
    private String state;

    private Date deliveredTime;
}
