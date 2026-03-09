package restaurant.project.order_table.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import restaurant.project.order_table.model.Book;
import restaurant.project.order_table.model.Category;
import restaurant.project.order_table.service.BookService;
import restaurant.project.order_table.service.CategoryService;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("")
    public String home(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "index";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        Book book = new Book();
        book.setCategory(new Category());

        model.addAttribute("book", book);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "add-book";
    }

    @PostMapping("/add")
    public String addBook(@Valid @ModelAttribute Book book, BindingResult result,
            @RequestParam("imageFile") MultipartFile imageFile, Model model) {
        if (result.hasErrors()) {
            System.out.println("Có lỗi");
            model.addAttribute("content", "add-book :: content");
            model.addAttribute("categories", categoryService.getAllCategories());
            return "add-book";
        }
        if (!imageFile.isEmpty()) {
            try {
                System.out.println("Lưu ảnh");
                String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
                Path uploadPath = Paths.get("order_table/src/main/resources/static/images");
                System.out.println(fileName);
                Files.createDirectories(uploadPath);
                Files.copy(imageFile.getInputStream(),
                        uploadPath.resolve(fileName),
                        StandardCopyOption.REPLACE_EXISTING);

                book.setImage(fileName);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Lưu ảnh");
        Category category = categoryService.getCategoryById(
                book.getCategory().getId()).orElse(null);

        book.setCategory(category);
        bookService.saveBook(book);
        return "redirect:/books";
    }

    @GetMapping("/edit/{id}")
    public String editBookForm(@PathVariable int id, Model model) {
        Optional<Book> book = bookService.getBookById(id);
        if (book.isPresent()) {
            model.addAttribute("book", book.get());
            model.addAttribute("categories", categoryService.getAllCategories());
            return "edit-book";
        } else {
            return "redirect:/books";
        }
    }

    @PostMapping("/edit/{id}")
    public String updateBook(
            @PathVariable int id,
            @Valid @ModelAttribute Book book,
            BindingResult result,
            @RequestParam("imageFile") MultipartFile imageFile,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAllCategories());
            return "edit-book";
        }

        Book oldBook = bookService.getBookById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy sách id: " + id));

        if (!imageFile.isEmpty()) {
            try {
                String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
                Path uploadPath = Paths.get("order_table/src/main/resources/static/images");
                Files.createDirectories(uploadPath);
                Files.copy(imageFile.getInputStream(),
                        uploadPath.resolve(fileName),
                        StandardCopyOption.REPLACE_EXISTING);
                book.setImage(fileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            book.setImage(oldBook.getImage());
        }

        Category category = categoryService
                .getCategoryById(book.getCategory().getId())
                .orElse(null);
        book.setCategory(category);

        bookService.saveBook(book);

        return "redirect:/books";
    }

    @PostMapping("/delete/{id}")
    public String deleteBook(@PathVariable Integer id) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }

}