import { Component } from '@angular/core';
import { Router } from "@angular/router";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {

  view: boolean = true;

  constructor(private router: Router){

  }

  homepage() {
    this.router.navigate(['home/pvform']);
    this.view = false;
  }
  viewpage(){
    this.router.navigate(['home/pvview']);
    this.view = true;
  }

}
