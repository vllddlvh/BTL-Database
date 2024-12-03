package com.mycompany.feedbacks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class FeedBackService {
    @Autowired private FeedBackRepository repo;

    public List<FeedBack> listAll() {
        return (List<FeedBack>) repo.findAll();
    }

    public void save(FeedBack feedback) {
        repo.save(feedback);
    }

    public FeedBack get(Integer id) throws FeedBackNotFoundException {
        Optional<FeedBack> result = repo.findById(id);
        if(result.isPresent()) {
            return result.get();
        }

        throw new FeedBackNotFoundException("Không tìm thấy phản hồi với ID: " + id);
    }

    public void delete(Integer id) throws FeedBackNotFoundException {
        Optional<FeedBack> result = repo.findById(id);
        if(result.isPresent()) {
            repo.delete(result.get());
        } else {
            throw new FeedBackNotFoundException("Không tìm thấy sách với ID: " + id);
        }
    }
}
