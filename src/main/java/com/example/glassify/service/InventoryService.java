package com.example.glassify.service;

import com.example.glassify.model.InventoryItem;
import com.example.glassify.model.dto.InventoryItemDTO;
import com.example.glassify.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    public List<InventoryItem> findAllInventoryItems() {
        return inventoryRepository.findAll();
    }

    public InventoryItem addInventoryItem(InventoryItemDTO inventoryItemDTO) {
        InventoryItem inventoryItem = new InventoryItem();
        inventoryItem.setName(inventoryItemDTO.getName());
        inventoryItem.setStockQuantity(inventoryItemDTO.getStockQuantity());
        inventoryItem.setVendor(inventoryItemDTO.getVendor());
        inventoryItem.setContactEmail(inventoryItemDTO.getContactEmail());
        inventoryItem.setContactPhoneNumber(inventoryItemDTO.getContactPhoneNumber());
        return inventoryRepository.save(inventoryItem);
    }

    public void deleteInventoryItem(Long id) {
        inventoryRepository.deleteById(id);
    }

    public Optional<InventoryItem> updateStockQuantity(Long id, Integer newQuantity) {
        Optional<InventoryItem> inventoryItem = inventoryRepository.findById(id);
        inventoryItem.ifPresent(item -> {
            item.setStockQuantity(newQuantity);
            inventoryRepository.save(item);
        });
        return inventoryItem;
    }

    public Optional<InventoryItem> adjustStockQuantity(Long id, Integer delta) {
        Optional<InventoryItem> inventoryItem = inventoryRepository.findById(id);
        inventoryItem.ifPresent(item -> {
            item.setStockQuantity(item.getStockQuantity() + delta);
            inventoryRepository.save(item);
        });
        return inventoryItem;
    }

    public Optional<InventoryItem> updateContactDetails(Long id, String vendor, String phoneNumber, String email) {
        Optional<InventoryItem> inventoryItem = inventoryRepository.findById(id);
        inventoryItem.ifPresent(item -> {
            item.setVendor(vendor);
            item.setContactPhoneNumber(phoneNumber);
            item.setContactEmail(email);
            inventoryRepository.save(item);
        });
        return inventoryItem;
    }

    public List<InventoryItem> getAllInventoryItems() {
        return inventoryRepository.findAll();
    }
}
