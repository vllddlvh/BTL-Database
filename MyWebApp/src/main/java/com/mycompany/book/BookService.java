package com.mycompany.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired private BookRepository repo;

    public List<Book> listAll() {
        return (List<Book>) repo.findAll();
    }

    public void save(Book book) {
        repo.save(book);
    }

    public Book get(Integer id) throws BookNotFoundException {
        Optional<Book> result = repo.findById(id);
        if(result.isPresent()) {
            return result.get();
        }
        throw new BookNotFoundException("Không tìm thấy sách với ID: " + id);
    }

    public void delete(Integer id) throws BookNotFoundException {
        Optional<Book> result = repo.findById(id);
        if(result.isPresent()) {
            repo.delete(result.get());
        } else {
            throw new BookNotFoundException("Không tìm thấy sách với ID: " + id);
        }
    }
}
