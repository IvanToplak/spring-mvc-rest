package hr.from.ivantoplak.springmvcrest.repositories;

import hr.from.ivantoplak.springmvcrest.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
