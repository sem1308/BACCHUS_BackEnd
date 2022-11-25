package uos.seclass.bacchus.dtos;

import lombok.*;
import java.util.Date;
import java.util.List;

@AllArgsConstructor()
@NoArgsConstructor()
@Setter
@Getter
@Builder
public class PrintCustomerDTO {
    private int customerNum;

    private List<PrintOrderDTO> orders;

    private String id;

    private String pw;

    private String name;

    private String address;

    private String cardNum;

    private Date createdAt;
}

