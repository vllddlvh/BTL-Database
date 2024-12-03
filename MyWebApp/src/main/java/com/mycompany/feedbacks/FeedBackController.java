package com.mycompany.feedbacks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class FeedBackController {

    @Autowired
    private FeedBackService service;

    // Hiển thị danh sách tất cả các Feedback
    @GetMapping("/feedbacks")
    public String showFeedBackList(Model model) {
        model.addAttribute("listFeedbacks", service.listAll());
        return "feedbacks"; // Tên file HTML danh sách feedbacks
    }

    // Hiển thị form để thêm mới Feedback
    @GetMapping("/feedbacks/new")
    public String showNewForm(Model model) {
        model.addAttribute("feedback", new FeedBack());
        model.addAttribute("pageTitle", "Thêm Feedback");
        return "feedback_form"; // Tên file HTML cho form thêm feedback
    }

    // Lưu hoặc cập nhật Feedback
    @PostMapping("/feedbacks/save")
    public String saveFeedBack(FeedBack feedback, RedirectAttributes ra) {
        service.save(feedback);
        ra.addFlashAttribute("message", "Feedback đã được lưu thành công");
        return "redirect:/feedbacks";
    }

    // Hiển thị form sửa Feedback theo ID
    @GetMapping("/feedbacks/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        try {
            FeedBack feedback = service.get(id);
            model.addAttribute("feedback", feedback);
            model.addAttribute("pageTitle", "Sửa Feedback với ID: " + id);
            return "feedback_form";
        } catch (FeedBackNotFoundException e) {
            ra.addFlashAttribute("message", "Không tìm thấy Feedback với ID: " + id);
            return "redirect:/feedbacks";
        }
    }

    // Xóa Feedback theo ID
    @GetMapping("/feedbacks/delete/{id}")
    public String deleteFeedBack(@PathVariable("id") Integer id, RedirectAttributes ra) {
        try {
            service.delete(id);
            ra.addFlashAttribute("message", "Feedback đã được xóa thành công");
        } catch (FeedBackNotFoundException e) {
            ra.addFlashAttribute("message", "Không thể xóa Feedback với ID: " + id);
        }
        return "redirect:/feedbacks";
    }
}
