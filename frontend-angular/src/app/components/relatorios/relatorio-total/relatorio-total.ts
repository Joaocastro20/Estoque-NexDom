import { Component } from '@angular/core';
import { ButtonModule } from 'primeng/button';
import { PanelModule } from 'primeng/panel';
import { SplitterModule } from 'primeng/splitter';
import { RelatoriosService } from '../../../domain/relatorios/relatorios.service';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { MessageService } from 'primeng/api';
import { Toast } from 'primeng/toast';

@Component({
  selector: 'app-relatorio-total',
  imports: [PanelModule, ButtonModule, SplitterModule, HttpClientModule, Toast],
  templateUrl: './relatorio-total.html',
  styleUrl: './relatorio-total.css',
  providers: [RelatoriosService, MessageService]
})
export class RelatorioTotal {

  pdfUrl!: SafeResourceUrl;

  constructor(private relatoriosService: RelatoriosService, private sanitizer: DomSanitizer,private messageService: MessageService) { }

  onGerarRelatorioTotal() {
    this.relatoriosService.gerarRelatorioTotal().subscribe(
      {
        next: (blob: Blob) => {
          const url = window.URL.createObjectURL(blob);
          window.open(url)
          this.pdfUrl = this.sanitizer.bypassSecurityTrustResourceUrl(url);
        },
        error: (err) => {
          this.messageService.add({ severity: 'info', summary: 'Info', detail: `Não há produtos cadastrados!` });
        }
      }

    )
  }

}
