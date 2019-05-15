package com.adiaz.springmvc.services;

import com.adiaz.springmvc.domain.User;
import com.adiaz.springmvc.domain.security.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

@Service
@Profile("jpadao")
public class RoleServiceJpaDaoImpl implements RoleService {

    @Autowired
    private EntityManagerFactory emf;

    @Override
    public List<Role> listAll() {
        return emf.createEntityManager().createQuery("from Role", Role.class).getResultList();
    }

    @Override
    public Role getById(Integer id) {
        return emf.createEntityManager().find(Role.class, id);
    }

    @Override
    public Role createOrUpdate(Role role) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Role saved = em.merge(role);
        em.getTransaction().commit();
        return saved;
    }

    @Override
    public void delete(Integer id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(em.find(Role.class, id));
        em.getTransaction().commit();
    }
}
