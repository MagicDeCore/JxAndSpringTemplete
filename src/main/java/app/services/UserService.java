package app.services;

import app.entity.data.user.domain.Info;
import app.entity.data.user.domain.User;
import app.entity.data.user.repository.UserInfoRepository;
import app.entity.data.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired private UserRepository userRepository;
    @Autowired private UserInfoRepository userInfoRepository;

    public User saveUserWithData(String name, String code, String email, String address) {
        User savedUser = null;
        if (!code.equals("")) {
            savedUser = new User(name, code);
            if (!email.equals("") || !address.equals("")) {
                userRepository.save(savedUser);
                Info userInfo = new Info(email, address);
                savedUser.setInfo(userInfoRepository.save(userInfo));
            }
            savedUser = userRepository.save(savedUser);
        }
        return savedUser;
    }

    public User editUserWithNewData(User user, String name, String code, String email, String address) {
        User savedUser;
        user.setName(name);
        user.setCode(code);
        if (!email.equals("") || !address.equals("")) {
            Info info = user.getInfo();
            if (info == null) {
                info = new Info();
                user.setInfo(info);
                info.setUser(user);
            }
            info.setEmail(email);
            info.setAddress(address);
            userInfoRepository.save(info);
        }
        savedUser = userRepository.save(user);
        return savedUser;
    }

    public List<User> searchUsersByInputtedData(String name, String code) {
        List<User> users = null;
        if (!name.equals("") && code.equals("")) {
            users = userRepository.findAllByNameContains(name);
        } else if (!code.equals("") && name.equals("")) {
            users = userRepository.findAllByCodeLike(name);
        }
        return users;
    }

    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }
}
