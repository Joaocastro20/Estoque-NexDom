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
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-produto',
  imports: [
    CardModule,
    PanelModule,
    TableModule, ToastModule, CommonModule, TagModule, SelectModule, ButtonModule, InputTextModule, FormsModule, HttpClientModule

  ],
  providers: [
    MessageService,
    ProdutoService
  ],
  templateUrl: './produto.html',
  styleUrl: './produto.css'
})
export class Produto {

  products!: ProdutoModel[];

  typeProducts!: SelectItem[];

  constructor(private productService: ProdutoService, private messageService: MessageService) { }

  ngOnInit() {
    this.onListProduct();

    this.typeProducts = [
      { label: "ELETRONICO", value: "ELETRONICO" },
      { label: "ELETRODOMESTICO", value: "ELETRODOMESTICO" },
      { label: "MOVEL", value: "MOVEL" },
    ]
  }

  onListProduct(){
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
    this.productService.editar(produtoUpdate,product.codigo).subscribe(() => this.onListProduct())
  }

  onRowEditCancel(product: ProdutoModel, index: number) {
    // this.products[index] = this.clonedProducts[product.id as string];
    // delete this.clonedProducts[product.id as string];
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
