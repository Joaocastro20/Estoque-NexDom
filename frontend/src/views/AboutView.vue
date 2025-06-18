<template>
  <div class="card">
    <DataTable
      :value="customers"
      paginator
      :rows="5"
      :rowsPerPageOptions="[5, 10, 20, 50]"
      tableStyle="min-width: 50rem"
    >
      <Column field="descricao" header="Descrição" style="width: 25%" />
      <Column field="codigo" header="Codigo" style="width: 25%" />
      <Column field="tipoProduto" header="Tipo" style="width: 25%" />
      <Column field="valorFornecedor" header="Valor" style="width: 25%" />
      <Column header="Ações" style="width: 20%">
        <template #body="slotProps">
          <button @click="editCustomer(slotProps.data)" class="p-button p-button-sm p-button-text">
            ✏️
          </button>
          <button @click="deleteCustomer(slotProps.data)" class="p-button p-button-sm p-button-danger p-button-text">
            🗑️
          </button>
        </template>
      </Column>
    </DataTable>
  </div>

  <Button label="Show" @click="visible = true">Novo</Button>

<Dialog v-model:visible="visible" header="Salvar Novo Produto" :style="{ width: '25rem' }">
  <div class="flex items-center gap-4 mb-4">
    <label for="descricao" class="font-semibold w-24">Descrição</label>
    <InputText id="descricao" class="flex-auto" v-model="form.descricao" autocomplete="off" />
  </div>
  
  <div class="flex items-center gap-4 mb-4">
    <label for="codigo" class="font-semibold w-24">Código</label>
    <InputText id="codigo" class="flex-auto" v-model="form.codigo" autocomplete="off" />
  </div>
  
  <div class="flex items-center gap-4 mb-4">
    <label for="tipoProduto" class="font-semibold w-24">Tipo</label>
    <Dropdown
      id="tipoProduto"
      v-model="form.tipoProduto"
      :options="options"
      option-label="name"
      option-value="code"
      placeholder="Selecione"
      class="flex-auto"
    />
  </div>
  
  <div class="flex items-center gap-4 mb-4">
    <label for="valorFornecedor" class="font-semibold w-24">Valor</label>
    <InputText id="valorFornecedor" class="flex-auto" v-model="form.valorFornecedor" autocomplete="off" />
  </div>
  <div class="flex items-center gap-4 mb-4">
    <label for="quantidadeEstoque" class="font-semibold w-24">Quantidade Estoque</label>
    <InputText id="quantidadeEstoque" class="flex-auto" v-model="form.quantidadeEstoque" autocomplete="off" />
  </div>

  <div class="flex justify-end gap-2">
    <Button type="button" label="Cancelar" severity="secondary" @click="visible = false" />
    <Button type="button" label="Salvar" @click="salvarCustomer()" />
  </div>
</Dialog>
   
</template>

<script setup>
import { ref, onMounted } from 'vue'
import  DataTable  from 'primevue/datatable'
import Column from 'primevue/column'
import axios from 'axios'
import InputText from 'primevue/inputtext'
import Dropdown from 'primevue/dropdown'

const customers = ref([])

const selectedCity = ref(null)

const options = [
  { name: 'ELETRÔNICO', code: 'ELETRÔNICO' },
  { name: 'ELETRODOMÉSTICO', code: 'ELETRODOMÉSTICO' },
  { name: 'MÓVEL', code: 'MÓVEL' }
]



const visible = ref(false)

const form = ref({
  descricao: '',
  codigo: 0,
  valorFornecedor: 0,
  quantidadeEstoque: 0
})

onMounted(() => {
  fetchCustomers();
})

async function fetchCustomers() {
  try {
    const response = await axios.get('http://localhost:8080/api/produtos?page=0&size=5&sort=id,asc')
    customers.value = response.data.content
    console.log("🚀 ~ fetchCustomers ~ response.data:", response.data)
    
  } catch (error) {
    console.error('Erro ao buscar clientes:', error)
  }
}

async function fetchSalvarCustomer() {
  try {
    const response = await axios.post('http://localhost:8080/api/produtos', form.value)
    console.log('Produto salvo:', response.data)
    visible.value = false
    await fetchCustomers() // recarrega tabela
    form.value = {
      descricao: '',
      codigo: '',
      tipoProduto: '',
      valorFornecedor: ''
    }
  } catch (error) {
    console.error('Erro ao salvar produto:', error)
  }
}

function editCustomer(customer) {
  console.log('Editar:', customer)
}

function deleteCustomer(customer) {
  console.log('Excluir:', customer)
}

function salvarCustomer(customer) {
  console.log('salvar:', customer)
  console.log('salvar 2:', form.value)
  fetchSalvarCustomer();
}
</script>

<style>
@media (min-width: 1024px) {
  .about {
    min-height: 100vh;
    display: flex;
    align-items: center;
  }
}
</style>
