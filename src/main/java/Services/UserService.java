package Services;

import com.example.Model.User;
import java.util.List;

public interface UserService {
    void registerUser(User user);
    boolean existsByEmail(String email);
    List<String> validatePassword(String password); // Updated method signature
    boolean isValidLogin(String email, String password);
}
