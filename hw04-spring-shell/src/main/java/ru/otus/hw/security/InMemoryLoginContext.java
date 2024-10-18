package ru.otus.hw.security;

import org.springframework.stereotype.Component;
import ru.otus.hw.domain.Student;

@Component
public class InMemoryLoginContext implements LoginContext {

    private Student studentCache;

    @Override
    public void login(String firstName, String lastName) {

        this.studentCache = new Student(firstName, lastName);
    }

    @Override
    public Student getCurrentStudent() {

        return new Student(this.studentCache.firstName(), this.studentCache.lastName());
    }

    @Override
    public boolean isUserLoggedIn() {

        return this.studentCache != null;
    }
}
