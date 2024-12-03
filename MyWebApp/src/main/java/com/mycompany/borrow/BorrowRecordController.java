package com.mycompany.borrow;

import com.mycompany.book.Book;
import com.mycompany.book.BookService;
import com.mycompany.user.User;
import com.mycompany.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String returnBook(@PathVariable("id") Integer id) {
        try {
            BorrowRecord borrowRecord = borrowRecordService.get(id);
            if ("Đã trả".equalsIgnoreCase(borrowRecord.getStatus())) {
                // Nếu sách đã trả rồi, không làm gì cả
                return "redirect:/borrow";
            }
            borrowRecord.setReturnDate(new Date()); // Cập nhật ngày trả là ngày hiện tại
            borrowRecord.setStatus("Đã trả"); // Cập nhật trạng thái
            borrowRecordService.save(borrowRecord);
        } catch (BorrowRecordNotFoundException e) {
            System.out.println("Không tìm thấy bản ghi mượn với ID: " + id);
        }
        return "redirect:/borrow";
    }
}
