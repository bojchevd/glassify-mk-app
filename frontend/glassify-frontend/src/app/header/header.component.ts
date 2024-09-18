import { Component, AfterViewInit, ViewChild  } from '@angular/core';
import { MatMenuTrigger } from '@angular/material/menu';
import { CartService } from '../cart/cart.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements AfterViewInit {

  @ViewChild('mobileMenu') menuTrigger!: MatMenuTrigger;

  mobileMenuOpen = false;
  itemCount: number = 0;

  constructor(private cartService: CartService) { }

  ngAfterViewInit(): void {
    // Log MatMenuTrigger for debugging purposes
    console.log("MatMenuTrigger:", this.menuTrigger);

    // Subscribe to itemCount$ to update itemCount property
    this.cartService.itemCount$.subscribe(count => {
      this.itemCount = count;
    });
  }

  toggleMenu(): void {
    if (this.menuTrigger) {
      if (this.mobileMenuOpen) {
        this.menuTrigger.closeMenu();
        console.log("Menu closed");
      } else {
        this.menuTrigger.openMenu();
        console.log("Menu opened");
      }
      this.mobileMenuOpen = !this.mobileMenuOpen;
    }
  }
}