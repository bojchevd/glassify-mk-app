import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProductViewDetailsComponent } from './product-view-details/product-view-details.component';
import { LandingPageComponent } from './home/landing-page/landing-page.component';
import { ContactComponent } from './contact/contact.component';
import { ProductPageComponent } from './product-page/product-page.component';

const routes: Routes = [
  { path: '', component: LandingPageComponent },
  { path: 'products', component: ProductPageComponent },
  { path: 'productViewDetails/:id', component: ProductViewDetailsComponent },
  { path: 'contact', component: ContactComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
