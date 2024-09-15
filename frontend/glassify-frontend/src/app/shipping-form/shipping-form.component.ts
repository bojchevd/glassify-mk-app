import { Component } from '@angular/core';

@Component({
  selector: 'app-shipping-form',
  templateUrl: './shipping-form.component.html',
  styleUrls: ['./shipping-form.component.css']
})
export class ShippingFormComponent {
  shippingDetails = {
    fullName: '',
    phoneNumber: '',
    address: '',
    city: ''
  };

  completeOrder(): void {
    console.log('Shipping details:', this.shippingDetails);
  }
}
