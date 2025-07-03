import { Component } from '@angular/core';
import { RouterLink, RouterOutlet } from '@angular/router';
import { Home } from "./components/home/home";
import { MenubarModule } from 'primeng/menubar';
import { MenuItem } from 'primeng/api';
import { DrawerModule } from 'primeng/drawer';
import { ButtonModule } from 'primeng/button';
import { AvatarModule } from 'primeng/avatar';
import { RippleModule } from 'primeng/ripple';
import { ViewChild } from '@angular/core';
import { Ripple } from 'primeng/ripple';
import { StyleClass } from 'primeng/styleclass';
import { Drawer } from 'primeng/drawer';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, MenubarModule, DrawerModule, ButtonModule, AvatarModule, RippleModule, Ripple, StyleClass, Drawer, RouterLink],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected title = 'frontend-angular';
  itens = ["teste"]
  items: MenuItem[] = [];

  @ViewChild('drawerRef') drawerRef!: Drawer;

  closeCallback(e: Event): void {
    this.drawerRef.close(e);
  }

  visible: boolean = false;

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
