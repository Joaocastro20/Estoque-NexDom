import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Home } from "./components/home/home";
import { MenubarModule } from 'primeng/menubar';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, MenubarModule],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected title = 'frontend-angular';
  itens = ["teste"]
  items: MenuItem[] = [];

ngOnInit() {
  this.items = [
    {
      label: 'Home',
      icon: 'pi pi-home',
      routerLink: '/home'
    },
    {
      label: 'Produtos',
      icon: 'pi pi-box',
      routerLink: '/produto'
    },
    // {
    //   label: 'Movimentar Produto',
    //   icon: 'pi pi-exchange',
    //   routerLink: '/movimentar'
    // }
  ];
}
}
