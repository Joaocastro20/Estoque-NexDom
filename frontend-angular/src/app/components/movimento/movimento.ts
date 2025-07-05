import { Component } from '@angular/core';
import { CardModule } from 'primeng/card';
import { InputTextModule } from 'primeng/inputtext';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { FloatLabel } from 'primeng/floatlabel';
import { SelectModule } from 'primeng/select';
import { ButtonModule } from 'primeng/button';
import { SelectItem } from 'primeng/api';
import { MessageModule } from 'primeng/message';
import { PanelModule } from 'primeng/panel';
import { MovimentoService } from '../../domain/movimento/movimento.service';
import { HttpClientModule } from '@angular/common/http';
import { MessageService } from 'primeng/api';
import { Toast } from 'primeng/toast';
import { SplitterModule } from 'primeng/splitter';
import { MovimentoModel } from '../../domain/movimento/movimento.model';
import { TableModule } from 'primeng/table';
import { CommonModule } from '@angular/common';
import { ToggleButtonModule } from 'primeng/togglebutton';
import { ProdutoService } from '../../domain/produto/produto.service';
import { MovimentoSaveModel } from '../../domain/movimento/movimento-save.model';
import { gerarNumerosAleatorios } from '../../utils/utils';
import { TipoMovimento } from '../../domain/movimento/tipo-movimento.enum';

@Component({
  selector: 'app-movimento',
  imports: [CardModule, InputTextModule, FormsModule,
    FloatLabel, ReactiveFormsModule, SelectModule, ButtonModule,
    MessageModule, PanelModule, HttpClientModule, Toast, SplitterModule,
    TableModule, CommonModule, ToggleButtonModule],
  templateUrl: './movimento.html',
  styleUrl: './movimento.css',
  providers: [MovimentoService, MessageService, ProdutoService]
})
export class Movimento {
  movimentoForm!: FormGroup;

  typeProducts!: SelectItem[];

  listMovimento!: MovimentoModel[];

  page: number = 0;
  size: number = 5;
  sort: string = "id,desc";

  checked: boolean = true;

  constructor(private fb: FormBuilder, private movimentoService: MovimentoService, private messageService: MessageService, private produtoService: ProdutoService) { }

  ngOnInit() {
    this.movimentoForm = this.fb.group({
      codigo: [null, [Validators.required, Validators.pattern(/^\d+(\.\d{1,2})?$/)]],
      tipoMovimentacao: [null, [Validators.required]],
      quantidadeMovimentada: [null, [Validators.required, Validators.pattern(/^\d+(\.\d{1,2})?$/)]],
      valorVenda: [null, [Validators.required, Validators.pattern(/^\d+(\.\d{1,2})?$/)]]
    });

    this.typeProducts = [
      { label: "ENTRADA", value: "ENTRADA" },
      { label: "SAIDA", value: "SAIDA" }
    ]

    this.onListMovimento();
  }

  onListMovimento() {
    this.movimentoService.listarTodos(this.page, this.size, this.sort).subscribe(data => {
      console.log("🚀 ~ Movimento ~ this.movimentoService.listarTodos ~ data:", data)
      this.listMovimento = data.content;
    })
  }

  onSave() {
    this.movimentoService.salvarMovimento(this.movimentoForm.value, this.movimentoForm.get("codigo")?.value).subscribe(
      {
        next: response => {
          this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Movimento salvo com sucesso' });
          this.onListMovimento();
        },
        error: err => {
          this.messageService.add({ severity: 'info', summary: 'Info', detail: `${err.error.message}` });
        },

        complete: () => {
        }
      }
    )
  }

  isInvalid(controlName: string) {
    const control = this.movimentoForm.get(controlName);
    return control?.invalid && (control.touched || this.movimentoForm);
  }

  async onRandomSell() {
    let codigos: string[] = [];
    
    this.produtoService.listarTodos(0, 5, 'id,asc').subscribe(data => {
      codigos = data.content.map(p => p.codigo);
    })
  
    while (!this.checked) {
      const codigoRandom = codigos[Math.floor(Math.random() * codigos.length)];
      const tipoMovimentacaoListaFake = Object.values(TipoMovimento);
      const tipoMovimentacaoFake = tipoMovimentacaoListaFake[Math.floor(Math.random() * tipoMovimentacaoListaFake.length)];
      
      let movimentoFake!: MovimentoSaveModel;
      movimentoFake = {
        quantidadeMovimentada: parseInt(gerarNumerosAleatorios(1)),
        valorVenda: parseInt(gerarNumerosAleatorios(3)),
        dataVenda: new Date(),
        tipoMovimentacao: tipoMovimentacaoFake
      }
      
      this.movimentoService.salvarMovimento(movimentoFake, codigoRandom).subscribe({
        next: response => {
          this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Movimento Fake salvo com sucesso' });
          this.onListMovimento();
        },
        error: err => {
          this.messageService.add({ severity: 'info', summary: 'Info', detail: `${err.error.message}` });
        },

        complete: () => {
        }
      })
      await this.delay(1000);
    }
    console.log('Parado.');
  }

  delay(ms: number) {
    return new Promise(resolve => setTimeout(resolve, ms));
  }


}
