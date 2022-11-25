package uos.seclass.bacchus.dtos;

import lombok.*;

import java.util.Date;
import java.util.Set;

@AllArgsConstructor()
@NoArgsConstructor()
@Setter
@Getter
@Builder
public class PrintOrderDTO {
    private int orderNum;

    private String customerName;

    private int customerNum;

    private Set<PrintOrderDinnerDTO> orderDinners;

    private String state;

    private Date orderTime;

    private Date deliveredTime;

    private Date wantedDeliveredTime;

    private int totalPrice;

    private String address;
}

