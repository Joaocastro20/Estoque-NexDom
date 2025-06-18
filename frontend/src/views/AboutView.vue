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
    <span class="text-surface-500 dark:text-surface-400 block mb-8">Update your information.</span>
    <div class="flex items-center gap-4 mb-4">
        <label for="username" class="font-semibold w-24">Username</label>
        <InputText id="username" class="flex-auto" autocomplete="off" />
    </div>
    <div class="flex items-center gap-4 mb-8">
        <label for="email" class="font-semibold w-24">Email</label>
        <InputText id="email" class="flex-auto" autocomplete="off" />
    </div>
    <div class="flex justify-end gap-2">
        <Button type="button" label="Cancel" severity="secondary" @click="visible = false"></Button>
        <Button type="button" label="Save" @click="visible = false"></Button>
    </div>
</Dialog>
   
</template>

<script setup>
import { ref, onMounted } from 'vue'
import  DataTable  from 'primevue/datatable'
import Column from 'primevue/column'
import axios from 'axios'
import InputText from 'primevue/inputtext'

const customers = ref([])



const visible = ref(false)

const form = ref({
  username: '',
  email: ''
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

function editCustomer(customer) {
  console.log('Editar:', customer)
}

function deleteCustomer(customer) {
  console.log('Excluir:', customer)
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
