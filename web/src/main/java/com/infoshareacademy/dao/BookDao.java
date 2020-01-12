package com.infoshareacademy.dao;

import com.infoshareacademy.domain.entity.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;


@Stateless
public class BookDao {

    private Logger logger = LoggerFactory.getLogger(getClass().getName());

    @PersistenceContext
    private EntityManager em;


    public void addBook(Book book) {

        em.persist(book);
        logger.info("book added");
    }

    public Book findByName(String name) {
        return em.find(Book.class, name);
    }

    public List<Book> findAll() {
        final Query query = em.createQuery("SELECT b FROM Book b");

        return query.getResultList();
    }
}