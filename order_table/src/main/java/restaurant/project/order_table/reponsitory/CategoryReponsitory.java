package restaurant.project.order_table.reponsitory;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import restaurant.project.order_table.model.Category;

@Repository
public interface CategoryReponsitory extends JpaRepository<Category, Integer> {
    
}
