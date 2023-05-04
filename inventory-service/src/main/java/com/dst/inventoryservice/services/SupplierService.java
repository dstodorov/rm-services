package com.dst.inventoryservice.services;

import com.dst.inventoryservice.exceptions.DuplicatedSupplierException;
import com.dst.inventoryservice.exceptions.SupplierNotFoundException;
import com.dst.inventoryservice.models.Product;
import com.dst.inventoryservice.models.Supplier;
import com.dst.inventoryservice.models.dtos.ProductDTO;
import com.dst.inventoryservice.models.dtos.SupplierDTO;
import com.dst.inventoryservice.repositories.SupplierRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class SupplierService {

    private final SupplierRepository supplierRepository;

    public Long createSupplier(SupplierDTO supplierDTO) {

        Optional<Supplier> supplierByName = this.supplierRepository.findByName(supplierDTO.name());
        Optional<Supplier> supplierByEmail = this.supplierRepository.findByEmail(supplierDTO.email());
        Optional<Supplier> supplierByPhoneNumber = this.supplierRepository.findByPhoneNumber(supplierDTO.phoneNumber());

        if (supplierByName.isPresent() || supplierByEmail.isPresent() || supplierByPhoneNumber.isPresent()) {
            return -1L;
        }

        Supplier supplier = map(supplierDTO);

        return this.supplierRepository.save(supplier).getId();
    }

    public Optional<SupplierDTO> getSupplier(Long id) {
        return this.supplierRepository.findById(id).map(this::mapToSupplierDTO);
    }

    public SupplierDTO updateSupplier(Long id, SupplierDTO supplierDTO) {
        // Check if supplier exists, if not, throw exception
        Supplier supplier = this.supplierRepository
                .findById(id)
                .orElseThrow(() -> new SupplierNotFoundException(id.toString()));

        Optional<Supplier> supplierByNameAndEmailAndPhoneNumber = this.supplierRepository
                .findByNameAndEmailAndPhoneNumber(id,
                        supplierDTO.name(),
                        supplierDTO.email(),
                        supplierDTO.phoneNumber());

        // Throw duplication exception if there record with same name/email/phoneNumber
        if (supplierByNameAndEmailAndPhoneNumber.isPresent()) {
            throw new DuplicatedSupplierException(id.toString());
        }

        // Saving changes
        supplier.setName(supplierDTO.name());
        supplier.setEmail(supplierDTO.email());
        supplier.setPhoneNumber(supplierDTO.phoneNumber());
        supplier.setDescription(supplierDTO.description());

        this.supplierRepository.save(supplier);

        return SupplierDTO
                .builder()
                .id(id)
                .name(supplierDTO.name())
                .email(supplierDTO.email())
                .phoneNumber(supplierDTO.phoneNumber())
                .description(supplierDTO.description())
                .build();
    }

    public void changeStatus() {

    }

    private SupplierDTO mapToSupplierDTO(Supplier supplier) {
        return SupplierDTO
                .builder()
                .id(supplier.getId())
                .name(supplier.getName())
                .phoneNumber(supplier.getPhoneNumber())
                .email(supplier.getEmail())
                .description(supplier.getDescription())
                .build();
    }

    private Supplier map(SupplierDTO supplierDTO) {
        return Supplier
                .builder()
                .name(supplierDTO.name())
                .phoneNumber(supplierDTO.phoneNumber())
                .email(supplierDTO.email())
                .description(supplierDTO.description())
                .build();
    }


}
