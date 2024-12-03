package com.mycompany.author;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository repository;

    // Lấy danh sách tất cả tác giả
    public List<Author> listAll() {
        return repository.findAll();
    }

    // Lưu một tác giả (thêm mới hoặc cập nhật)
    public void save(Author author) {
        repository.save(author);
    }

    // Lấy thông tin một tác giả theo ID
    public Author get(Integer id) {
        Optional<Author> result = repository.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        return null; // Trả về null hoặc ném ngoại lệ nếu không tìm thấy
    }

    // Xóa một tác giả theo ID
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}
