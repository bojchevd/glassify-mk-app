import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OrderViewComponent } from './order-view/order-view.component';
import { SharedModule } from '../shared/shared.module';
import { LayoutModule } from '../layout/layout.module';
import { InventoryViewComponent } from './inventory-view/inventory-view.component';
import { AdminHeaderComponent } from './admin-header/admin-header.component';
import { UsersViewComponent } from './users-view/users-view.component';



@NgModule({
  declarations: [
    OrderViewComponent,
    InventoryViewComponent,
    AdminHeaderComponent,
    UsersViewComponent
  ],
  imports: [
    CommonModule,
    SharedModule,
    LayoutModule
  ],
  exports: [
    AdminHeaderComponent
  ]
})
export class AdminModule { }
