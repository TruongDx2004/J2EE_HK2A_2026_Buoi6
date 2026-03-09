package restaurant.project.order_table.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import restaurant.project.order_table.model.Book;
import restaurant.project.order_table.reponsitory.BookReponsitory;

@Service
public class BookService {
    @Autowired 
    private BookReponsitory bookReponsitory;

    public List<Book> getAllBooks() {
        return bookReponsitory.findAll();
    }

    public Optional<Book> getBookById(int id) {
        return bookReponsitory.findById(id);
    }

    public void saveBook(Book book) {
        bookReponsitory.save(book);
    }

    public void deleteBook(int id) {
        bookReponsitory.deleteById(id);
    }
}
