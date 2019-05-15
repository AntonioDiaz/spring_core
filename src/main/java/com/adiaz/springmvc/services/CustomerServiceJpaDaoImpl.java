package com.adiaz.springmvc.services;

import com.adiaz.springmvc.domain.Customer;
import com.adiaz.springmvc.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.List;

@Service
@Profile("jpadao")
public class CustomerServiceJpaDaoImpl implements CustomerService {

    @Autowired
    private EncryptionService encryptionService;

    private EntityManagerFactory emf;

    @PersistenceUnit
    public void setEmf(EntityManagerFactory emf) {
        this.emf = emf;
    }


    @Override
    public List<Customer> listAll() {
        return emf.createEntityManager().createQuery("from Customer", Customer.class).getResultList();
    }

    @Override
    public Customer getById(Integer id) {
        return emf.createEntityManager().find(Customer.class, id);
    }

    @Override
    public Customer createOrUpdate(Customer customer) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        if (customer.getUser()!=null && customer.getUser().getPassword()!=null) {
            customer.getUser().setEncryptedPassword(
                    encryptionService.encryptString(customer.getUser().getPassword()));
        }
        Customer customerSaved = em.merge(customer);
        em.getTransaction().commit();
        return customerSaved;
    }

    @Override
    public void delete(Integer id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(em.find(Customer.class, id));
        em.getTransaction().commit();
    }
}
