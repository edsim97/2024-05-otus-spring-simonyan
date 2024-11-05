import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { catchError, map, Observable, tap, of } from "rxjs";
import { Author, Book, Genre } from "./app.component";

@Injectable({ providedIn: "root" })
export class AppService {

    displayedColumns = [ "id", "title", "author", "genres" ];

    constructor(private http: HttpClient) { }

    getAllBooks(): Observable<Book[]> {

        return this.http.get<Book[]>("api/books");
    }

    saveBook(book: Book) {

        return this.http.post(`api/books`, { ...book })
            .pipe(
                map((result) => true),
                catchError(err => of(false)),
            );
    }

    deleteBook(id: number) {

        return this.http.delete(`api/books/${id}`)
            .pipe(
                map((result) => true),
                catchError(err => of(false)),
            );
    }

    getAllAuthors(): Observable<Author[]> {

        return this.http.get<Author[]>("api/authors");
    }

    getAllGenres(): Observable<Genre[]> {

        return this.http.get<Genre[]>("api/genres");
    }
}
