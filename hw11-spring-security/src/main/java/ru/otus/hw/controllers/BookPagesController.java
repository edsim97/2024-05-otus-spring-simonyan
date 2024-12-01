package ru.otus.hw.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class BookPagesController {

    @GetMapping({"/", "/books"})
    public String getBooks(Model model) {

        return "list";
    }

    @GetMapping("/books/edit")
    public String editBookPage(@RequestParam("id") long id, Model model) {

        return "edit";
    }
}
