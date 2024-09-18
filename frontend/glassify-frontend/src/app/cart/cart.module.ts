import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CartService } from './cart.service';
import { CartComponent } from './cart/cart.component';
import { HeaderComponent } from '../header/header.component';
import { FooterComponent } from '../footer/footer.component';
import { LayoutModule } from '../layout/layout.module';
import { RouterModule } from '@angular/router';
import { OrderModule } from '../order/order.module';
import { SharedModule } from '../shared/shared.module';

@NgModule({
  declarations: [
    CartComponent
  ],
  imports: [
    CommonModule,
    LayoutModule,
    RouterModule,
    OrderModule,
    SharedModule
  ],
  providers: [CartService]
})
export class CartModule { }
