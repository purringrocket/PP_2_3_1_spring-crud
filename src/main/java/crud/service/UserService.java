package crud.service;

import crud.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    User getUserById(long id);

    void save(User user);

    void update(User user);

    void delete(long id);
}
