package hr.from.ivantoplak.springmvcrest.api.v1.mapper;

import hr.from.ivantoplak.springmvcrest.api.v1.model.CustomerDTO;
import hr.from.ivantoplak.springmvcrest.domain.Customer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerMapperTest {

    private static final String JOHN = "John";
    private static final String DOE = "Doe";

    private CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    @Test
    public void customerToCustomerDto() {

        //given
        Customer customer = new Customer();
        customer.setFirstName(JOHN);
        customer.setLastName(DOE);

        //when
        CustomerDTO customerDTO = customerMapper.customerToCustomerDto(customer);

        //then
        assertEquals(JOHN, customerDTO.getFirstName());
        assertEquals(DOE, customerDTO.getLastName());
    }

    @Test
    public void customerDtoToCustomer() {

        //given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName(JOHN);
        customerDTO.setLastName(DOE);

        //when
        Customer customer = customerMapper.customerDtoToCustomer(customerDTO);

        //then
        assertEquals(JOHN, customer.getFirstName());
        assertEquals(DOE, customer.getLastName());
    }
}