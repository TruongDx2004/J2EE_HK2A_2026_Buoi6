package restaurant.project.order_table.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import restaurant.project.order_table.model.Category;
import restaurant.project.order_table.reponsitory.CategoryReponsitory;

@Service
public class CategoryService {
    @Autowired
    private CategoryReponsitory categoryReponsitory;

    public List<Category> getAllCategories() {
        return categoryReponsitory.findAll();
    }

    public Optional<Category> getCategoryById(int id) {
        return categoryReponsitory.findById(id);
    }

    public void saveCategory(Category category) {
        categoryReponsitory.save(category);
    }   

    public void deleteCategory(int id) {
        categoryReponsitory.deleteById(id);
    }
}
