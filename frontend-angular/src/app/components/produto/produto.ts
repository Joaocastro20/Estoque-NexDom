import { Component } from '@angular/core';
import { CardModule } from 'primeng/card';
import { PanelModule } from 'primeng/panel';
import { MessageService, SelectItem } from 'primeng/api';
import { TableModule } from 'primeng/table';
import { ToastModule } from 'primeng/toast';
import { CommonModule } from '@angular/common';
import { TagModule } from 'primeng/tag';
import { SelectModule } from 'primeng/select';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { ProdutoService } from '../../domain/produto/produto.service';
import { ProdutoModel } from '../../domain/produto/produto.model';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { ConfirmationService } from 'primeng/api';
import { ConfirmDialog } from 'primeng/confirmdialog';
import { Dialog } from 'primeng/dialog';
import { gerarNumerosAleatorios } from '../../utils/utils'
import { PaginatorModule, PaginatorState } from 'primeng/paginator';


@Component({
  selector: 'app-produto',
  imports: [
    CardModule,
    PanelModule,
    TableModule, ToastModule, CommonModule, TagModule, SelectModule, ButtonModule,
    InputTextModule, FormsModule, HttpClientModule, ConfirmDialog, ToastModule, ButtonModule,
    Dialog, ReactiveFormsModule, PaginatorModule

  ],
  providers: [
    MessageService,
    ProdutoService, ConfirmationService, MessageService
  ],
  templateUrl: './produto.html',
  styleUrl: './produto.css'
})
export class Produto {

  products!: ProdutoModel[];

  typeProducts!: SelectItem[];

  visible: boolean = false;

  produtoForm!: FormGroup;

  first: number = 0;

  rows: number = 5;

  totalRecords: number = 0
  totalPages: number = 0

  page: number = 0;
  size: number = 5;
  sort: string = "id,asc";



  constructor(private productService: ProdutoService, private messageService: MessageService, private confirmationService: ConfirmationService, private fb: FormBuilder) { }

  ngOnInit() {
    this.produtoForm = this.fb.group({
      descricao: [null, [Validators.required]],
      tipoProduto: [null, [Validators.required]],
      valorFornecedor: [null, [Validators.required, Validators.pattern(/^\d+(\.\d{1,2})?$/)]],
      quantidadeEstoque: [null, [Validators.required, Validators.pattern(/^\d+(\.\d{1,2})?$/)]]
    });

    this.onListProduct();

    this.typeProducts = [
      { label: "ELETRONICO", value: "ELETRONICO" },
      { label: "ELETRODOMESTICO", value: "ELETRODOMESTICO" },
      { label: "MOVEL", value: "MOVEL" },
    ]
  }

  onListProduct() {
    this.productService.listarTodos(this.page, this.size, this.sort).subscribe(data => {
      this.products = data.content;
      this.totalPages = data.totalPages;
      this.totalRecords = data.totalElements;
    })
  }

  onPageChange(event: PaginatorState) {
    this.first = event.first ?? 0;
    this.page = event.page ?? 0;
    this.rows = event.rows ?? 5;
    this.size = event.rows ?? 5;
    this.onListProduct();
  }

  onRowEditInit(product: ProdutoModel) {
  }

  onRowEditSave(product: ProdutoModel) {
    const produtoUpdate = product;
    this.productService.editar(produtoUpdate, product.codigo).subscribe(() => this.onListProduct())
  }

  onRowEditCancel(product: ProdutoModel, index: number) {
  }

  onRowSave() {
    const produtoSalve = this.produtoForm.value;
    produtoSalve.codigo = gerarNumerosAleatorios(10);
    this.productService.salvar(this.produtoForm.value).subscribe(() => {
      this.onListProduct();
      this.visible = false;
      this.produtoForm.reset()
    })
  }

  showDialog() {
    this.visible = true;
  }

  onRowTrash(event: Event, product: ProdutoModel) {
    this.confirmationService.confirm({
      target: event.target as EventTarget,
      message: `Você deseja deletar o produto: ${product.descricao}`,
      header: 'Excluir',
      icon: 'pi pi-info-circle',
      rejectLabel: 'Cancel',
      rejectButtonProps: {
        label: 'Cancel',
        severity: 'secondary',
        outlined: true,
      },
      acceptButtonProps: {
        label: 'Delete',
        severity: 'danger',
      },

      accept: () => {
        this.productService.excluir(product.codigo).subscribe(() => {
          this.messageService.add({ severity: 'info', summary: 'Confirmed', detail: `Produto ${product.codigo} foi deleletado com sucesso!` });
          this.onListProduct();
        })

      }
    });
  }

  getSeverity(status: string) {
    switch (status) {
      case 'MOVEL':
        return 'success';
      case 'ELETRONICO':
        return 'warn';
      case 'ELETRODOMESTICO':
        return 'danger';
      default:
        return 'warn'
    }
  }

}
