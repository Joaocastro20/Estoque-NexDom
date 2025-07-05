import { Component } from '@angular/core';
import { ButtonModule } from 'primeng/button';
import { PanelModule } from 'primeng/panel';
import { SplitterModule } from 'primeng/splitter';
import { RelatoriosService } from '../../../domain/relatorios/relatorios.service';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-relatorio-total',
  imports: [PanelModule, ButtonModule, SplitterModule, HttpClientModule],
  templateUrl: './relatorio-total.html',
  styleUrl: './relatorio-total.css',
  providers: [RelatoriosService]
})
export class RelatorioTotal {

  pdfUrl!: SafeResourceUrl;

  constructor(private relatoriosService: RelatoriosService, private sanitizer: DomSanitizer){}

  onGerarRelatorioTotal(){
    this.relatoriosService.gerarRelatorioTotal().subscribe(
      blob => {
        const url = window.URL.createObjectURL(blob);
        window.open(url)
        this.pdfUrl = this.sanitizer.bypassSecurityTrustResourceUrl(url);
      }
    )
  }

}
