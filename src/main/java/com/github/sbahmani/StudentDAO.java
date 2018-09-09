package com.github.sbahmani;

import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author sjb
 */
public class StudentDAO {

    public static Student Persist(Student s) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction trx = session.beginTransaction();
            session.save(s);
            trx.commit();
            return s;
        }
    }
}
