package uos.seclass.bacchus.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uos.seclass.bacchus.domains.Account;
import uos.seclass.bacchus.domains.Customer;
import uos.seclass.bacchus.domains.Style;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>{
    public Optional<Account> findByCardNum(String cardNum);
    public Optional<Account> findByCardNumAndOwnerName(String cardNum, String ownerName);
}
