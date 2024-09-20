import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { Router } from '@angular/router';
import { OrderService } from 'src/app/order/order.service';
import { MatTableDataSource } from '@angular/material/table';
import { CommonModule } from '@angular/common';
import { SharedModule } from 'src/app/shared/shared.module';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmationDialogComponent } from 'src/app/shared/confirmation-dialog/confirmation-dialog.component';

@Component({
  selector: 'app-order-view',
  templateUrl: './order-view.component.html',
  styleUrl: './order-view.component.css'
})
export class OrderViewComponent implements OnInit {
  displayedColumns: string[] = ['fullName', 'orderStatus', 'totalPrice', 'createdAt', 'actions'];
  dataSource = new MatTableDataSource<any>();
  totalOrders: number = 0;
  itemsPerPage: number = 10;
  orderStatusOptions: string[] = ['NEW', 'DESIGNED', 'SHIPPED', 'DELIVERED'];

  @ViewChild(MatPaginator) paginator: MatPaginator | undefined;

  constructor(private orderService: OrderService, 
              private router: Router,
              private snackBar: MatSnackBar,
              public dialog: MatDialog) { }

  ngOnInit() {
    this.loadOrders();
  }

  loadOrders(pageIndex: number = 0, pageSize: number = this.itemsPerPage) {
    const offset = pageIndex * pageSize;
    this.orderService.getOrders(offset, pageSize).subscribe(response => {
      console.log(response.orders)
      this.dataSource.data = response.orders;
      this.totalOrders = response.totalCount;
      if (this.paginator) {
        this.paginator.pageIndex = pageIndex;
        this.paginator.pageSize = pageSize;
      }
    });
  }

  modifyStatus(orderId: number, newStatus: string, event: Event) {
    event.stopPropagation();
    this.orderService.updateOrderStatus(orderId, newStatus).subscribe(() => {
      this.loadOrders(); 
    });
  }

  deleteOrder(orderId: number, event: Event) {
    event.stopPropagation(); // Prevent row click from triggering
    if (confirm('Are you sure you want to delete this order?')) {
      this.orderService.deleteOrder(orderId).subscribe(() => {
        this.openConfirmationDialog('Нарачката е избришана')
        this.loadOrders(); // Refresh the list after deletion
      });
    }
  }
  onPageChange(event: PageEvent) {
    this.loadOrders(event.pageIndex, event.pageSize);
  }

  onStatusChange(orderId: number, newStatus: string): void {
    const confirmUpdate = confirm(`Are you sure you want to change the order status to ${newStatus}?`);
    if (confirmUpdate) {
      this.orderService.updateOrderStatus(orderId, newStatus).subscribe(() => {
        this.openConfirmationDialog('Статусот на нарачката е променет')
        this.loadOrders(); // Reload orders to reflect the new status
      });
    }
  }

  viewOrderDetails(order: any) {
    this.router.navigate(['/order-detail', order.id], { state: { order: order } });
  }

  openConfirmationDialog(message: string): void {
    this.dialog.open(ConfirmationDialogComponent, {
      width: '300px',
      data: { message: message }
    });
  }
}