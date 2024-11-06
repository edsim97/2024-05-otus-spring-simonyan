
import { Component, inject, model, OnInit } from '@angular/core';
import { filter, Observable, of, switchMap, take, tap } from 'rxjs';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { AppService } from '../../services/app.service';
import { EditBookDialog } from '../edit-book-dialog-component/edit-book-dialog.component';
import { Genre } from '../../models/genre';
import { Book } from '../../models/book';

@Component({
  selector: 'books',
  templateUrl: './books.component.html',
  styleUrl: './books.component.scss'
})
export class BooksComponent {

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

