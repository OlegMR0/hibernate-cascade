package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDetailsDao;
import core.basesyntax.exception.DataAccessException;
import core.basesyntax.model.MessageDetails;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class MessageDetailsDaoImpl extends AbstractDao implements MessageDetailsDao {
    public MessageDetailsDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public MessageDetails create(MessageDetails entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataAccessException("An error occurred while saving the entity!");
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    @Override
    public MessageDetails get(Long id) {
        Session session = null;
        try {
            session = factory.openSession();
            return session.get(MessageDetails.class, id);
        } catch (Exception e) {
            throw new DataAccessException("An error occurred while pulling the entity!");
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
