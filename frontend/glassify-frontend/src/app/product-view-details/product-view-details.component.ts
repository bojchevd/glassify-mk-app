import { Component, OnInit } from '@angular/core';
import { Product } from '../model/product';
import { ActivatedRoute } from '@angular/router';
import { OrderDetails } from '../model/orderDetails.model';
import { ProductService } from '../product/product.service';
import { OrderService } from '../order/order.service';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmationDialogComponent } from '../shared/confirmation-dialog/confirmation-dialog.component';
import { FileUploadComponent } from '../order/file-upload/file-upload.component';
import { FileUploadService } from '../order/file-upload.service';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CartService } from '../cart/cart.service';
import { CartItem } from '../model/cart-item.model';


@Component({
  selector: 'app-product-view-details',
  templateUrl: './product-view-details.component.html',
  styleUrls: ['./product-view-details.component.css']
})
export class ProductViewDetailsComponent implements OnInit {

  product: Product = new Product;

  cartItem: CartItem = {
    id: "",
    productName: "",
    songName: "",
    artistName: "",
    customDetails: "",
    albumName: "",
    frameColor: "",
    photoUrl: "",
    quantity: 1,
  }

  cartId: string = "";

  constructor(private activatedRoute: ActivatedRoute,
              private productService: ProductService,
              private orderService: OrderService,
              private cartService: CartService,
              public dialog: MatDialog) { }

              ngOnInit(): void {
                this.activatedRoute.paramMap.subscribe(params => {
                  const id = params.get('id');
                  if (id) {
                    this.productService.getProductDetailsById(Number(id)).subscribe(data => {
                      this.product = data;
                      console.log('Fetched product:', this.product);
                      this.cartItem.productName = this.product.name;
                      console.log('CartItem productName after fetching:', this.cartItem.productName);
                    }, error => {
                      console.error('Error fetching product details:', error);
                    });
                  }
                });
              }

              saveOrder(): void {
                if (!this.cartId) {
                  this.cartService.createCart().subscribe(cartId => {
                    this.cartId = cartId;
                    this.cartService.addItemToCart(this.cartId, this.cartItem).subscribe(
                      response => {
                        console.log('Cart item added:', response);
                        this.openConfirmationDialog();
                      },
                      error => {
                        console.error('Error adding item to cart:', error);
                      }
                  )}, error => {
                    console.error('Error creating cart:', error);
                  });
                } else {
                  this.cartService.addItemToCart(this.cartId, this.cartItem);
                  this.openConfirmationDialog;
                }
              }

              resetForm(): void {
                this.cartItem = {
                  id: "",
                  productName: this.product.name, 
                  songName: "",
                  artistName: "",
                  customDetails: "",
                  albumName: "",
                  frameColor: "",
                  photoUrl: "",
                  quantity: 1
                };
              }

              getImageUrl(imageUrl: string | null): string {
                return imageUrl ? `assets/${imageUrl}` : 'assets/default-image.jpg';
              }

              openConfirmationDialog(): void {
                this.dialog.open(ConfirmationDialogComponent, {
                  width: '300px',
                  data: { message: 'Производот е додаден во кошница!' }
                });
              }

}
