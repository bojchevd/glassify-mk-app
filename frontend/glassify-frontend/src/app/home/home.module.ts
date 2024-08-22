import { NgModule } from '@angular/core';
import { ProductModule } from '../product/product.module';
import { LayoutModule } from '../layout/layout.module';
import { HomeComponent } from './home.component'; // Example component
import { SharedModule } from '../shared/shared.module';

@NgModule({
  declarations: [
    HomeComponent
  ],
  imports: [
    ProductModule,
    LayoutModule,
    SharedModule
  ]
})
export class HomeModule { }
