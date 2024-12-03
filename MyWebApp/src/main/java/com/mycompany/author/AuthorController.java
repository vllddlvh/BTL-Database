package com.mycompany.author;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthorController {

    @Autowired
    private AuthorService service;

    // Hiển thị danh sách tác giả
    @GetMapping("/authors")
    public String showAuthorList(Model model) {
        model.addAttribute("listAuthors", service.listAll());
        return "authors"; // Tên file HTML hiển thị danh sách tác giả
    }

    // Hiển thị form thêm tác giả mới
    @GetMapping("/authors/new")
    public String showNewForm(Model model) {
        model.addAttribute("author", new Author());
        model.addAttribute("pageTitle", "Thêm tác giả mới");
        return "author_form"; // Tên file HTML cho form thêm tác giả
    }

    // Lưu tác giả mới hoặc cập nhật tác giả
    @PostMapping("/authors/save")
    public String saveAuthor(Author author, RedirectAttributes ra) {
        service.save(author);
        ra.addFlashAttribute("message", "Tác giả đã được lưu thành công");
        return "redirect:/authors";
    }

    // Hiển thị form chỉnh sửa tác giả
    @GetMapping("/authors/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        Author author = service.get(id);
        if (author != null) {
            model.addAttribute("author", author);
            model.addAttribute("pageTitle", "Sửa tác giả với ID: " + id);
            return "author_form";
        } else {
            ra.addFlashAttribute("message", "Không tìm thấy tác giả với ID: " + id);
            return "redirect:/authors";
        }
    }

    // Xóa tác giả theo ID
    @GetMapping("/authors/delete/{id}")
    public String deleteAuthor(@PathVariable("id") Integer id, RedirectAttributes ra) {
        service.delete(id);
        ra.addFlashAttribute("message", "Tác giả đã được xóa thành công");
        return "redirect:/authors";
    }
}
