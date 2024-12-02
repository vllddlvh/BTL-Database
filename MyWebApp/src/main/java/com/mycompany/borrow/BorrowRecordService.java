package com.mycompany.borrow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BorrowRecordService {
    @Autowired private BorrowRecordRepository repo;

    public List<BorrowRecord> listAll() {
        return (List<BorrowRecord>) repo.findAll();
    }

    public void save(BorrowRecord borrowRecord) {
        repo.save(borrowRecord);
    }

    public BorrowRecord get(Integer id) throws BorrowRecordNotFoundException {
        Optional<BorrowRecord> result = repo.findById(id);
        if(result.isPresent()) {
            return result.get();
        }

        throw new BorrowRecordNotFoundException("Không tìm thấy bản ghi mượn với ID: " + id);
    }
}
