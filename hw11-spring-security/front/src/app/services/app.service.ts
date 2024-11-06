import { HttpClient, HttpResponse } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { catchError, map, Observable, tap, of } from "rxjs";
import { Book } from "../models/book";
import { Genre } from "../models/genre";
import { Author } from "../models/author";

@Injectable({ providedIn: "root" })
export class AppService {

    http = inject(HttpClient);
    displayedColumns = [ "id", "title", "author", "genres" ];

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

    login(credentials: { username: string, password: string }) {

        return this.http.post(
            "api/auth/login",
            credentials,
            { observe: "response", responseType: "text" }
        );
    }
}
