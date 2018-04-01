package hr.from.ivantoplak.springmvcrest.services;

import hr.from.ivantoplak.springmvcrest.api.v1.mapper.CustomerMapper;
import hr.from.ivantoplak.springmvcrest.api.v1.model.CustomerDTO;
import hr.from.ivantoplak.springmvcrest.controllers.v1.CustomerController;
import hr.from.ivantoplak.springmvcrest.domain.Customer;
import hr.from.ivantoplak.springmvcrest.repositories.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class CustomerServiceImplTest {

    private static final Long ID = 1L;
    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "Doe";
    private static final int SIZE = 3;

    @Mock
    private CustomerRepository customerRepository;

    private CustomerService customerService;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);
        customerService = new CustomerServiceImpl(CustomerMapper.INSTANCE, customerRepository);
    }

    @Test
    public void getAllCustomers() {

        //given
        List<Customer> customers = Arrays.asList(new Customer(), new Customer(), new Customer());

        when(customerRepository.findAll()).thenReturn(customers);

        //when
        List<CustomerDTO> customerDTOS = customerService.getAllCustomers();

        //then
        assertEquals(SIZE, customerDTOS.size());
    }

    @Test
    public void getCustomerById() {

        //given
        Customer customer = getCustomer();

        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));

        //when
        CustomerDTO customerDTO = customerService.getCustomerById(ID);

        //then
        assertEquals(FIRST_NAME, customerDTO.getFirstName());
        assertEquals(LAST_NAME, customerDTO.getLastName());
    }

    @Test
    public void createNewCustomer() {

        //given
        CustomerDTO customerDTO = getCustomerDTO();

        Customer savedCustomer = getCustomer();

        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        //when
        CustomerDTO savedDto = customerService.createNewCustomer(customerDTO);

        //then
        assertEquals(customerDTO.getFirstName(), savedDto.getFirstName());
        assertEquals(customerDTO.getLastName(), savedDto.getLastName());
        assertEquals(CustomerController.BASE_URL + "/1", savedDto.getCustomerUrl());
    }

    @Test
    public void saveCustomerByDTO() {

        //given
        CustomerDTO customerDTO = getCustomerDTO();

        Customer savedCustomer = getCustomer();

        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        //when
        CustomerDTO savedDto = customerService.saveCustomerByDTO(ID, customerDTO);

        //then
        assertEquals(customerDTO.getFirstName(), savedDto.getFirstName());
        assertEquals(customerDTO.getLastName(), savedDto.getLastName());
        assertEquals(CustomerController.BASE_URL + "/1", savedDto.getCustomerUrl());
    }

    @Test
    public void deleteCustomerById() {

        //when
        customerService.deleteCustomerById(ID);

        //then
        verify(customerRepository, times(1)).findById(anyLong());
    }

    private Customer getCustomer() {
        Customer customer = new Customer();
        customer.setId(ID);
        customer.setFirstName(FIRST_NAME);
        customer.setLastName(LAST_NAME);
        return customer;
    }

    private CustomerDTO getCustomerDTO() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName(FIRST_NAME);
        customerDTO.setLastName(LAST_NAME);
        return customerDTO;
    }
}