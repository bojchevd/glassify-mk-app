import { Component } from '@angular/core';
import { SharedModule } from 'src/app/shared/shared.module';
import { LayoutModule } from 'src/app/layout/layout.module';
import { ProductModule } from 'src/app/product/product.module';
import { HeroComponent } from '../hero/hero.component';
import { AppRoutingModule } from 'src/app/app-routing.module';

@Component({
  selector: 'app-landing-page',
  templateUrl: './landing-page.component.html',
  styleUrls: ['./landing-page.component.css']
})
export class LandingPageComponent { }
