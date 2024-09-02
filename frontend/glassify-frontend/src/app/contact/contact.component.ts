import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ContactService } from './contact.service';

@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.css']
})
export class ContactComponent {
  contactForm: FormGroup;

  constructor(private fb: FormBuilder, private contactService: ContactService) {
    this.contactForm = this.fb.group({
      name: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      message: ['', [Validators.required]]
    });
  }

  onSubmit() {
    if (this.contactForm.valid) {
      const { name, email, message } = this.contactForm.value;
      this.contactService.sendEmail(name, email, message).subscribe(
        response => {
          console.log(response);
          alert('Message sent successfully!');
        },
        error => {
          alert('Failed to send message.');
        }
      );
    } else {
      alert('Please fill in all required fields.');
    }
  }

//   isSubmitted(): boolean {
//     return this.onSubmit()
// }
  
}
