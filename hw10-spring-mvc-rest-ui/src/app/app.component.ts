import { Component, inject, model, OnInit } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { AppService } from './app.service';
import { filter, Observable, of, switchMap, take, tap } from 'rxjs';
import { MatTableModule } from '@angular/material/table';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { EditBookDialog } from './edit-book-dialog.component/edit-book-dialog.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, CommonModule, MatTableModule, MatButtonModule, MatIconModule, MatDialogModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {

    readonly displayedColumns = [ "id", "title", "author", "genres", "actions" ] as const;
    readonly service = inject(AppService);
    readonly dialog = inject(MatDialog);

    readonly allAuthors = model(this.service.getAllAuthors());
    readonly allGenres = model(this.service.getAllGenres());

    public dataSource = this.service.getAllBooks();

    handleEditButtonClick(book: Book | null) {
        
        this.dialog.open(EditBookDialog, { data: { book, allAuthors: this.allAuthors(), allGenres: this.allGenres() } })
            .afterClosed()
            .pipe(filter(Boolean))
            .subscribe(book => this.service.saveBook(book)
                .subscribe(() => this.dataSource = this.service.getAllBooks())
            );
    }

    handleDeleteButtonClick(book: Book) {
        
        this.service.deleteBook(book.id).subscribe(() => this.dataSource = this.service.getAllBooks());
    }

    genreArrayToString(arr: Genre[]) {

        return !!arr ? arr.map(genre => genre.name).join(", ") : "";
    }
}

export interface Author { id: number; fullName: string; }
export interface Genre { id: number; name: string; }
export interface Book { id: number; title: string; author: Author; genres: Genre[]; }
