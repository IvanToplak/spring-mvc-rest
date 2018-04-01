package hr.from.ivantoplak.springmvcrest.services;

import hr.from.ivantoplak.springmvcrest.api.v1.mapper.VendorMapper;
import hr.from.ivantoplak.springmvcrest.api.v1.model.VendorDTO;
import hr.from.ivantoplak.springmvcrest.controllers.v1.VendorController;
import hr.from.ivantoplak.springmvcrest.domain.Vendor;
import hr.from.ivantoplak.springmvcrest.repositories.VendorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendorServiceImpl implements VendorService {

    private final VendorMapper vendorMapper;
    private final VendorRepository vendorRepository;

    public VendorServiceImpl(VendorMapper vendorMapper, VendorRepository vendorRepository) {
        this.vendorMapper = vendorMapper;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public List<VendorDTO> getAllVendors() {

        return vendorRepository
                .findAll()
                .stream()
                .map(vendor -> {
                    VendorDTO vendorDTO = vendorMapper.vendorToVendorDto(vendor);
                    vendorDTO.setVendorUrl(getVendorUrl(vendor.getId()));
                    return vendorDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public VendorDTO getVendorById(Long id) {

        return vendorRepository
                .findById(id)
                .map(vendorMapper::vendorToVendorDto)
                .map(vendorDTO -> {
                    vendorDTO.setVendorUrl(getVendorUrl(id));
                    return vendorDTO;
                })
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public VendorDTO createNewVendor(VendorDTO vendorDTO) {

        return saveAndReturnDTO(vendorMapper.vendorDtoToVendor(vendorDTO));
    }

    @Override
    public VendorDTO saveVendorByDTO(Long id, VendorDTO vendorDTO) {

        Vendor vendor = vendorMapper.vendorDtoToVendor(vendorDTO);
        vendor.setId(id);

        return saveAndReturnDTO(vendor);
    }

    @Override
    public VendorDTO patchVendor(Long id, VendorDTO vendorDTO) {

        return vendorRepository.findById(id).map(vendor -> {

            if (vendorDTO.getName() != null) {
                vendor.setName(vendorDTO.getName());
            }

            VendorDTO returnDto = vendorMapper.vendorToVendorDto(vendorRepository.save(vendor));
            returnDto.setVendorUrl(getVendorUrl(id));

            return returnDto;
        }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteVendorById(Long id) {

        vendorRepository.findById(id).ifPresent(vendor -> vendorRepository.deleteById(vendor.getId()));
    }

    private String getVendorUrl(Long id) {

        return VendorController.BASE_URL + "/" + id;
    }

    private VendorDTO saveAndReturnDTO(Vendor vendor) {

        Vendor savedVendor = vendorRepository.save(vendor);
        VendorDTO returnDto = vendorMapper.vendorToVendorDto(savedVendor);
        returnDto.setVendorUrl(getVendorUrl(savedVendor.getId()));
        return returnDto;
    }
}
