package uos.seclass.bacchus.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uos.seclass.bacchus.domains.FoodDinnerCount;
import uos.seclass.bacchus.domains.FoodOrderCount;

@Repository
public interface FoodDinnerCountRepository extends JpaRepository<FoodDinnerCount, Integer>{
}
