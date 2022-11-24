package uos.seclass.bacchus.domains;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Employees")
@AllArgsConstructor()
@NoArgsConstructor()
@Setter
@Getter
@Builder
public class Employee{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="employee_num")
    private int employeeNum;

    @Column(nullable = false, unique = true, length = 16)
    private String id;

    @Column(nullable = false, unique = false, length = 128)
    private String pw;

    @Column(nullable = false, length = 16)
    private String name;

    @Column(nullable = false, length = 16)
    private String occupation;

    @Column(name="employeed_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date employeedAt;

    @Column(name="is_approved", nullable = false)
    private boolean isApproved;
}

