import { Component } from '@angular/core';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { PanelModule } from 'primeng/panel';
import { AnimateOnScroll } from 'primeng/animateonscroll';
import { AvatarModule } from 'primeng/avatar';
import { ToastModule } from 'primeng/toast';
import { RippleModule } from 'primeng/ripple';
import { MessageService } from 'primeng/api';
import { MensagemService } from '../../domain/mensagem/mensagem.service';
import { HttpClientModule } from '@angular/common/http';
import { MensagemModel } from '../../domain/mensagem/mensagem.model';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-home',
  imports: [ButtonModule, CardModule, PanelModule, AnimateOnScroll, AvatarModule, ToastModule, RippleModule, HttpClientModule, CommonModule],
  templateUrl: './home.html',
  styleUrl: './home.css',
  styles: [
        `
            :host {
                @keyframes slidedown-icon {
                    0% {
                        transform: translateY(0);
                    }

                    50% {
                        transform: translateY(20px);
                    }

                    100% {
                        transform: translateY(0);
                    }
                }

                .slidedown-icon {
                    animation: slidedown-icon;
                    animation-duration: 3s;
                    animation-iteration-count: infinite;
                }

                .box {
                    background-image: radial-gradient(var(--primary-300), var(--primary-600));
                    border-radius: 50% !important;
                    color: var(--primary-color-text);
                }
            }
        `
    ],
    providers: [MessageService, MensagemService]
})
export class Home {
  listMensagens!: MensagemModel[];

    constructor(private messageService: MensagemService) {}

    ngOnInit(){
      this.onListarMensagens()
      
    }

    onListarMensagens(){
      this.messageService.listarTodos().subscribe(data => {
        this.listMensagens = data
      })
    }

}
  
