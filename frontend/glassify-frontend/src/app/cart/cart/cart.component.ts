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
      this.cartService.getCartItems().subscribe(items => {
        console.log(items);
        this.cartItems = items || [];
      });
    
  }

  calculateProductPrice(item: CartItem): number {
    return 100; // todo
  }

  calculateCartPrice(): number {
    return this.cartItems.reduce((total, item) => total + (item.price), 0);
  }

  calculateShippingPrice(): number {
    const cartPrice = this.calculateCartPrice();
    return cartPrice >= 2000 ? 0 : 150; 
  }

  removeItem(itemId: string): void {
    if (this.cartId) {
      console.log(itemId)
      this.cartService.removeItemFromCart(itemId).subscribe(() => {
        this.loadCartItems();
      });
    }
  }

  completeCart(): void {
    
  }
}