package hr.from.ivantoplak.springmvcrest.controllers.v1;

import hr.from.ivantoplak.springmvcrest.api.v1.model.VendorDTO;
import hr.from.ivantoplak.springmvcrest.controllers.RestResponseEntityExceptionHandler;
import hr.from.ivantoplak.springmvcrest.services.ResourceNotFoundException;
import hr.from.ivantoplak.springmvcrest.services.VendorService;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VendorControllerTest {

    private static final String NAME_1 = "Joe";
    private static final String NAME_2 = "Moe";

    @Mock
    private VendorService vendorService;

    @InjectMocks
    private VendorController vendorController;

    private MockMvc mockMvc;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(vendorController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }

    @Test
    public void getListOfVendors() throws Exception {

        //given
        VendorDTO vendorDTO1 = new VendorDTO();
        vendorDTO1.setName(NAME_1);
        vendorDTO1.setVendorUrl(VendorController.BASE_URL + "/1");

        VendorDTO vendorDTO2 = new VendorDTO();
        vendorDTO2.setName(NAME_2);
        vendorDTO2.setVendorUrl(VendorController.BASE_URL + "/2");

        List<VendorDTO> vendors = Arrays.asList(vendorDTO1, vendorDTO2);

        given(vendorService.getAllVendors()).willReturn(vendors);

        //when
        mockMvc.perform(get(VendorController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) //then
                .andExpect(jsonPath("$.vendors", hasSize(2)));
    }

    @Test
    public void getVendorById() throws Exception {

        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME_1);
        vendorDTO.setVendorUrl(VendorController.BASE_URL + "/1");

        given(vendorService.getVendorById(anyLong())).willReturn(vendorDTO);

        //when
        mockMvc.perform(get(VendorController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) //then
                .andExpect(jsonPath("$.name", equalTo(NAME_1)));
    }

    @Test
    public void createNewVendor() throws Exception {

        //given
        VendorDTO vendor = new VendorDTO();
        vendor.setName(NAME_1);

        VendorDTO returnDTO = new VendorDTO();
        returnDTO.setName(vendor.getName());
        returnDTO.setVendorUrl(VendorController.BASE_URL + "/1");

        given(vendorService.createNewVendor(vendor)).willReturn(returnDTO);

        //when
        mockMvc.perform(post(VendorController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendor)))
                .andExpect(status().isCreated()) //then
                .andExpect(jsonPath("$.name", equalTo(NAME_1)))
                .andExpect(jsonPath("$.vendor_url", equalTo(VendorController.BASE_URL + "/1")));
    }

    @Test
    public void updateVendor() throws Exception {

        //given
        VendorDTO vendor = new VendorDTO();
        vendor.setName(NAME_1);

        VendorDTO returnDTO = new VendorDTO();
        returnDTO.setName(vendor.getName());
        returnDTO.setVendorUrl(VendorController.BASE_URL + "/1");

        given(vendorService.saveVendorByDTO(anyLong(), any(VendorDTO.class))).willReturn(returnDTO);

        //when
        mockMvc.perform(put(VendorController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendor)))
                .andExpect(status().isOk()) //then
                .andExpect(jsonPath("$.name", equalTo(NAME_1)))
                .andExpect(jsonPath("$.vendor_url", equalTo(VendorController.BASE_URL + "/1")));
    }

    @Test
    public void patchVendor() throws Exception {

        //given
        VendorDTO vendor = new VendorDTO();
        vendor.setName(NAME_1);

        VendorDTO returnDTO = new VendorDTO();
        returnDTO.setName(vendor.getName());
        returnDTO.setVendorUrl(VendorController.BASE_URL + "/1");

        given(vendorService.patchVendor(anyLong(), any(VendorDTO.class))).willReturn(returnDTO);

        //when
        mockMvc.perform(patch(VendorController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendor)))
                .andExpect(status().isOk()) //then
                .andExpect(jsonPath("$.name", equalTo(NAME_1)))
                .andExpect(jsonPath("$.vendor_url", equalTo(VendorController.BASE_URL + "/1")));
    }

    @Test
    public void deleteVendor() throws Exception {

        mockMvc.perform(delete(VendorController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(vendorService).deleteVendorById(anyLong());
    }

    @Test
    public void getVendorByIdNotFound() throws Exception {

        when(vendorService.getVendorById(anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(VendorController.BASE_URL + "/222")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}