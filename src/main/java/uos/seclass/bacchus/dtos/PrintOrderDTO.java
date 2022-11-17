package uos.seclass.bacchus.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import uos.seclass.bacchus.domains.Customer;
import uos.seclass.bacchus.domains.Dinner;
import uos.seclass.bacchus.domains.FoodOrderCount;
import uos.seclass.bacchus.domains.Style;

import javax.persistence.*;
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

    private Set<Dinner> dinners;

    private Set<FoodOrderCount> foodCounts;

    private Style style;

    private String state;

    private Date orderTime;

    private Date deliveredTime;

    private Date wantedDeliveredTime;

    private int totalPrice;

    private String address;
}

