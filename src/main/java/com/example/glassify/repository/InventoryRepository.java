package com.example.glassify.repository;

import com.example.glassify.model.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<InventoryItem, Long> {
}
