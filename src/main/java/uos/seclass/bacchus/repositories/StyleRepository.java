package uos.seclass.bacchus.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uos.seclass.bacchus.domains.Order;
import uos.seclass.bacchus.domains.Style;

import java.util.List;

@Repository
public interface StyleRepository extends JpaRepository<Style, Integer>{
    public Style findByStyleCode(String styleCode);
}
