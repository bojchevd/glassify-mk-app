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
    price: 0
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

              areRequiredFieldsFilled(): boolean {
                const { productName, songName, artistName, albumName, quantity } = this.cartItem;

                const isProductValid = !!productName.trim();
                const isQuantityValid = quantity > 0;
                const isSongNameValid = !!songName.trim();
                const isArtistNameValid = !!artistName.trim();
                const isAlbumNameValid = !!albumName.trim();

                if (!isProductValid && !!isQuantityValid) {
                  return false;
                }

                switch(this.product.name) {
                  case 'Glass Plaque' :
                    return isSongNameValid && isArtistNameValid;
                  case 'Keychain' :
                    return isSongNameValid && isArtistNameValid;
                  case 'Poster' :
                    return isAlbumNameValid && isArtistNameValid;
                  case 'Bestie' :
                    return true;
                  default : return true;
                }

              }

              calculatePrice(): number {
                let basePrice = this.product.basePrice;
                const quantity = this.cartItem.quantity;
                let calculatedPrice: number;
            
                switch (this.product.name) {
                    case 'Keychain':
                        if (quantity == 2) {
                            calculatedPrice = 399;
                        } else if (quantity == 3) {
                            calculatedPrice = 549;
                        } else if (quantity > 3) {
                            calculatedPrice = (basePrice * quantity) * 0.80;
                        } else {
                            calculatedPrice = basePrice * quantity;
                        }
                        break;
            
                    case 'Poster':
                        if (quantity == 2) {
                            calculatedPrice = 1290;
                        } else if (quantity == 3) {
                            calculatedPrice = 1690;
                        } else if (quantity > 3) {
                            calculatedPrice = (basePrice * quantity) * 0.85;
                        } else {
                            calculatedPrice = basePrice * quantity;
                        }
                        break;
            
                    case 'Glass Plaque':
                        if (quantity == 2) {
                            calculatedPrice = 1490;
                        } else if (quantity == 3) {
                            calculatedPrice = 1999;
                        } else if (quantity > 3) {
                            calculatedPrice = Math.round((basePrice * quantity) * 0.85);
                        } else {
                            calculatedPrice = basePrice * quantity;
                        }
                        break;
            
                    default:
                        calculatedPrice = Math.round(basePrice * quantity);
                }
            
                this.cartItem.price = calculatedPrice;
                return calculatedPrice;
            }
            

              isDiscountApplied(): boolean {
                return this.calculatePrice() < this.product.basePrice * this.cartItem.quantity;
            }

            getDiscountPercentage(): number {
              const basePrice = this.product.basePrice * this.cartItem.quantity;
              const discountedPrice = this.calculatePrice();
              return Math.round((basePrice - discountedPrice));
          }

          saveOrder(): void {
            this.cartService.addItemToCart(this.cartItem).subscribe(
              response => {
                console.log('Cart item added:', response);
                this.openConfirmationDialog();
              },
              error => {
                console.error('Error adding item to cart:', error);
              }
            );
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
                  quantity: 1,
                  price: 0
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
