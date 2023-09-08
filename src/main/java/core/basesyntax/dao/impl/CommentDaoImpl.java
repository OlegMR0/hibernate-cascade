package core.basesyntax.dao.impl;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.exception.DataAccessException;
import core.basesyntax.model.Comment;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class CommentDaoImpl extends AbstractDao implements CommentDao {
    public CommentDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Comment create(Comment entity) {
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
    public Comment get(Long id) {
        Session session = null;
        Comment comment = null;
        try {
            session = factory.openSession();
            comment = session.get(Comment.class, id);
        } catch (Exception e) {
            throw new DataAccessException("An error occurred while pulling the entity!");
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return comment;
    }

    @Override
    public List<Comment> getAll() {
        Session session = null;
        Comment comment = null;
        List<Comment> list = null;
        try {
            session = factory.openSession();
//            list = session.createQuery("FROM Comment", Comment.class).list();
            CriteriaQuery<Comment> query = session.getCriteriaBuilder().createQuery(Comment.class);
            query.from(Comment.class);
            list = session.createQuery(query).getResultList();
        } catch (Exception e) {
            throw new DataAccessException("An error occurred while pulling the entity!");
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return list;
    }

    @Override
    public void remove(Comment entity) {
        Session session = null;
        Comment comment = null;
        try {
            session = factory.openSession();
            session.remove(entity);
        } catch (Exception e) {
            throw new DataAccessException("An error occurred while removing the entity!");
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
