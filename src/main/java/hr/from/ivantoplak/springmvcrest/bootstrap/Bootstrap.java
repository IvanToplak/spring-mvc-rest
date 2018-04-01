package hr.from.ivantoplak.springmvcrest.bootstrap;

import hr.from.ivantoplak.springmvcrest.domain.Category;
import hr.from.ivantoplak.springmvcrest.domain.Customer;
import hr.from.ivantoplak.springmvcrest.domain.Vendor;
import hr.from.ivantoplak.springmvcrest.repositories.CategoryRepository;
import hr.from.ivantoplak.springmvcrest.repositories.CustomerRepository;
import hr.from.ivantoplak.springmvcrest.repositories.VendorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Bootstrap implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final CustomerRepository customerRepository;
    private final VendorRepository vendorRepository;

    public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) {

        loadCustomers();
        loadCategories();
        loadVendors();
    }

    private void loadCategories() {
        Category fruits = new Category();
        fruits.setName("Fruits");

        Category dried = new Category();
        dried.setName("Dried");

        Category fresh = new Category();
        fresh.setName("Fresh");

        Category exotic = new Category();
        exotic.setName("Exotic");

        Category nuts = new Category();
        nuts.setName("Nuts");

        categoryRepository.save(fruits);
        categoryRepository.save(dried);
        categoryRepository.save(fresh);
        categoryRepository.save(exotic);
        categoryRepository.save(nuts);

        log.info("Categories Loaded = " + categoryRepository.count());
    }

    private void loadCustomers() {

        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setFirstName("Michele");
        customer1.setLastName("Weston");

        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setFirstName("Sam");
        customer2.setLastName("Axe");

        customerRepository.save(customer1);
        customerRepository.save(customer2);

        log.info("Customers Loaded = " + customerRepository.count());
    }

    private void loadVendors() {

        Vendor vendor1 = new Vendor();
        vendor1.setName("John");

        Vendor vendor2 = new Vendor();
        vendor2.setName("Jim");

        vendorRepository.save(vendor1);
        vendorRepository.save(vendor2);

        log.info("Vendors Loaded = " + vendorRepository.count());
    }
}
