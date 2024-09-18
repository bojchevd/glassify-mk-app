import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable,  of, BehaviorSubject} from 'rxjs';
import { switchMap } from 'rxjs/operators';
import { tap } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { CartItem } from '../model/cart-item.model';

@Injectable({
  providedIn: 'root'
})
export class CartService {

  private apiUrl = environment.springUrl;
  private cartIdKey = 'cartId';

  private itemCountSource = new BehaviorSubject<number>(0);
  itemCount$ = this.itemCountSource.asObservable();

  constructor(private http: HttpClient) { 
    const initialCount = Number(localStorage.getItem('itemCount')) || 0;
    this.itemCountSource.next(initialCount);
  }

  getCartId(): string | null {
    return sessionStorage.getItem(this.cartIdKey);
  }

  private updateItemCount(itemCount: number): void {
    this.itemCountSource.next(itemCount);
    localStorage.setItem('itemCount', itemCount.toString());
  }

  createCart(): Observable<string> {
    return this.http.post<string>(`${this.apiUrl}/cart/create`, {}).pipe(
      switchMap(cartId => {
        sessionStorage.setItem(this.cartIdKey, cartId);
        return of(cartId);
      })
    );
  }

  addItemToCart(item: CartItem): Observable<CartItem> {
    let cartId = this.getCartId();
    const currentCount = this.itemCountSource.getValue();
    this.updateItemCount(currentCount + 1);
    if (!cartId) {
      return this.createCart().pipe(
        switchMap(newCartId => {
          return this.http.post<CartItem>(`${this.apiUrl}/cart/${newCartId}/add`, item);
        })
      );
    } else {
      return this.http.post<CartItem>(`${this.apiUrl}/cart/${cartId}/add`, item);
    }
    
  }

  removeItemFromCart(itemId: string): Observable<void> {
    let cartId = this.getCartId();
    const currentCount = this.itemCountSource.getValue();
    this.updateItemCount(currentCount - 1);
    
    if (cartId) {
      return this.http.delete<void>(`${this.apiUrl}/cart/${cartId}/remove/${itemId}`);
    } else {
      return of(undefined);
    }
  }

  clearCart(): void {
    window.sessionStorage.clear();
    console.log(this.getCartId)
  }

  getCartItems(): Observable<CartItem[]> {
    let cartId = this.getCartId();
    if (cartId) {
      return this.http.get<CartItem[]>(`${this.apiUrl}/cart/${cartId}/getAll`);
    } else throw new Error("no cart created");
  }

  saveShippingDetails(shippingDetails: any) {
    let cartId = this.getCartId();
    return this.http.post<any>(`${this.apiUrl}/order/submit/${cartId}`, shippingDetails)
  }

  }
