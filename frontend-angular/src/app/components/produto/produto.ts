import { Component } from '@angular/core';
import { CardModule } from 'primeng/card';
import { PanelModule } from 'primeng/panel';

@Component({
  selector: 'app-produto',
  imports: [CardModule, PanelModule ],
  templateUrl: './produto.html',
  styleUrl: './produto.css'
})
export class Produto {

}
