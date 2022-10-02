package uos.seclass.bacchus.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uos.seclass.bacchus.domains.Dinner;
import uos.seclass.bacchus.domains.Food;

@Repository
public interface FoodRepository extends JpaRepository<Food, Integer>{
}
