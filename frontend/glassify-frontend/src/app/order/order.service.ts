import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Order } from '../model/order.model';
import { OrderDetails } from '../model/orderDetails.model';

@Injectable({
  providedIn: 'root'
})
export class OrderService {
  private apiUrl = environment.mockApi; // todo : change

  constructor(private http: HttpClient) { }

  saveOrderDetails(orderDetails: OrderDetails): Observable<any> {
    return this.http.post(`${this.apiUrl}/orders`, orderDetails);
  }

  submitOrder(cartId: number): Observable<Order> {
    return this.http.post<Order>(`${this.apiUrl}/order/submit/${cartId}`, {});
  }
}
