package hr.from.ivantoplak.springmvcrest.services;

import hr.from.ivantoplak.springmvcrest.api.v1.mapper.VendorMapper;
import hr.from.ivantoplak.springmvcrest.api.v1.model.VendorDTO;
import hr.from.ivantoplak.springmvcrest.controllers.v1.VendorController;
import hr.from.ivantoplak.springmvcrest.domain.Vendor;
import hr.from.ivantoplak.springmvcrest.repositories.VendorRepository;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

public class VendorServiceImplTest {

    private static final long ID = 1L;
    private static final String NAME = "John";
    private static final int SIZE = 3;

    @Mock
    private VendorRepository vendorRepository;

    private VendorService vendorService;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);
        vendorService = new VendorServiceImpl(VendorMapper.INSTANCE, vendorRepository);
    }

    @Test
    public void getAllVendors() {

        //given
        List<Vendor> vendors = Arrays.asList(new Vendor(), new Vendor(), new Vendor());

        given(vendorRepository.findAll()).willReturn(vendors);

        //when
        List<VendorDTO> vendorDTOS = vendorService.getAllVendors();

        //then
        then(vendorRepository).should(times(1)).findAll();
        assertEquals(SIZE, vendorDTOS.size());
    }

    @Test
    public void getVendorById() {

        //given
        Vendor vendor = getVendor();

        given(vendorRepository.findById(anyLong())).willReturn(Optional.of(vendor));

        //when
        VendorDTO vendorDTO = vendorService.getVendorById(ID);

        //then
        then(vendorRepository).should().findById(anyLong());
        assertEquals(NAME, vendorDTO.getName());
    }


    @Test(expected = ResourceNotFoundException.class)
    public void getVendorByIdNotFound() {

        //given
        given(vendorRepository.findById(anyLong())).willReturn(Optional.empty());

        //when
        vendorService.getVendorById(ID);

        //then
        then(vendorRepository).should().findById(anyLong());

    }

    @Test
    public void createNewVendor() {

        //given
        VendorDTO vendorDTO = getVendorDTO();

        Vendor savedVendor = getVendor();

        given(vendorRepository.save(any(Vendor.class))).willReturn(savedVendor);

        //when
        VendorDTO savedDto = vendorService.createNewVendor(vendorDTO);

        //then
        then(vendorRepository).should().save(any(Vendor.class));
        assertEquals(vendorDTO.getName(), savedDto.getName());
        assertEquals(VendorController.BASE_URL + "/1", savedDto.getVendorUrl());
    }

    @Test
    public void saveVendorByDTO() {

        //given
        VendorDTO vendorDTO = getVendorDTO();

        Vendor savedVendor = getVendor();

        given(vendorRepository.save(any(Vendor.class))).willReturn(savedVendor);

        //when
        VendorDTO savedDto = vendorService.saveVendorByDTO(ID, vendorDTO);

        //then
        then(vendorRepository).should().save(any(Vendor.class));
        assertEquals(vendorDTO.getName(), savedDto.getName());
        assertEquals(VendorController.BASE_URL + "/1", savedDto.getVendorUrl());
    }

    @Test
    public void patchVendor() {

        //given
        VendorDTO vendorDTO = getVendorDTO();

        Vendor vendor = getVendor();

        given(vendorRepository.findById(anyLong())).willReturn(Optional.of(vendor));
        given(vendorRepository.save(any(Vendor.class))).willReturn(vendor);

        //when

        VendorDTO savedDto = vendorService.patchVendor(ID, vendorDTO);

        //then
        then(vendorRepository).should().save(any(Vendor.class));
        then(vendorRepository).should().findById(anyLong());
        assertEquals(VendorController.BASE_URL + "/1", savedDto.getVendorUrl());
    }

    @Test
    public void deleteVendorById() {

        //when
        vendorService.deleteVendorById(ID);

        //then
        then(vendorRepository).should().findById(anyLong());
    }

    private Vendor getVendor() {

        Vendor vendor = new Vendor();
        vendor.setId(ID);
        vendor.setName(NAME);
        return vendor;
    }

    private VendorDTO getVendorDTO() {

        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME);
        return vendorDTO;
    }
}