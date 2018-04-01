package hr.from.ivantoplak.springmvcrest.repositories;

import hr.from.ivantoplak.springmvcrest.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByName(String name);
}
