import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { InventoryService } from '../inventory.service';
import { MatTableDataSource } from '@angular/material/table';

export interface InventoryItem {
  id: number;
  name: string;
  stockQuantity: number;
  vendor: string;
  contactPhoneNumber: string;
  contactEmail: string;
  updatedAt: string;
}

@Component({
  selector: 'app-inventory-view',
  templateUrl: './inventory-view.component.html',
  styleUrl: './inventory-view.component.css'
})
export class InventoryViewComponent implements OnInit {

  displayedColumns: string[] = ['name', 'stockQuantity', 'vendor', 'contactPhoneNumber', 'contactEmail', 'updatedAt', 'actions'];
  dataSource: MatTableDataSource<InventoryItem> = new MatTableDataSource<InventoryItem>([]);

  constructor(private inventoryService: InventoryService, private snackBar: MatSnackBar) {}

  ngOnInit(): void {
    this.getInventoryItems();
  }

  getInventoryItems(): void {
    this.inventoryService.getInventory().subscribe((data: InventoryItem[]) => {
      this.dataSource.data = data.map(item => ({ ...item, isEditing: false }));
    });
  }

  enableEdit(element: any): void {
    element.isEditing = true;
  }

  onEdit(item: InventoryItem): void {
    // Implement edit logic (open a dialog or navigate to an edit form)
    console.log('Editing item:', item);
    this.snackBar.open('Edit feature not implemented yet', 'Close', { duration: 3000 });
  }

  saveEdit(element: any): void {
    element.isEditing = false;

    this.inventoryService.updateStockQuantity(element.id, element.stockQuantity).subscribe(
      () => {
        this.snackBar.open('Успешна промена на количина!', 'Close', { duration: 2000 });
      },
      error => {
        this.snackBar.open('Неуспешна промена на количина!', 'Close', { duration: 2000 });
      }
    );
  }

  onDelete(id: number): void {
    if (confirm('Сигурно сакаш да избришеш??')) {
      this.inventoryService.deleteInventoryItem(id).subscribe(() => {
        this.snackBar.open('Успешно избришан материјал', 'Close', { duration: 3000 });
        this.getInventoryItems(); // Refresh the table data
      });
    }
  }

  cancelEdit(element: any): void {
    element.isEditing = false;
    this.getInventoryItems();
  }

}
