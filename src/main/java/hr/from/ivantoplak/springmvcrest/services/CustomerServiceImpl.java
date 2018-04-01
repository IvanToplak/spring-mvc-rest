package hr.from.ivantoplak.springmvcrest.services;

import hr.from.ivantoplak.springmvcrest.api.v1.mapper.CustomerMapper;
import hr.from.ivantoplak.springmvcrest.api.v1.model.CustomerDTO;
import hr.from.ivantoplak.springmvcrest.controllers.v1.CustomerController;
import hr.from.ivantoplak.springmvcrest.domain.Customer;
import hr.from.ivantoplak.springmvcrest.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerMapper customerMapper, CustomerRepository customerRepository) {
        this.customerMapper = customerMapper;
        this.customerRepository = customerRepository;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {

        return customerRepository
                .findAll()
                .stream()
                .map(customer -> {
                    CustomerDTO customerDTO = customerMapper.customerToCustomerDto(customer);
                    customerDTO.setCustomerUrl(getCustomerUrl(customer.getId()));
                    return customerDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {

        return customerRepository
                .findById(id)
                .map(customerMapper::customerToCustomerDto)
                .map(customerDTO -> {
                    customerDTO.setCustomerUrl(getCustomerUrl(id));
                    return customerDTO;
                })
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {

        return saveAndReturnDTO(customerMapper.customerDtoToCustomer(customerDTO));
    }

    @Override
    public CustomerDTO saveCustomerByDTO(Long id, CustomerDTO customerDTO) {

        Customer customer = customerMapper.customerDtoToCustomer(customerDTO);
        customer.setId(id);

        return saveAndReturnDTO(customer);
    }

    @Override
    public CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO) {

        return customerRepository.findById(id).map(customer -> {

            if (customerDTO.getFirstName() != null) {
                customer.setFirstName(customerDTO.getFirstName());
            }

            if (customerDTO.getLastName() != null) {
                customer.setLastName(customerDTO.getLastName());
            }

            CustomerDTO returnDTO = customerMapper.customerToCustomerDto(customerRepository.save(customer));
            returnDTO.setCustomerUrl(getCustomerUrl(id));

            return returnDTO;
        }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteCustomerById(Long id) {

        customerRepository.findById(id).ifPresent(customer -> customerRepository.deleteById(customer.getId()));
    }

    private String getCustomerUrl(Long id) {

        return CustomerController.BASE_URL + "/" + id;
    }

    private CustomerDTO saveAndReturnDTO(Customer customer) {

        Customer savedCustomer = customerRepository.save(customer);
        CustomerDTO returnDto = customerMapper.customerToCustomerDto(savedCustomer);
        returnDto.setCustomerUrl(getCustomerUrl(savedCustomer.getId()));
        return returnDto;
    }
}
