package restaurant.project.order_table.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import restaurant.project.order_table.service.BookService;

import org.springframework.ui.Model;

@Controller
public class HomeController {

    @Autowired
    private BookService bookService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "index";
    }

}
