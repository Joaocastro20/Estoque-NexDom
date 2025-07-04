import { Routes } from '@angular/router';
import { Home } from './components/home/home';
import { Produto } from './components/produto/produto';
import { Movimento } from './components/movimento/movimento';

export const routes: Routes = [
    { path: '', component: Home },
    { path: 'home', component: Home },
    { path: 'produto', component: Produto },
    { path: 'movimento', component: Movimento },
];
