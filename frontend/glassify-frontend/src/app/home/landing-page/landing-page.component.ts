import { Component } from '@angular/core';
import { SharedModule } from 'src/app/shared/shared.module';
import { LayoutModule } from 'src/app/layout/layout.module';
import { ProductModule } from 'src/app/product/product.module';

@Component({
  selector: 'app-landing-page',
  standalone: true,
  imports: [
    SharedModule,
    LayoutModule,
    ProductModule
  ],
  templateUrl: './landing-page.component.html',
  styleUrls: ['./landing-page.component.css']
})
export class LandingPageComponent { }
