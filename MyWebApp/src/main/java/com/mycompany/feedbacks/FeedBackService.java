package com.mycompany.feedbacks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FeedBackService {

    @Autowired
    private FeedBackRepository feedbackRepository;

    /**
     * Lấy danh sách tất cả Feedbacks
     * @return List<FeedBack>
     */
    public List<FeedBack> listAll() {
        return (List<FeedBack>) feedbackRepository.findAll();
    }

    /**
     * Lưu hoặc cập nhật Feedback
     * @param feedback Đối tượng Feedback cần lưu
     */
    public void save(FeedBack feedback) {
        feedbackRepository.save(feedback);
    }

    /**
     * Lấy Feedback theo ID
     * @param id ID của Feedback
     * @return FeedBack
     * @throws FeedbackNotFoundException Nếu không tìm thấy Feedback
     */
    public FeedBack get(Integer id) throws FeedBackNotFoundException {
        Optional<FeedBack> result = feedbackRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        throw new FeedBackNotFoundException("Không tìm thấy Feedback với ID: " + id);
    }

    /**
     * Xóa Feedback theo ID
     * @param id ID của Feedback
     * @throws FeedbackNotFoundException Nếu không tìm thấy Feedback
     */
    public void delete(Integer id) throws FeedBackNotFoundException {
        Optional<FeedBack> result = feedbackRepository.findById(id);
        if (result.isPresent()) {
            feedbackRepository.delete(result.get());
        } else {
            throw new FeedBackNotFoundException("Không tìm thấy Feedback với ID: " + id);
        }
    }
}
