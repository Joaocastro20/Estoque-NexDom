<template>
  <div class="card">
    <DataTable :value="customers" paginator :rows="5" :rowsPerPageOptions="[5, 10, 20, 50]"
      tableStyle="min-width: 50rem">
      <Column field="descricao" header="Descrição" style="width: 25%" />
      <Column field="codigo" header="Codigo" style="width: 25%" />
      <Column field="tipoProduto" header="Tipo" style="width: 25%" />
      <Column field="valorFornecedor" header="Valor" style="width: 25%" />
      <Column header="Ações" style="width: 20%">
        <template #body="slotProps">
          <button @click="editCustomerForm(slotProps.data)" class="p-button p-button-sm p-button-text">
            ✏️
          </button>
          <button @click="deleteCustomer(slotProps.data)" class="p-button p-button-sm p-button-danger p-button-text">
            🗑️
          </button>
          <button @click="incrementItem(slotProps.data)" class="p-button p-button-sm p-button-success p-button-text">
            ➕
          </button>
          <button @click="decrementItem(slotProps.data)" class="p-button p-button-sm p-button-warning p-button-text">
            ➖
          </button>
        </template>
      </Column>
    </DataTable>
  </div>

  <Button label="Novo" @click="openNewDialog">Novo</Button>

  <Dialog v-model:visible="visible" :header="isEditing ? 'Editar Produto' : 'Novo Produto'" :style="{ width: '25rem' }">
    <div class="flex items-center gap-4 mb-4">
      <label for="descricao" class="font-semibold w-24">Descrição</label>
      <InputText v-model="form.descricao" id="descricao" class="flex-auto" autocomplete="off" />
    </div>

    <div class="flex items-center gap-4 mb-4">
      <label for="codigo" class="font-semibold w-24">Código</label>
      <InputText v-model="form.codigo" id="codigo" class="flex-auto" autocomplete="off"
        @input="form.codigo = form.codigo.replace(/\D/g, '')" />
    </div>

    <div class="flex items-center gap-4 mb-4">
      <label for="tipo" class="font-semibold w-24">Tipo</label>
      <Dropdown :options="options" id="tipo" v-model="form.tipoProduto" option-label="name" option-value="code"
        placeholder="Selecione" class="flex-auto" />
    </div>

    <div class="flex items-center gap-4 mb-4">
      <label for="valor" class="font-semibold w-24">Valor</label>
      <InputText v-model="form.valorFornecedor" id="valor" class="flex-auto" autocomplete="off" />
    </div>
    <div class="flex items-center gap-4 mb-4">
      <label for="quantidadeEstoque" class="font-semibold w-24">Quantidade</label>
      <InputText v-model="form.quantidadeEstoque" id="quantidadeEstoque" class="flex-auto" autocomplete="off" />
    </div>

    <div class="flex justify-end gap-2">
      <Button type="button" label="Cancelar" severity="secondary" @click="visible = false" />
      <Button type="button" label="Salvar" @click="salvarCustomer" />
    </div>
  </Dialog>

</template>

<script setup>
import { ref, onMounted } from 'vue'
import DataTable from 'primevue/datatable'
import Column from 'primevue/column'
import axios from 'axios'
import InputText from 'primevue/inputtext'
import Dropdown from 'primevue/dropdown'

const customers = ref([])

const selectedCity = ref(null)

const isEditing = ref(false)

const options = [
  { name: 'ELETRÔNICO', code: 'ELETRÔNICO' },
  { name: 'ELETRODOMÉSTICO', code: 'ELETRODOMÉSTICO' },
  { name: 'MÓVEL', code: 'MÓVEL' }
]

function openNewDialog() {
  isEditing.value = false
  form.value = {
    id: null,
    descricao: '',
    codigo: '',
    tipoProduto: null,
    valorFornecedor: 0,
    quantidadeEstoque: 0
  }
  visible.value = true
}

function editCustomerForm(customer) {
  console.log("🚀 ~ editCustomerForm ~ customer:", customer)
  isEditing.value = true
  form.value = { ...customer } // copia os dados para edição
  visible.value = true
}

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
    await fetchCustomers()
    form.value = {
      descricao: '',
      codigo: 0,
      tipoProduto: '',
      valorFornecedor: 0
    }
  } catch (error) {
    console.error('Erro ao salvar produto:', error)
  }
}

async function fetchAlterarCustomer(codigo) {
  try {
    await axios.put(`http://localhost:8080/api/produtos/${codigo}`, form.value)
    console.log('Produto salvo:', response.data)
    visible.value = false
    await fetchCustomers()
    form.value = {
      descricao: '',
      codigo: 0,
      tipoProduto: '',
      valorFornecedor: 0
    }
  } catch (error) {
    console.error('Erro ao atualizar produto:', error)
  }
}

function editCustomer(customer) {
  console.log('Editar:', customer)
  editCustomerForm()
}

function deleteCustomer(customer) {
  console.log('Excluir:', customer)
}
function incrementItem(customer) {
  console.log('increment:', customer)
}
function decrementItem(customer) {
  console.log('increment:', customer)
}

function salvarCustomer(customer) {
  console.log('salvar:', customer.value)
  console.log('salvar 2:', form.value.codigo)
  console.log("🚀 ~ salvarCustomer ~ isEditing:", isEditing.value)

  if (isEditing.value) {
    fetchAlterarCustomer(form.value.codigo)
  } else {
    fetchSalvarCustomer();
  }
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
