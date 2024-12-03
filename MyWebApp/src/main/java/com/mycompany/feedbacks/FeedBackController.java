package com.mycompany.feedbacks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class FeedBackController {

    @Autowired
    private FeedBackService service;

    @GetMapping("/feedbacks")
    public String showFeedbackList(Model model) {
        List<FeedBack> listFeedbacks = service.listAll();
        model.addAttribute("listFeedbacks", listFeedbacks);

        return "feedbacks"; // Tên template file (feedbacks.html)
    }

    @GetMapping("/feedbacks/new")
    public String showNewForm(Model model) {
        model.addAttribute("feedback", new FeedBack());
        model.addAttribute("pageTitle", "Thêm Feedback");
        return "feedback_form";
    }

    @PostMapping("/feedbacks/save")
    public String saveFeedback(FeedBack feedback, RedirectAttributes ra) {
        service.save(feedback);
        ra.addFlashAttribute("message", "Feedback đã được lưu thành công!");
        return "redirect:/feedbacks";
    }

    @GetMapping("/feedbacks/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        try {
            FeedBack feedback = service.get(id);
            model.addAttribute("feedback", feedback);
            model.addAttribute("pageTitle", "Sửa Feedback có ID: " + id);
            return "feedback_form";
        } catch (FeedBackNotFoundException e) {
            ra.addFlashAttribute("message", "Không thấy Feedback có ID " + id);
            return "redirect:/feedbacks";
        }
    }

    @GetMapping("/feedbacks/delete/{id}")
    public String deleteFeedback(@PathVariable("id") Integer id, RedirectAttributes ra) {
        try {
            service.delete(id); // Gọi dịch vụ xóa Feedback theo ID
            ra.addFlashAttribute("message", "Feedback với ID " + id + " đã bị xóa.");
        } catch (FeedBackNotFoundException e) {
            ra.addFlashAttribute("message", "Không thể tìm thấy Feedback với ID " + id);
        }
        return "redirect:/feedbacks";
    }
}
