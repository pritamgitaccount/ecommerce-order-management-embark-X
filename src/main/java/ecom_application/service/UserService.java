package ecom_application.service;

import ecom_application.entity.User;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private List<User> users = new ArrayList<User>();

    public List<User>fetchAllUsers() {
        return users;
    }

    public List<User> createUser(User user) {
        users.add(user);
        return users;
    }

 public Optional<User> fetchUserById(Long id){
        //use stream to find the user by id
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();

//        for (User user : users) {
//            if (user.getId().equals(id)) {
//                return user;
//            }
//        }
//        return null; // or throw an exception if user not found
 }
}
