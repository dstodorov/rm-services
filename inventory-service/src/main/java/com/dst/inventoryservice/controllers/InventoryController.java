package com.dst.inventoryservice.controllers;

import com.dst.inventoryservice.models.dtos.AddInventoryProductDTO;
import com.dst.inventoryservice.models.dtos.InventoryProductInfoDTO;
import com.dst.inventoryservice.services.InventoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/inventory")
@AllArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<AddInventoryProductDTO> addInventoryProduct(@Valid @RequestBody AddInventoryProductDTO inventoryProductDTO,
                                                                      UriComponentsBuilder uriComponentsBuilder) {

        Long inventoryProductId = this.inventoryService.addToInventory(inventoryProductDTO);

        return ResponseEntity
                .created(uriComponentsBuilder
                        .path("/api/v1/inventory/{id}")
                        .build(inventoryProductId))
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryProductInfoDTO> getInventoryProductInfo(@PathVariable Long id) {

        InventoryProductInfoDTO inventoryProductInfo = this.inventoryService.getInventoryProductInfo(id);

        return ResponseEntity.ok(inventoryProductInfo);
    }
}
