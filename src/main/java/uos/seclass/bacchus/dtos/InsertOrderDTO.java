package uos.seclass.bacchus.dtos;

import lombok.*;
import uos.seclass.bacchus.domains.Dinner;
import uos.seclass.bacchus.domains.Employee;
import uos.seclass.bacchus.domains.Member;
import uos.seclass.bacchus.domains.Style;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@AllArgsConstructor()
@NoArgsConstructor()
@Setter
@Getter
@Builder
public class InsertOrderDTO {
    private int memberNum;

    private int employeeNum;

    private Set<Integer> dinnerNum;

    private String style_code;

    private int totalPrice;

    private Date orderTime;
}

