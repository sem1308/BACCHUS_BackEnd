package uos.seclass.bacchus.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor()
@NoArgsConstructor()
@Setter
@Getter
@Builder
public class UpdateMemberDTO {
    private String id;

    private String pw;

    private String address;

    private String cardNum;
}
