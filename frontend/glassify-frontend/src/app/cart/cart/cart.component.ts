import { Component, OnInit } from '@angular/core';
import { CartService } from '../cart.service';
import { CartItem } from 'src/app/model/cart-item.model';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {
  cartItems: CartItem[] = [];
  cartId: string | null = null;

  constructor(private cartService: CartService) {}

  ngOnInit(): void {
    this.cartId = this.cartService.getCartId();
    if (this.cartId) {
      this.loadCartItems();
    }
  }

  loadCartItems(): void {
    if (this.cartId) {
      this.cartService.getCartItems(this.cartId).subscribe(items => {
        this.cartItems = items;
      });
    }
  }

  calculateProductPrice(item: CartItem): number {
    return 100; // Placeholder value, replace with actual price calculation
  }

  calculateCartPrice(): number {
    return this.cartItems.reduce((total, item) => total + (this.calculateProductPrice(item) * item.quantity), 0);
  }

  calculateShippingPrice(): number {
    const cartPrice = this.calculateCartPrice();
    return cartPrice >= 2000 ? 0 : 150; 
  }

  removeItem(itemId: string): void {
    if (this.cartId) {  // Ensure cartId is not null
      this.cartService.removeItemFromCart(this.cartId, itemId).subscribe(() => {
        this.loadCartItems(); // Reload cart items after removing one
      });
    }
  }

  completeCart(): void {
    // Navigate to the shipping details page or show a shipping form : TODO
  }
}