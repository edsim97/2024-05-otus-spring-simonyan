import { ChangeDetectionStrategy, Component, inject } from "@angular/core";
import { FormBuilder, Validators } from "@angular/forms";
import { AppService } from "../../services/app.service";
import { filter } from "rxjs";
import { Router } from "@angular/router";

@Component({
    selector: "login",
    templateUrl: "./login.component.html",
    styleUrl: "./login.component.scss",
    changeDetection: ChangeDetectionStrategy.OnPush,
})
export class LoginComponent {
    
    service = inject(AppService);
    private _fb = inject(FormBuilder);
    private _router = inject(Router);

    formGroup = this._fb.nonNullable.group({
        username: this._fb.nonNullable.control("", Validators.required),
        password: this._fb.nonNullable.control("", Validators.required),
    });

    login() {

        this.service.login(this.formGroup.getRawValue())
            .subscribe(response => {

                if (response.ok) {
                    
                    this._router.navigate(["books"])
                }
            });
    }
}