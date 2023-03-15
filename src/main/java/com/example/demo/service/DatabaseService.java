package com.example.demo.service;

import com.example.demo.storage.TestersDTO;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class DatabaseService {
    private final SessionFactory factory = new Configuration()
                            .configure("hibernate.cfg.xml")
                            .addAnnotatedClass(TestersDTO.class)
                            .buildSessionFactory();
    public <T> List<T> getAllEntity(Class<T> clazz) {
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        String HQL = "from " + clazz.getSimpleName();
        List<T> list = session.createQuery(HQL, clazz).list();
        transaction.commit();
        session.close();
        return list;
    }

    public <T> T getEntityById(Class<T> clazz, int id) {
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        T result = session.get(clazz, id);
        transaction.commit();
        session.close();
        return result;
    }

    public Session openSession() {
        return factory.openSession();
    }

    public void createEntity(Object entity) {
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(entity);
        transaction.commit();
        session.close();
    }

    public void updateEntity(Object entity) {
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        session.merge(entity);
        transaction.commit();
        session.close();
    }

    public <T> void removeEntityById(Class<T> clazz, int id) {
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        T receivedObject = session.get(clazz, id);
        session.remove(receivedObject);
        transaction.commit();
        session.close();
    }

    public void removeEntity(Object entity) {
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        session.remove(entity);
        transaction.commit();
        session.close();
    }
}
