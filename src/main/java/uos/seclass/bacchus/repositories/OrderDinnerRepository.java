package uos.seclass.bacchus.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uos.seclass.bacchus.domains.OrderDinner;

@Repository
public interface OrderDinnerRepository extends JpaRepository<OrderDinner, Integer>{
}
