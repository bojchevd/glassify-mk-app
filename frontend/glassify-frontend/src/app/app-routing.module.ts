import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProductViewDetailsComponent } from './product-view-details/product-view-details.component';
import { LandingPageComponent } from './home/landing-page/landing-page.component';
import { ContactComponent } from './contact/contact.component';
import { ProductPageComponent } from './product-page/product-page.component';
import { CartComponent } from './cart/cart/cart.component';
import { OrderViewComponent } from './admin/order-view/order-view.component';
import { InventoryViewComponent } from './admin/inventory-view/inventory-view.component';
import { AuthGuard } from './auth/auth.guard';
import { AdminGuard } from './auth/admin.guard';
import { LoginComponent } from './auth/login/login.component';
import { OrderDetailComponent } from './admin/order-detail/order-detail.component';
import { UsersViewComponent } from './admin/users-view/users-view.component';

const routes: Routes = [
  { path: '', component: LandingPageComponent },
  { path: 'products', component: ProductPageComponent },
  { path: 'productViewDetails/:id', component: ProductViewDetailsComponent, canActivate: [AuthGuard, AdminGuard] },
  { path: 'login', component: LoginComponent },
  { path: 'contact', component: ContactComponent },
  { path: 'cart', component: CartComponent },
  { path: 'admin', component: OrderViewComponent, canActivate: [AuthGuard, AdminGuard] },
  { path : 'admin/inventory', component: InventoryViewComponent, canActivate: [AuthGuard, AdminGuard] },
  { path: 'order-detail/:id', component: OrderDetailComponent },
  { path: 'admin/users', component: UsersViewComponent, canActivate: [AuthGuard, AdminGuard] }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
