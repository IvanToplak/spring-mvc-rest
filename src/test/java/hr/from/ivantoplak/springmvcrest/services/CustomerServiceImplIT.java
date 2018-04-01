package hr.from.ivantoplak.springmvcrest.services;

import hr.from.ivantoplak.springmvcrest.api.v1.mapper.CustomerMapper;
import hr.from.ivantoplak.springmvcrest.api.v1.model.CustomerDTO;
import hr.from.ivantoplak.springmvcrest.bootstrap.Bootstrap;
import hr.from.ivantoplak.springmvcrest.domain.Customer;
import hr.from.ivantoplak.springmvcrest.repositories.CategoryRepository;
import hr.from.ivantoplak.springmvcrest.repositories.CustomerRepository;
import hr.from.ivantoplak.springmvcrest.repositories.VendorRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@Slf4j
public class CustomerServiceImplIT {

    private static final String UPDATED_NAME = "UpdatedName";

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    VendorRepository vendorRepository;

    private CustomerService customerService;

    @Before
    public void setUp() {

        log.info("Loading Customer Data");
        log.info(String.valueOf(customerRepository.findAll().size()));

        //setup data for testing
        Bootstrap bootstrap = new Bootstrap(categoryRepository, customerRepository, vendorRepository);
        bootstrap.run();

        customerService = new CustomerServiceImpl(CustomerMapper.INSTANCE, customerRepository);
    }

    @Test
    public void patchCustomerUpdateFirstName() {

        long id = getCustomerIdValue();

        Customer originalCustomer = customerRepository.getOne(id);
        assertNotNull(originalCustomer);

        //save original first name and last name
        String originalFirstName = originalCustomer.getFirstName();
        String originalLastName = originalCustomer.getLastName();

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName(UPDATED_NAME);

        customerService.patchCustomer(id, customerDTO);

        Customer updatedCustomer = customerRepository.findById(id).get();

        assertNotNull(updatedCustomer);
        assertEquals(UPDATED_NAME, updatedCustomer.getFirstName());
        assertThat(originalFirstName, not(equalTo(updatedCustomer.getFirstName())));
        assertThat(originalLastName, equalTo(updatedCustomer.getLastName()));
    }

    @Test
    public void patchCustomerUpdateLastName() {

        long id = getCustomerIdValue();

        Customer originalCustomer = customerRepository.getOne(id);
        assertNotNull(originalCustomer);

        //save original first/last name
        String originalFirstName = originalCustomer.getFirstName();
        String originalLastName = originalCustomer.getLastName();

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setLastName(UPDATED_NAME);

        customerService.patchCustomer(id, customerDTO);

        Customer updatedCustomer = customerRepository.findById(id).get();

        assertNotNull(updatedCustomer);
        assertEquals(UPDATED_NAME, updatedCustomer.getLastName());
        assertThat(originalFirstName, equalTo(updatedCustomer.getFirstName()));
        assertThat(originalLastName, not(equalTo(updatedCustomer.getLastName())));
    }

    /**
     * Returns ID of the first customer retrieved from DB.
     *
     * @return Customer ID
     */
    private Long getCustomerIdValue() {

        List<Customer> customers = customerRepository.findAll();

        log.info("Customers Found:" + customers.size());

        return customers.get(0).getId();
    }
}
