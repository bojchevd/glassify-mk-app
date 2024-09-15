import { Component, AfterViewInit, ViewChild  } from '@angular/core';
import { MatMenuTrigger } from '@angular/material/menu';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements AfterViewInit {

  @ViewChild('mobileMenu') menuTrigger!: MatMenuTrigger;

  mobileMenuOpen = false;

  constructor() { }

  ngAfterViewInit(): void {
    console.log("MatMenuTrigger:", this.menuTrigger);
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