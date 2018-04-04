package hr.from.ivantoplak.springmvcrest.controllers.v1;

import hr.from.ivantoplak.springmvcrest.api.v1.model.VendorDTO;
import hr.from.ivantoplak.springmvcrest.api.v1.model.VendorListDTO;
import hr.from.ivantoplak.springmvcrest.services.VendorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Api(description = "Vendor API")
@RestController
@RequestMapping(VendorController.BASE_URL)
public class VendorController {

    public static final String BASE_URL = "/api/v1/vendors";
    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @ApiOperation(value = "Get a list of vendors")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public VendorListDTO getListOfVendors() {

        return new VendorListDTO(vendorService.getAllVendors());
    }

    @ApiOperation(value = "Get vendor by ID")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO getVendorById(@PathVariable Long id) {

        return vendorService.getVendorById(id);
    }

    @ApiOperation(value = "Create a new vendor")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VendorDTO createNewVendor(@RequestBody VendorDTO vendorDTO) {

        return vendorService.createNewVendor(vendorDTO);
    }

    @ApiOperation(value = "Update an existing vendor")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO updateVendor(@PathVariable Long id, @RequestBody VendorDTO vendorDTO) {

        return vendorService.saveVendorByDTO(id, vendorDTO);
    }

    @ApiOperation(value = "Update a vendor property")
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO patchVendor(@PathVariable Long id, @RequestBody VendorDTO vendorDTO) {

        return vendorService.patchVendor(id, vendorDTO);
    }

    @ApiOperation(value = "Delete a vendor")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteVendor(@PathVariable Long id) {

        vendorService.deleteVendorById(id);
    }
}
