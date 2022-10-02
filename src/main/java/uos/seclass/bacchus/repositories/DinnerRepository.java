package uos.seclass.bacchus.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uos.seclass.bacchus.domains.Dinner;

import java.util.List;

@Repository
public interface DinnerRepository extends JpaRepository<Dinner, Integer>{
}
