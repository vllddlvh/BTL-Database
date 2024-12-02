package com.mycompany.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class BookController {
    @Autowired
    private BookService service;

    @GetMapping("/books")
    public String showBookList(Model model) {
        model.addAttribute("listBooks", service.listAll());
        return "books"; // Tên file HTML danh sách sách
    }

    @GetMapping("/books/new")
    public String showNewForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("pageTitle", "Thêm sách");
        return "book_form"; // Tên file HTML cho form thêm sách
    }

    @PostMapping("/books/save")
    public String saveBook(Book book, RedirectAttributes ra) {
        service.save(book);
        ra.addFlashAttribute("message", "Sách đã được lưu thành công");
        return "redirect:/books";
    }

    @GetMapping("/books/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        try {
            Book book = service.get(id);
            model.addAttribute("book", book);
            model.addAttribute("pageTitle", "Sửa sách với ID: " + id);
            return "book_form";
        } catch (BookNotFoundException e) {
            ra.addFlashAttribute("message", "Không tìm thấy sách với ID: " + id);
            return "redirect:/books";
        }
    }

    @GetMapping("/books/delete/{id}")
    public String deleteBook(@PathVariable("id") Integer id, RedirectAttributes ra) {
        try {
            service.delete(id);
            ra.addFlashAttribute("message", "Sách đã được xóa thành công");
        } catch (BookNotFoundException e) {
            ra.addFlashAttribute("message", "Không thể xóa sách với ID: " + id);
        }
        return "redirect:/books";
    }
}
