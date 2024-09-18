import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OrderViewComponent } from './order-view/order-view.component';
import { SharedModule } from '../shared/shared.module';
import { LayoutModule } from '../layout/layout.module';



@NgModule({
  declarations: [
    OrderViewComponent
  ],
  imports: [
    CommonModule,
    SharedModule,
    LayoutModule
  ]
})
export class AdminModule { }
