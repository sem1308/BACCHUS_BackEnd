package uos.seclass.bacchus.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uos.seclass.bacchus.domains.FoodOrderCount;

@Repository
public interface FoodOrderCountRepository extends JpaRepository<FoodOrderCount, Integer>{
}
