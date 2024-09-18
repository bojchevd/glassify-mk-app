import { Component, OnInit } from '@angular/core';
import { CartService } from '../cart.service';
import { CartItem } from 'src/app/model/cart-item.model';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';
import { Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmationDialogComponent } from 'src/app/shared/confirmation-dialog/confirmation-dialog.component';


@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {
  cartItems: CartItem[] = [];
  cartId: string | null = null;
  itemCount: number = 0;

  shippingDetails = {
    fullName: '',
    phoneNumber: '',
    address: '',
    city: '',
    email: ''
  };

  shippingDetailsForm = new FormGroup({
    fullName: new FormControl(this.shippingDetails.fullName, Validators.required),
    phoneNumber: new FormControl(this.shippingDetails.phoneNumber, [
      Validators.required,
      Validators.pattern(/^[\d\s-]{9,}$/)
    ]),
    address: new FormControl(this.shippingDetails.address, Validators.required),
    city: new FormControl(this.shippingDetails.city, Validators.required),
    email: new FormControl('', [Validators.required, Validators.email])
  });

  validateShippingDetails(): boolean {
    return this.shippingDetailsForm.valid;
  }

  cities: string[] = [
    'Берово', 'Битола', 'Богданци', 'Валандово', 'Вевчани', 'Велес', 'Виница', 'Гевгелија', 
    'Гостивар', 'Градско', 'Дебар', 'Делчево', 'Демир Капија', 'Демир Хисар', 'Дојран', 
    'Кавадарци', 'Кичево', 'Кочани', 'Кратово', 'Крива Паланка', 'Крушево', 'Куманово', 
    'Лозово', 'Маврово', 'Македонска Каменица', 'Македонски Брод', 'Неготино', 'Охрид', 
    'Пехчево', 'Прилеп', 'Пробиштип', 'Радовиш', 'Ресен', 'Росоман', 'Свети Николе', 'Скопје', 
    'Скопје - Аеродром', 'Скопје - Арачиново', 'Скопје - Бутел', 'Скопје - Гази Баба', 
    'Скопје - Ѓорче Петров', 'Скопје - Зелениково', 'Скопје - Илинден', 'Скопје - Карпош', 
    'Скопје - Кисела Вода', 'Скопје - Петровец', 'Скопје - Сарај', 'Скопје - Сопиште', 
    'Скопје - Студеничани', 'Скопје - Центар', 'Скопје - Чаир', 'Скопје - Чучер-Сандево', 
    'Скопје - Шуто Оризари', 'Струга', 'Струмица', 'Тетово', 'Штип'
];

cityCtrl = new FormControl();
filteredCities!: Observable<string[]>;

  constructor(private cartService: CartService,
    private router: Router,
    public dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.cartId = this.cartService.getCartId();
    if (this.cartId) {
      this.loadCartItems();
    }

    this.filteredCities = this.cityCtrl.valueChanges.pipe(
      startWith(''),
      map(value => this._filter(value))
    );

    this.cartService.itemCount$.subscribe(count => {
      this.itemCount = count;
    });
    this.cartService.itemCount$.subscribe(count => this.itemCount = count);
  }

  private _filter(value: string): string[] {
    const filterValue = value.toLowerCase();
    return this.cities.filter(city => city.toLowerCase().includes(filterValue));
  }

  clearSearch() {
    this.cityCtrl.setValue('');
  }

  loadCartItems(): void {
      this.cartService.getCartItems().subscribe(items => {
        console.log(items);
        this.cartItems = items || [];
      });
    
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

  openConfirmationDialog(): void { // todo : confirmation dialog buttons
    this.dialog.open(ConfirmationDialogComponent, {
      width: '300px',
      data: { message: 'Нарачката е потврдена!' }
    });
  }

  completeCart(): void {
    this.shippingDetails = {
      fullName: this.shippingDetailsForm.value.fullName ?? '',     
      phoneNumber: this.shippingDetailsForm.value.phoneNumber ?? '',
      address: this.shippingDetailsForm.value.address ?? '',
      city: this.shippingDetailsForm.value.city ?? '',
      email: this.shippingDetailsForm.value.email ?? ''
    };

    this.cartService.saveShippingDetails(this.shippingDetails).subscribe(response => {
      console.log("Order submitted successfully", response);
      this.openConfirmationDialog();
      this.cartService.clearCart();
      this.router.navigate(['/products']);
    }, error => {
      console.error("Error submitting order", error);
    });
  }
}