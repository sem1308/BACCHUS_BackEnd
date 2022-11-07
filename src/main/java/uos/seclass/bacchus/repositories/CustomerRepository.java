package uos.seclass.bacchus.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uos.seclass.bacchus.domains.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer>{
    public Optional<Customer> findById(String id);
}
