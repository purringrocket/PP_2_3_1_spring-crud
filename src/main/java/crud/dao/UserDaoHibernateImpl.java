package crud.dao;

import crud.model.User;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class UserDaoHibernateImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    @Override
    public List<User> getAllUsers() {
        System.out.println("GET ALL");
        List<User> users = entityManager.createQuery("select u from User u", User.class).getResultList();
        return users;
    }

    @Transactional(readOnly = true)
    @Override
    public User getUserById(long id) {
        System.out.println("USER GET");
        User user = entityManager.find(User.class, id);
        return user;
    }

    @Transactional
    @Override
    public void save(User user) {
        entityManager.persist(user);
    }

    @Transactional
    @Override
    public void update(User user) {
        User userToUpdate = (User) entityManager.createQuery("select u from User u where u.id = :id").setParameter("id", user.getId()).getSingleResult();
        userToUpdate.setName(user.getName());
        userToUpdate.setEmail(user.getEmail());
    }

    @Transactional
    @Override
    public void delete(long id) {
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);
    }
}
