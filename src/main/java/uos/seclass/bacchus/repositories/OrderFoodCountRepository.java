package uos.seclass.bacchus.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uos.seclass.bacchus.domains.OrderFoodCount;

@Repository
public interface OrderFoodCountRepository extends JpaRepository<OrderFoodCount, Integer>{
}
