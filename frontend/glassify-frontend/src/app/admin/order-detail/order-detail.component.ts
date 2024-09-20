import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-order-detail',
  templateUrl: './order-detail.component.html',
  styleUrls: ['./order-detail.component.css']
})
export class OrderDetailComponent implements OnInit {
  order: any;

  constructor(private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.order = history.state.order;
    console.log(this.order)
  }

  getPhotoUrl(photoPath: string): string {
    console.log(`http://localhost:8080/${photoPath}`)
    return `http://localhost:8080/${photoPath}`;
  }
  
}
