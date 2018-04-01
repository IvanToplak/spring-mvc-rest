package hr.from.ivantoplak.springmvcrest.controllers.v1;

import hr.from.ivantoplak.springmvcrest.api.v1.model.CustomerDTO;
import hr.from.ivantoplak.springmvcrest.controllers.RestResponseEntityExceptionHandler;
import hr.from.ivantoplak.springmvcrest.services.CustomerService;
import hr.from.ivantoplak.springmvcrest.services.ResourceNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static hr.from.ivantoplak.springmvcrest.api.v1.mapper.AbstractRestControllerTest.asJsonString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CustomerControllerTest {

    private static final String FIRSTNAME_1 = "John";
    private static final String FIRSTNAME_2 = "Jimmy";
    private static final String LASTNAME_1 = "Doe";
    private static final String LASTNAME_2 = "Jones";

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    private MockMvc mockMvc;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(customerController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }

    @Test
    public void getListOfCustomers() throws Exception {

        //given
        CustomerDTO customerDTO1 = getCustomerDTO1();

        CustomerDTO customerDTO2 = getCustomerDTO2();

        List<CustomerDTO> customers = Arrays.asList(customerDTO1, customerDTO2);

        when(customerService.getAllCustomers()).thenReturn(customers);

        //when
        mockMvc.perform(get(CustomerController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) //then
                .andExpect(jsonPath("$.customers", hasSize(2)));

    }

    @Test
    public void getCustomerById() throws Exception {

        //given
        CustomerDTO customerDTO1 = getCustomerDTO1();

        when(customerService.getCustomerById(anyLong())).thenReturn(customerDTO1);

        //when
        mockMvc.perform(get(CustomerController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) //then
                .andExpect(jsonPath("$.firstname", equalTo(FIRSTNAME_1)))
                .andExpect(jsonPath("$.lastname", equalTo(LASTNAME_1)));
    }

    @Test
    public void createNewCustomer() throws Exception {

        //given
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstName(FIRSTNAME_1);
        customer.setLastName(LASTNAME_1);

        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setFirstName(customer.getFirstName());
        returnDTO.setLastName(customer.getLastName());
        returnDTO.setCustomerUrl(CustomerController.BASE_URL + "/1");

        when(customerService.createNewCustomer(customer)).thenReturn(returnDTO);

        //when
        mockMvc.perform(post(CustomerController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customer)))
                .andExpect(status().isCreated()) //then
                .andExpect(jsonPath("$.firstname", equalTo(FIRSTNAME_1)))
                .andExpect(jsonPath("$.lastname", equalTo(LASTNAME_1)))
                .andExpect(jsonPath("$.customer_url", equalTo(CustomerController.BASE_URL + "/1")));
    }

    @Test
    public void updateCustomer() throws Exception {


        //given
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstName(FIRSTNAME_1);
        customer.setLastName(LASTNAME_1);

        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setFirstName(customer.getFirstName());
        returnDTO.setLastName(customer.getLastName());
        returnDTO.setCustomerUrl(CustomerController.BASE_URL + "/1");

        when(customerService.saveCustomerByDTO(anyLong(), any(CustomerDTO.class))).thenReturn(returnDTO);

        //when
        mockMvc.perform(put(CustomerController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customer)))
                .andExpect(status().isOk()) //then
                .andExpect(jsonPath("$.firstname", equalTo(FIRSTNAME_1)))
                .andExpect(jsonPath("$.lastname", equalTo(LASTNAME_1)))
                .andExpect(jsonPath("$.customer_url", equalTo(CustomerController.BASE_URL + "/1")));
    }

    @Test
    public void patchCustomer() throws Exception {

        //given
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstName(FIRSTNAME_1);
        customer.setLastName(LASTNAME_1);

        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setFirstName(customer.getFirstName());
        returnDTO.setLastName(customer.getLastName());
        returnDTO.setCustomerUrl(CustomerController.BASE_URL + "/1");

        when(customerService.patchCustomer(anyLong(), any(CustomerDTO.class))).thenReturn(returnDTO);

        //when
        mockMvc.perform(patch(CustomerController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customer)))
                .andExpect(status().isOk()) //then
                .andExpect(jsonPath("$.firstname", equalTo(FIRSTNAME_1)))
                .andExpect(jsonPath("$.lastname", equalTo(LASTNAME_1)))
                .andExpect(jsonPath("$.customer_url", equalTo(CustomerController.BASE_URL + "/1")));
    }

    @Test
    public void deleteCustomer() throws Exception {

        mockMvc.perform(delete(CustomerController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(customerService).deleteCustomerById(anyLong());
    }

    @Test
    public void getCustomerByIdNotFound() throws Exception {

        when(customerService.getCustomerById(anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(CustomerController.BASE_URL + "/222")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    private CustomerDTO getCustomerDTO1() {
        CustomerDTO customerDTO1 = new CustomerDTO();
        customerDTO1.setFirstName(FIRSTNAME_1);
        customerDTO1.setLastName(LASTNAME_1);
        customerDTO1.setCustomerUrl(CustomerController.BASE_URL + "/1");
        return customerDTO1;
    }

    private CustomerDTO getCustomerDTO2() {
        CustomerDTO customerDTO2 = new CustomerDTO();
        customerDTO2.setFirstName(FIRSTNAME_2);
        customerDTO2.setLastName(LASTNAME_2);
        customerDTO2.setCustomerUrl(CustomerController.BASE_URL + "/2");
        return customerDTO2;
    }
}