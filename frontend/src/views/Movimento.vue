<template>
  <main>
    <div class="grid">
      <!-- <div class="col-12">
        <div class="flex items-center gap-4 mb-4">
          <label for="tipo" class="font-semibold w-24">Tipo</label>
          <Dropdown :options="entradaSaida" id="tipo" v-model="form.tipoProduto" option-label="name" option-value="code"
            placeholder="Selecione" class="flex-auto" />
        </div>
        <div class="flex items-center gap-4 mb-4">
          <label for="quantidadeMovimentada" class="font-semibold w-24">Quantidade</label>
          <InputText v-model="form.quantidadeMovimentada" id="quantidadeMovimentada" class="flex-auto"
            autocomplete="off" />
        </div>
        <div class="flex items-center gap-4 mb-4">
          <label for="valorVenda" class="font-semibold w-24">Valor</label>
          <InputText v-model="form.valorVenda" id="valorVenda" class="flex-auto"
            autocomplete="off" />
            <Button label="Filtrar" @click="fetchMovimento">Salvar Movimento</Button>
        </div>
      </div> -->
      <div class="col-6">Direita</div>
    </div>
    <Dropdown :options="options" id="tipo" v-model="selectedTipo" option-label="name" option-value="code"
      placeholder="Selecione" class="flex-auto" />
    <Button label="Filtrar" @click="fetchProdutoTipo(selectedTipo)">Filtrar</Button>
  </main>
</template>

<script setup>
import { ref } from "vue";
import Dropdown from 'primevue/dropdown'
import axios from 'axios'

const form = ref({
  tipoMovimentacao: '',
  quantidadeMovimentada: 0,
  valorVenda: 0,
})

const selectedTipo = ref();
const options = [
  { name: 'ELETRÔNICO', code: 'ELETRÔNICO' },
  { name: 'ELETRODOMÉSTICO', code: 'ELETRODOMÉSTICO' },
  { name: 'MÓVEL', code: 'MÓVEL' }
]

// const entradaSaida = [
//   { name: 'ENTRADA', code: 'ENTRADA' },
//   { name: 'SAÍDA', code: 'SAÍDA' }
// ]

async function fetchProdutoTipo(tipo) {
  try {
    const response = await axios.get(`http://localhost:8080/api/produtos/tipo/${tipo}`)
    customers.value = response.data.content
    console.log("🚀 ~ fetchCustomers ~ response.data:", response.data)

  } catch (error) {
    console.error('Erro ao buscar clientes:', error)
  }
}

// async function fetchMovimento() {
//   try {
//     const response = await axios.get(`http://localhost:8080/api/movimentoestoque/${}`)
//     customers.value = response.data.content
//     console.log("🚀 ~ fetchCustomers ~ response.data:", response.data)

//   } catch (error) {
//     console.error('Erro ao buscar clientes:', error)
//   }
// }
</script>
