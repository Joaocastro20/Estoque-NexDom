import { Component } from '@angular/core';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { PanelModule } from 'primeng/panel';

@Component({
  selector: 'app-home',
  imports: [ButtonModule, CardModule, PanelModule],
  templateUrl: './home.html',
  styleUrl: './home.css'
})
export class Home {

}
