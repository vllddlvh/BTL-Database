package com.mycompany.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired private UserRepository repo;

    public List<User> listAll() {
        return (List<User>) repo.findAll();
    }

    public void save(User user) {
        repo.save(user);
    }

    public User get(Integer id) throws UserNotFoundException {
        Optional<User> result = repo.findById(id);
        if(result.isPresent()) {
            return result.get();
        }

        throw new UserNotFoundException("Khong tim thay ID:" + id);
    }

    // Phương thức xóa người dùng theo ID
    public void delete(Integer id) throws UserNotFoundException {
        Optional<User> user = repo.findById(id);
        if (user.isPresent()) {
            repo.deleteById(id); // Xóa người dùng từ cơ sở dữ liệu
        } else {
            throw new UserNotFoundException("Không tìm thấy người dùng với ID: " + id);
        }
    }
}
