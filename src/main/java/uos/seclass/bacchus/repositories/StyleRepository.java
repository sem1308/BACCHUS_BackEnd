package uos.seclass.bacchus.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uos.seclass.bacchus.domains.Order;
import uos.seclass.bacchus.domains.Style;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface StyleRepository extends JpaRepository<Style, Integer>{
    public Optional<Style> findByStyleCode(String styleCode);
}
