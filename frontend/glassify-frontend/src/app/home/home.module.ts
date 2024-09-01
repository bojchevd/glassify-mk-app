import { NgModule } from '@angular/core';
import { ProductModule } from '../product/product.module';
import { LayoutModule } from '../layout/layout.module';
import { SharedModule } from '../shared/shared.module';
import { HomeComponent } from './home.component';
import { LandingPageComponent } from './landing-page/landing-page.component';
import { HeroComponent } from './hero/hero.component';

@NgModule({
  declarations: [
    HomeComponent,
    LandingPageComponent
  ],
  imports: [
    ProductModule,
    LayoutModule,
    SharedModule,
    HeroComponent
  ], exports: [
    LandingPageComponent
  ]
})
export class HomeModule { }
