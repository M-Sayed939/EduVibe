package Services;

import com.example.Model.User;
import com.example.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void registerUser(User user) {
        userRepository.save(user);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public List<String> validatePassword(String password) {
        List<String> messages = new ArrayList<>();

        if (!Pattern.compile("(?=.*[a-z])").matcher(password).find()) {
            messages.add("Password must contain at least one lowercase letter.");
        }
        if (!Pattern.compile("(?=.*[A-Z])").matcher(password).find()) {
            messages.add("Password must contain at least one uppercase letter.");
        }
        if (!Pattern.compile("(?=.*\\d)").matcher(password).find()) {
            messages.add("Password must contain at least one digit.");
        }

        return messages;
    }

    @Override
    public boolean isValidLogin(String email, String password) {
        User user = userRepository.findByEmail(email);
        return user != null && user.getPassword().equals(password);
    }
}

