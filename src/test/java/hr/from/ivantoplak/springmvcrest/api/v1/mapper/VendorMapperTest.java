package hr.from.ivantoplak.springmvcrest.api.v1.mapper;

import hr.from.ivantoplak.springmvcrest.api.v1.model.VendorDTO;
import hr.from.ivantoplak.springmvcrest.domain.Vendor;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class VendorMapperTest {

    private static final String NAME = "John";

    private VendorMapper vendorMapper = VendorMapper.INSTANCE;

    @Test
    public void vendorToVendorDto() {

        //given
        Vendor vendor = new Vendor();
        vendor.setName(NAME);

        //when
        VendorDTO vendorDTO = vendorMapper.vendorToVendorDto(vendor);

        //then
        assertEquals(NAME, vendorDTO.getName());
    }

    @Test
    public void vendorDtoToVendor() {

        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME);

        //when
        Vendor vendor = vendorMapper.vendorDtoToVendor(vendorDTO);

        //then
        assertEquals(NAME, vendor.getName());
    }
}