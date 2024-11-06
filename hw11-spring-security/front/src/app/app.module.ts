import { NgModule, provideZoneChangeDetection } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { RootRoutingModule } from './app.routes';
import { BrowserModule, provideClientHydration } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { provideHttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatDialogModule } from '@angular/material/dialog';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { AppComponent } from './app.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { LoginComponent } from './components/login-component/login.component';
import { BooksComponent } from './components/books-component/books.component';
import { EditBookDialog } from './components/edit-book-dialog-component/edit-book-dialog.component';

@NgModule({
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    RouterOutlet,
    RootRoutingModule,
    FormsModule,
    ReactiveFormsModule,

    CommonModule,

    MatTableModule,
    MatButtonModule,
    MatInputModule,
    MatSelectModule,
    MatIconModule,
    MatDialogModule,
  ],
  declarations: [
    AppComponent,
    LoginComponent,
    BooksComponent,
    EditBookDialog,
  ],
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideClientHydration(),
    provideHttpClient(),
  ],
  bootstrap: [AppComponent]
})
export class AppModule {}