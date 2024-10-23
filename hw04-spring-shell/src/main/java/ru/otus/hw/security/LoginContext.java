package ru.otus.hw.security;

import ru.otus.hw.domain.Student;

public interface LoginContext {

    void login(String firstName, String lastName);

    Student getCurrentStudent();

    boolean isUserLoggedIn();
}
