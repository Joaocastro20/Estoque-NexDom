import './assets/main.css'

import { createApp } from 'vue'
import App from './App.vue'
import router from './router'

// PrimeVue core
import PrimeVue from 'primevue/config'
import Dialog from 'primevue/dialog'
import Button from 'primevue/button'
import InputText from 'primevue/inputtext'

// Estilos
import 'primevue/resources/themes/saga-blue/theme.css'
import 'primevue/resources/primevue.min.css'
import 'primeicons/primeicons.css'
import 'primevue/resources/themes/aura-light-blue/theme.css' // ou outro tema
import 'primevue/resources/primevue.min.css'
import 'primeicons/primeicons.css'

// Componentes usados
import DataTable from 'primevue/datatable'
import Column from 'primevue/column'

const app = createApp(App)

app.use(router)
app.use(PrimeVue)

app.component('DataTable', DataTable)
app.component('Column', Column)
app.component('Dialog', Dialog)
app.component('Button', Button)
app.component('InputText', InputText)

app.mount('#app')
