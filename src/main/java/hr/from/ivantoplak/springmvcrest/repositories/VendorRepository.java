package hr.from.ivantoplak.springmvcrest.repositories;

import hr.from.ivantoplak.springmvcrest.domain.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<Vendor, Long> {
}
