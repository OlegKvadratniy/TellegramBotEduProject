package com.example.demo.repository;

import com.example.demo.service.DatabaseService;
import com.example.demo.storage.TestersDTO;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TestersRepository implements DatabaseRepository<TestersDTO> {
    @Autowired
    private DatabaseService databaseService;

    @Override
    public List<TestersDTO> getAll() {
        return databaseService.getAllEntity(TestersDTO.class);
    }

    @Override
    public TestersDTO getById(int id) {
        return databaseService.getEntityById(TestersDTO.class, id);
    }

    @Override
    public void update(TestersDTO object) {
        databaseService.updateEntity(object);
    }

    @Override
    public void create(TestersDTO object) {
        databaseService.createEntity(object);
    }

    @Override
    public void remove(TestersDTO object) {
        databaseService.removeEntity(object);
    }

    @Override
    public void removeById(int id) {
        databaseService.removeEntityById(TestersDTO.class, id);
    }

    public List<TestersDTO> getTestersByChatId(long id) {
        Session session = databaseService.openSession();
        Transaction transaction = session.beginTransaction();
        Query<TestersDTO> query = session.createQuery("from TestersDTO as t where t.chatId = :chat_id", TestersDTO.class);
        query.setParameter("chat_id", id);
        List<TestersDTO> testers = query.getResultList();
        transaction.commit();
        session.close();
        return testers;
    }
}
