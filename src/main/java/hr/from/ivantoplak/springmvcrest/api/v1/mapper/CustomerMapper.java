package hr.from.ivantoplak.springmvcrest.api.v1.mapper;

import hr.from.ivantoplak.springmvcrest.api.v1.model.CustomerDTO;
import hr.from.ivantoplak.springmvcrest.domain.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    CustomerDTO customerToCustomerDto(Customer customer);

    Customer customerDtoToCustomer(CustomerDTO customerDTO);
}
