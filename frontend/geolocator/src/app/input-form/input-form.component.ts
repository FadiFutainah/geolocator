import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-input-form',
  templateUrl: './input-form.component.html',
  styleUrls: ['./input-form.component.css'],
})
export class InputFormComponent {
  input1Value: string = '';
  input2Value: string = '';
  isLoading = false;
  response: any;
  error: boolean = false;
  errorMessage: string = '';
  showResponse: boolean = false;

  constructor(private http: HttpClient) {}

  sendData(): void {
    // You can send the data to the server here
    console.log('Sending data to server:', this.input1Value, this.input2Value);
    this.isLoading = true;

    const data = {
      firstString: this.input1Value,
      secondString: this.input2Value,
    };

    this.http.post('http://127.0.0.1:8080/api/geocode',data).subscribe(
      // Success handler
      (response) => {
        this.isLoading = false;
        this.response = response;
        this.showResponse = true;
      },
      // Error handler
      (error) => {
        this.isLoading = false;
        this.error = true;
        this.errorMessage = error.message;
        this.showResponse = true;
      }
    );
  }
}
