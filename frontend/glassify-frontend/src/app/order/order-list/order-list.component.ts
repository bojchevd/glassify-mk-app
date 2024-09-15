// import { Component, OnInit, ViewChild } from '@angular/core';
// import { MatTableDataSource } from '@angular/material/table';
// import { OrderService } from '../order.service';

// @Component({
//   selector: 'app-order-list',
//   templateUrl: './order-list.component.html',
//   styleUrls: ['./order-list.component.css']
// })
// export class OrderListComponent implements OnInit {

//   displayedColumns: string[] = ['fullName', 'phoneNumber', 'city', 'address', 'totalPrice', 'orderStatus', 'itemCount'];
//   dataSource = new MatTableDataSource<any>();

//   @ViewChild(MatPaginator) paginator!: MatPaginator;
//   @ViewChild(MatSort) sort!: MatSort;

//   constructor(private orderService: OrderService) { }

//   ngOnInit(): void {
//     this.loadOrders();
//   }

//   loadOrders(page: number = 0, size: number = 10, sort: string = 'createdAt,desc'): void {
//     this.orderService.getOrders(page, size, sort).subscribe(data => {
//       this.dataSource.data = data.orders;
//       this.paginator.length = data.totalCount;
//     });
//   }

//   ngAfterViewInit() {
//     this.dataSource.paginator = this.paginator;
//     this.dataSource.sort = this.sort;
//   }
// }