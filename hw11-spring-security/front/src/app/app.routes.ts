import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';
import { BooksComponent } from './components/books-component/books.component';
import { LoginComponent } from './components/login-component/login.component';

export const routes: Routes = [
    { path: "", pathMatch: "full", redirectTo: "/login" },
    { path: "books", component: BooksComponent },
    { path: "login", component: LoginComponent },
];

@NgModule({
    exports: [RouterModule],
    imports: [RouterModule.forRoot(routes)]
})
export class RootRoutingModule {}