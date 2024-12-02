package com.mycompany.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService service;

    @GetMapping("/users")
    public String showUserList(Model model) {
        List<User> listUsers = service.listAll();
        model.addAttribute("listUsers", listUsers); // Sử dụng camelCase

        return "users"; // Tên template file (users.html)
    }

    @GetMapping("/users/new")
    public String showNewForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("pageTitle","Thêm người dùng");
        return "user_form";
    }

    @PostMapping("/users/save")
    public String saveUser(User user, RedirectAttributes ra) {
        service.save(user);
        ra.addFlashAttribute("message", "Thanh cong rui nhe");
        return "redirect:/users";
    }

    @GetMapping("/users/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        try {
            User user = service.get(id);
            model.addAttribute("user", user);
            model.addAttribute("pageTitle", "Sửa người dùng có ID: " + id);
            return "user_form";
        } catch (UserNotFoundException e) {
            ra.addFlashAttribute("message", "Không thấy người dùng có ID " + id);
            return "redirect:/users";
        }
    }

    // Phương thức để xóa người dùng
    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, RedirectAttributes ra) {
        try {
            service.delete(id); // Gọi dịch vụ xóa người dùng theo ID
            ra.addFlashAttribute("message", "Người dùng với ID " + id + " đã bị xóa.");
        } catch (UserNotFoundException e) {
            ra.addFlashAttribute("message", "Không thể tìm thấy người dùng với ID " + id);
        }
        return "redirect:/users";
    }
}
