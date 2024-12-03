package com.mycompany.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository repo;

    // Lấy danh sách tất cả Category
    public List<Category> listAll() {
        return (List<Category>) repo.findAll();
    }

    // Lưu Category mới hoặc cập nhật Category
    public void save(Category category) {
        repo.save(category);
    }

    // Lấy Category theo ID
    public Category get(Integer id) throws CategoryNotFoundException {
        Optional<Category> result = repo.findById(id);
        if (result.isPresent()) {
            return result.get();
        }

        throw new CategoryNotFoundException("Không tìm thấy thể loại với ID: " + id);
    }

    // Xóa Category theo ID
    public void delete(Integer id) throws CategoryNotFoundException {
        if (!repo.existsById(id)) {
            throw new CategoryNotFoundException("Không tìm thấy thể loại với ID: " + id);
        }
        repo.deleteById(id);
    }

}
