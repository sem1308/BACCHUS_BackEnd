package uos.seclass.bacchus.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uos.seclass.bacchus.domains.DinnerFoodCount;

@Repository
public interface DinnerFoodCountRepository extends JpaRepository<DinnerFoodCount, Integer>{
}
