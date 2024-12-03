package com.mycompany.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CategoryController {
    @Autowired
    private CategoryService service;

    // Hiển thị danh sách thể loại
    @GetMapping("/categories")
    public String showCategoryList(Model model) {
        model.addAttribute("listCategories", service.listAll());
        return "categories"; // Tên file HTML hiển thị danh sách thể loại
    }

    // Hiển thị form thêm thể loại mới
    @GetMapping("/categories/new")
    public String showNewForm(Model model) {
        model.addAttribute("category", new Category());
        model.addAttribute("pageTitle", "Thêm thể loại mới");
        return "category_form"; // Tên file HTML cho form thêm thể loại
    }

    // Lưu thể loại mới hoặc cập nhật thể loại
    @PostMapping("/categories/save")
    public String saveCategory(Category category, RedirectAttributes ra) {
        service.save(category);
        ra.addFlashAttribute("message", "Thể loại đã được lưu thành công");
        return "redirect:/categories";
    }
   

    // Hiển thị form chỉnh sửa thể loại
    @GetMapping("/categories/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        try {
            Category category = service.get(id);
            model.addAttribute("category", category);
            model.addAttribute("pageTitle", "Sửa thể loại với ID: " + id);
            return "category_form";
        } catch (CategoryNotFoundException e) {
            ra.addFlashAttribute("message", "Không tìm thấy thể loại với ID: " + id);
            return "redirect:/categories";
        }
    }

    // Xóa thể loại theo ID
    @GetMapping("/categories/delete/{id}")
    public String deleteCategory(@PathVariable("id") Integer id, RedirectAttributes ra) {
        try {
            service.delete(id);
            ra.addFlashAttribute("message", "Thể loại đã được xóa thành công");
        } catch (CategoryNotFoundException e) {
            ra.addFlashAttribute("message", "Không thể xóa thể loại với ID: " + id);
        }
        return "redirect:/categories";
    }
}
