package uos.seclass.bacchus.domains;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Customers")
@AllArgsConstructor()
@NoArgsConstructor()
@Setter
@Getter
@Builder
public class Customer{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="customer_num")
    private int customerNum;

    /* Foreign Key */
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_num", nullable = false, insertable = false, updatable = false)
    private List<Order> orders;
    /* */

    @Column(nullable = false, unique = true, length = 16)
    private String id;

    @Column(nullable = false, unique = false, length = 128)
    private String pw;

    @Column(nullable = false, unique = false, length = 16)
    private String name;

    @Column(nullable = false, length = 100)
    private String address;

    @Column(name = "card_num", nullable = false, length = 16)
    private String cardNum;

    @Column(name="created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
}

