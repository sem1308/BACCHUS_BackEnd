package uos.seclass.bacchus.dtos;

import lombok.*;

import java.util.Date;

@AllArgsConstructor()
@NoArgsConstructor()
@Setter
@Getter
@Builder
public class InsertOrderDTO {
    private int customerNum;

    private int totalPrice;

    private Date wantedDeliveredTime;

    private String address;

    private String cardNum;
}

