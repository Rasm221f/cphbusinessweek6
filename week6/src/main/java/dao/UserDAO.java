package dao;

import io.javalin.validation.ValidationException;
import model.Role;
import model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;
import javax.security.auth.login.LoginException;
import java.util.Optional;

public class UserDAO implements ISecurityDAO {

    @Override
    public User getVerifiedUser(String username, String password) throws ValidationException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            User user = session.createQuery("from User where username = :username", User.class)
                    .setParameter("username", username)
                    .uniqueResult();

            if (user != null && user.verifyPassword(password)) {
                return user;
            } else {
                throw new ValidationException("Invalid username or password");
            }
        }
    }

    @Override
    public User createUser(String username, String password) {
        User user = new User(username, password);
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.save(user);
            tx.commit();
        }
        return user;
    }

    @Override
    public Role createRole(String role) {
        Role newRole = new Role(role);
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.save(newRole);
            tx.commit();
        }
        return newRole;
    }

    @Override
    public User addUserRole(String username, String roleName) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            User user = session.createQuery("from User where username = :username", User.class)
                    .setParameter("username", username)
                    .uniqueResult();

            Role role = session.createQuery("from Role where role = :role", Role.class)
                    .setParameter("role", roleName)
                    .uniqueResult();

            if (user != null && role != null) {
                Transaction tx = session.beginTransaction();
                user.addRole(role);
                session.update(user);
                tx.commit();
                return user;
            } else {
                // Handle the case where the user or role doesn't exist
                throw new IllegalStateException("User or Role not found");
            }
        }
    }
}
