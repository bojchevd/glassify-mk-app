import { Component } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { User } from 'src/app/model/user.model';
import { UserServiceService } from 'src/app/user/user-service.service';

@Component({
  selector: 'app-users-view',
  templateUrl: './users-view.component.html',
  styleUrl: './users-view.component.css'
})
export class UsersViewComponent {
  
  displayedColumns: string[] = ['email', 'role', 'name', 'actions'];
  dataSource = new MatTableDataSource<User>();

  constructor(private userService: UserServiceService) {}

  ngOnInit(): void {
    this.userService.getUsers().subscribe((data: User[]) => {
      this.dataSource.data = data;
    });
  }

  viewDetails(email: string): void {
    console.log(email);
    // Add logic to navigate to the user details view or open a modal
  }
}
