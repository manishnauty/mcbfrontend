import { Component } from '@angular/core';
import { Router } from "@angular/router";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {

  constructor(private router: Router){

  }

  homepage() {
    this.router.navigate(['pvform']);
  }
  viewpage(){
    this.router.navigate(['pvview']);
  }

}
