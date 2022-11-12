package uos.seclass.bacchus.dtos;

import lombok.*;
import java.util.Date;
import java.util.Set;

@AllArgsConstructor()
@NoArgsConstructor()
@Setter
@Getter
@Builder
public class InsertOrderDTO {
    private int customerNum;

    private Set<Integer> dinnerNum;

    private String styleCode;

    private int totalPrice;

    private Date wantedDeliveredTime;

    private String address;

    private String cardNum;
}

