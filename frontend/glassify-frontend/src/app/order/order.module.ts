import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
// import { OrderListComponent } from './order-list/order-list.component';
import { SharedModule } from '../shared/shared.module';
import { OrderService } from './order.service';
import { ShippingFormComponent } from '../shipping-form/shipping-form.component';



@NgModule({
  declarations: [
    // OrderListComponent
    ShippingFormComponent
  ],
  imports: [
    CommonModule,
    SharedModule
  ], exports: [
    ShippingFormComponent
    // OrderListComponent
  ],
  providers: [
    OrderService
  ]

})
export class OrderModule { }
