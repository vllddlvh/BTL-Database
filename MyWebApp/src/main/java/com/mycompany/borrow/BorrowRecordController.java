package com.mycompany.borrow;

import com.mycompany.book.Book;
import com.mycompany.book.BookService;
import com.mycompany.user.User;
import com.mycompany.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/borrow")
public class BorrowRecordController {

    @Autowired
    private BorrowRecordService borrowRecordService;

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    // Hiển thị danh sách bản ghi mượn
    @GetMapping
    public String listBorrowRecords(Model model) {
        List<BorrowRecord> listBorrowRecords = borrowRecordService.listAll();
        model.addAttribute("listBorrowRecords", listBorrowRecords);
        return "borrow"; // Tên file HTML: borrow.html
    }

    // Hiển thị form mượn sách mới
    @GetMapping("/new")
    public String showNewBorrowForm(Model model) {
        BorrowRecord borrowRecord = new BorrowRecord();
        List<User> listUsers = userService.listAll();
        List<Book> listBooks = bookService.listAll();

        model.addAttribute("borrowRecord", borrowRecord);
        model.addAttribute("listUsers", listUsers);
        model.addAttribute("listBooks", listBooks);
        return "borrow_form"; // Tên file HTML: borrow_form.html
    }

    // Lưu bản ghi mượn mới
    @PostMapping("/save")
    public String saveBorrowRecord(@ModelAttribute("borrowRecord") BorrowRecord borrowRecord) {
        borrowRecord.setBorrowDate(new Date()); // Ngày mượn là ngày hiện tại
        borrowRecord.setStatus("Đang mượn");
        borrowRecordService.save(borrowRecord);
        return "redirect:/borrow";
    }

    // Trả sách
    @GetMapping("/return/{id}")
    public String returnBook(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            // Lấy bản ghi mượn
            BorrowRecord borrowRecord = borrowRecordService.get(id);

            // Kiểm tra trạng thái trước khi xử lý
            if ("Đã trả".equalsIgnoreCase(borrowRecord.getStatus())) {
                redirectAttributes.addFlashAttribute("message", "Sách đã được trả trước đó!");
                return "redirect:/borrow";
            }

            // Cập nhật trạng thái và ngày trả
            borrowRecord.setReturnDate(new Date()); // Ngày trả là ngày hiện tại
            borrowRecord.setStatus("Đã trả"); // Cập nhật trạng thái

            // Lưu thay đổi vào cơ sở dữ liệu
            borrowRecordService.save(borrowRecord);

            // Thêm thông báo thành công
            redirectAttributes.addFlashAttribute("message", "Trả sách thành công cho bản ghi ID: " + id);

        } catch (BorrowRecordNotFoundException e) {
            // Nếu không tìm thấy bản ghi, thêm thông báo lỗi
            redirectAttributes.addFlashAttribute("message", "Không tìm thấy bản ghi mượn với ID: " + id);
        }

        // Điều hướng về danh sách bản ghi
        return "redirect:/borrow";
    }

}
