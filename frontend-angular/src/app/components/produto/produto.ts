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
import {gerarNumerosAleatorios} from '../../utils/utils'


@Component({
  selector: 'app-produto',
  imports: [
    CardModule,
    PanelModule,
    TableModule, ToastModule, CommonModule, TagModule, SelectModule, ButtonModule, InputTextModule, FormsModule, HttpClientModule, ConfirmDialog, ToastModule, ButtonModule, Dialog,ReactiveFormsModule

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

    showDialog() {
        this.visible = true;
    }

    produtoForm!: FormGroup;

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
    this.productService.listarTodos().subscribe(data => {
      console.log("🚀 ~ Produto ~ this.productService.listarTodos ~ data:", data)
      this.products = data.content;
    })
  }

  onRowEditInit(product: ProdutoModel) {
    console.log("🚀 ~ Produto ~ onRowEditInit ~ product:", product)
    // this.clonedProducts[product.id as string] = { ...product };
  }

  onRowEditSave(product: ProdutoModel) {
    const produtoUpdate = product;
    this.productService.editar(produtoUpdate, product.codigo).subscribe(() => this.onListProduct())
  }

  onRowEditCancel(product: ProdutoModel, index: number) {
    // this.products[index] = this.clonedProducts[product.id as string];
    // delete this.clonedProducts[product.id as string];
  }

  onRowSave(){
      console.log("🚀 ~ Produto ~ onRowSave ~ produtoForm:", this.produtoForm.value)
      const produtoSalve = this.produtoForm.value;
      produtoSalve.codigo = gerarNumerosAleatorios(10);
      this.productService.salvar(this.produtoForm.value).subscribe(()=>{
        this.onListProduct();
        this.visible = false;
        this.produtoForm.reset()
      })
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
