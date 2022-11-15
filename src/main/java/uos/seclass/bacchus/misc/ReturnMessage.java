package uos.seclass.bacchus.misc;

import lombok.*;

@AllArgsConstructor()
@NoArgsConstructor()
@Setter
@Getter
@Builder
public class ReturnMessage<T> {
    private StatusEnum status;

    private String message;

    private T data;
}
