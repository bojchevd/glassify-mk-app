package com.example.glassify.web;

import com.example.glassify.model.InventoryItem;
import com.example.glassify.model.dto.InventoryItemDTO;
import com.example.glassify.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;
    @GetMapping("/get")
    public ResponseEntity<List<InventoryItem>> getInventory() {
        List<InventoryItem> inventoryItems = inventoryService.findAllInventoryItems();
        return ResponseEntity.ok(inventoryItems);
    }

    @PostMapping("/add")
    public ResponseEntity<InventoryItem> addInventoryItem(@RequestBody InventoryItemDTO inventoryItemDTO) {
        InventoryItem createdItem = inventoryService.addInventoryItem(inventoryItemDTO);
        return ResponseEntity.ok(createdItem);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteInventoryItem(@PathVariable Long id) {
        inventoryService.deleteInventoryItem(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update-stock/{id}")
    public ResponseEntity<InventoryItem> updateStockQuantity(@PathVariable Long id,
                                                             @RequestParam Integer stockQuantity) {
        Optional<InventoryItem> updatedItem = inventoryService.updateStockQuantity(id, stockQuantity);
        return updatedItem.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/adjust-stock/{id}")
    public ResponseEntity<InventoryItem> adjustStockQuantity(@PathVariable Long id,
                                                             @RequestParam Integer delta) {
        Optional<InventoryItem> updatedItem = inventoryService.adjustStockQuantity(id, delta);
        return updatedItem.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/update-contact/{id}")
    public ResponseEntity<InventoryItem> updateContactDetails(@PathVariable Long id,
                                                              @RequestParam String vendor,
                                                              @RequestParam String phoneNumber,
                                                              @RequestParam String email) {
        Optional<InventoryItem> updatedItem = inventoryService.updateContactDetails(id, vendor, phoneNumber, email);
        return updatedItem.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


}
