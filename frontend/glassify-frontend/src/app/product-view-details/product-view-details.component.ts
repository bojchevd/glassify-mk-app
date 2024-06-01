import { Component, OnInit } from '@angular/core';
import { Product } from '../model/product';
import { ActivatedRoute } from '@angular/router';
import { OrderDetails } from '../model/orderDetails.model';

@Component({
  selector: 'app-product-view-details',
  templateUrl: './product-view-details.component.html',
  styleUrls: ['./product-view-details.component.css']
})
export class ProductViewDetailsComponent implements OnInit {

  product: Product = new Product;

  orderDetails: OrderDetails = {
    productName: "",
    songName: "",
    artistName: "",
    songUrl: "",
    customDetails: "",
    albumName: "",
    frameColor: "",
    photoUrl: "",
    quantity: 1,
    fullName: "",
    deliveryAddress: "",
    city: "",
    phoneNumber: ""
  }


  constructor(private activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {
    this.product = this.activatedRoute.snapshot.data['product'];
  }

}
