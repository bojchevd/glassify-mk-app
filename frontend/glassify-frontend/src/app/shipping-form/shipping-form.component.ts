import { Component } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';

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

  ngOnInit() {
    this.filteredCities = this.cityCtrl.valueChanges.pipe(
      startWith(''),
      map(value => this._filter(value))
    );
  }

  private _filter(value: string): string[] {
    const filterValue = value.toLowerCase();
    return this.cities.filter(city => city.toLowerCase().includes(filterValue));
  }

  clearSearch() {
    this.cityCtrl.setValue('');
  }
}
