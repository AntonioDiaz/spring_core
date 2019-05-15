package com.adiaz.springmvc.services;

import com.adiaz.springmvc.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

@Service
@Profile("jpadao")
public class UserServiceJpaDaoImpl implements UserService {

    @Autowired
    private EntityManagerFactory emf;

    @Autowired
    private EncryptionService encryptionService;

    @Override
    public List<User> listAll() {
        return emf.createEntityManager().createQuery("from User", User.class).getResultList();
    }

    @Override
    public User getById(Integer id) {
        return emf.createEntityManager().find(User.class, id);
    }

    @Override
    public User createOrUpdate(User user) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        if (user.getPassword()!=null) {
            user.setEncryptedPassword(encryptionService.encryptString(user.getPassword()));
        }
        User userSaved = em.merge(user);
        em.getTransaction().commit();
        return userSaved;
    }

    @Override
    public void delete(Integer id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(em.find(User.class, id));
        em.getTransaction().commit();
    }
}
