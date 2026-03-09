package restaurant.project.order_table.reponsitory;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import restaurant.project.order_table.model.Book;

@Repository
public interface BookReponsitory extends JpaRepository<Book, Integer> {
    
}