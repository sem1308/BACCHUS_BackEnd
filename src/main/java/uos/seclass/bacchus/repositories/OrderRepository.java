package uos.seclass.bacchus.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uos.seclass.bacchus.domains.Order;
import uos.seclass.bacchus.domains.Member;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>{
    public List<Order> findByMember(Member member);
}
