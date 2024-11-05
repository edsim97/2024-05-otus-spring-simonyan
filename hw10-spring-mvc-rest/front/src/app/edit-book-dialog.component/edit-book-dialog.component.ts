import { ChangeDetectionStrategy, Component, inject, model } from "@angular/core";
import { MatButtonModule } from "@angular/material/button";
import { MatInputModule } from "@angular/material/input";
import { MatSelectModule } from "@angular/material/select";
import { filter, Observable, of, switchMap, take } from 'rxjs';
import { CommonModule } from '@angular/common';import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from "@angular/material/dialog";
import { Author, Book, Genre } from "../app.component";
import { FormArray, FormControl, FormGroup, FormsModule, ReactiveFormsModule } from "@angular/forms";

@Component({
    selector: "edit-book-dialog",
    template: `
    <div class="book-edit">
        <h2>
            @if (!!book()?.id) {
                Edit book {{book().id}}
            }
            @else {
                Create new book
            }
        </h2>
        <ng-container [formGroup]="formGroup">
            <mat-form-field>
                <mat-label>Title</mat-label>
                <input matInput formControlName="title">
            </mat-form-field>
            <mat-form-field>
                <mat-label>Author</mat-label>
                <mat-select formControlName="author">
                    @for (author of allAuthors() | async; track author) {
                        <mat-option [value]="author">{{author.fullName}}</mat-option>
                    }
                </mat-select>  
            </mat-form-field>
            <mat-form-field>
                <mat-label>Genres</mat-label>
                <mat-select formControlName="genres" multiple>
                    @for (genre of allGenres() | async; track genre) {
                        <mat-option [value]="genre">{{genre.name}}</mat-option>
                    }
                </mat-select>  
            </mat-form-field>
        </ng-container>
        
        <div class="buttons-group">
            <button mat-raised-button color="primary" (click)="save()">{{ !!book()?.id ? "Update" : "Create" }}</button>
            <button mat-raised-button color="secondary" (click)="cancel()">Cancel</button>
        </div>
    </div>
    `,
    styles: `
        .book-edit {
            display: flex;
            flex-direction: column;
            padding: 16px;
        }

        .buttons-group {
            display: flex;
            flex-direction: row;
            justify-content: space-around;
        }
    `,
    standalone: true,
    imports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        MatDialogModule,
        MatButtonModule,
        MatInputModule,
        MatSelectModule
    ],
    changeDetection: ChangeDetectionStrategy.OnPush,
})
export class EditBookDialog {

    readonly dialogRef = inject(MatDialogRef<EditBookDialog>);
    readonly data = inject<{book: Book, allAuthors: Observable<Author[]>, allGenres: Observable<Genre[]>}>(MAT_DIALOG_DATA);
    readonly book = model(this.data.book);
    readonly allAuthors = model(this.data.allAuthors);
    readonly allGenres = model(this.data.allGenres);

    formGroup: FormGroup<{
        id: FormControl<number | null>,
        title: FormControl<string | null>,
        author: FormControl<Author | null>,
        genres: FormControl<Genre[] | null>,
    }> = new FormGroup({
        id: new FormControl(this.book()?.id),
        title: new FormControl(this.book()?.title),
        author: new FormControl(this.book()?.author),
        genres: new FormControl(this.book()?.genres),
    });

    save() {

        this.dialogRef.close(this.formGroup.value);
    }

    cancel() {

        this.dialogRef.close();
    }
}