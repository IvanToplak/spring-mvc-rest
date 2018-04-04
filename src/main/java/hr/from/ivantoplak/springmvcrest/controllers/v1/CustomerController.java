package hr.from.ivantoplak.springmvcrest.controllers.v1;

import hr.from.ivantoplak.springmvcrest.api.v1.model.CustomerDTO;
import hr.from.ivantoplak.springmvcrest.api.v1.model.CustomerListDTO;
import hr.from.ivantoplak.springmvcrest.services.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Api(description = "Customer API")
@RestController
@RequestMapping(CustomerController.BASE_URL)
public class CustomerController {

    public static final String BASE_URL = "/api/v1/customers";
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @ApiOperation(value = "Get a list of customers")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CustomerListDTO getListOfCustomers() {

        return new CustomerListDTO(customerService.getAllCustomers());
    }

    @ApiOperation(value = "Get customer by ID")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO getCustomerById(@PathVariable Long id) {

        return customerService.getCustomerById(id);
    }

    @ApiOperation(value = "Create a new customer")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTO createNewCustomer(@RequestBody CustomerDTO customerDTO) {

        return customerService.createNewCustomer(customerDTO);
    }

    @ApiOperation(value = "Update an existing customer")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {

        return customerService.saveCustomerByDTO(id, customerDTO);
    }

    @ApiOperation(value = "Update a customer property")
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO patchCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {

        return customerService.patchCustomer(id, customerDTO);
    }

    @ApiOperation(value = "Delete a customer")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCustomer(@PathVariable Long id) {

        customerService.deleteCustomerById(id);
    }
}
