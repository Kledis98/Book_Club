package com.example.book__club.controllers;


import com.example.book__club.models.Book;
import com.example.book__club.models.LoginUser;
import com.example.book__club.models.User;
import com.example.book__club.services.BookService;
import com.example.book__club.services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {

    // Add once service is implemented:
    @Autowired
    private UserService users;
    @Autowired
    private BookService books;

    @GetMapping("/")
    public String index(Model model, @ModelAttribute("newUser") User newUser,
                        @ModelAttribute("newLogin") User newLogin, HttpSession session) {
        if(session.getAttribute("userId") != null) {
            return "redirect:/home";
        }


        // Bind empty User and LoginUser objects to the JSP
        // to capture the form input
        model.addAttribute("newUser", new User());
        model.addAttribute("newLogin", new LoginUser());
        return "index";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("newUser") User newUser,
                           BindingResult result, Model model, HttpSession session) {

        // Call a register method in the service
        // to do some extra validations and create a new user!
        User user = users.register(newUser, result);


        if(result.hasErrors()) {
            // Be sure to send in the empty LoginUser before
            // re-rendering the page.
            model.addAttribute("newLogin", new LoginUser());
            return "index";
        }

        // No errors!
        // TO-DO Later: Store their ID from the DB in session,
        // in other words, log them in.
        session.setAttribute("userId", user.getId());

        return "redirect:/home";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("newLogin") LoginUser newLogin,
                        BindingResult result, Model model, HttpSession session) {

        // Add once service is implemented:
        User user = users.login(newLogin, result);

        if(result.hasErrors()) {
            model.addAttribute("newUser", new User());
            return "index";
        }

        // No errors!
        // Store their ID from the DB in session,
        // in other words, log them in.
        session.setAttribute("userId", user.getId());

        return "redirect:/home";
    }

    @GetMapping("/home")
    public String home(Model model, HttpSession session) {

        if(session.getAttribute("userId") == null) {
            return "redirect:/";
        }

        model.addAttribute("books", books.all());
        model.addAttribute("user", users.findById((Long)session.getAttribute("userId")));
        return "home";
    }

    @GetMapping("/addPage")
    public String addPage(@ModelAttribute("book") Book book, Model model, HttpSession session) {

        User user = users.findById((Long)session.getAttribute("userId"));
        model.addAttribute("user", user);

        return "addPage";
    }

    @PostMapping("/books")
    public String createBook(@Valid @ModelAttribute("book") Book book, BindingResult result) {

        if (result.hasErrors()) {
            return "addPage";
        }

        books.create(book);

        return "redirect:/home";
    }



    @GetMapping("/books/{id}")
    public String bookDetail(Model model,
                             @PathVariable("id") Long id,
                             HttpSession session) {
        if(session.getAttribute("userId") == null) {
            return "redirect:/home";
        }
        Book book = books.findById(id);
        model.addAttribute("book", book);
        model.addAttribute("user", users.findById((Long)session.getAttribute("userId")));
        return "book";
    }

    @GetMapping("/books/{id}/edit")
    public String getEditBook(@PathVariable("id") Long id, Model model,HttpSession session){
        if(session.getAttribute("userId") == null) {
            return "redirect:/home";
        }
        Book bookEdit = books.findById(id);
        model.addAttribute("bookEdit",bookEdit);
        return "editBook";
    }

    @PutMapping("/books/{id}/update")
    public String editBook(@Valid @ModelAttribute("bookEdit") Book bookEdit,
                           BindingResult result, Model model,
                           @PathVariable("id") Long id, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        User userLogged = users.findById(userId);
        if (result.hasErrors()) {
            return "editBook";
        } else {
            bookEdit.setUser(userLogged);
            books.create(bookEdit);
            return "redirect:/home";
        }
    }

    @DeleteMapping("/books/{id}/delete")
    public String deleteBook(@PathVariable("id") Long id, HttpSession session) {
        books.deleteBook(id);
        return "redirect:/home";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }


}