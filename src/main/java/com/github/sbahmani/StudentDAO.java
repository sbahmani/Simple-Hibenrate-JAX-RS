package com.github.sbahmani;

import java.util.List;
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

    public static Student Delete(Student s) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction trx = session.beginTransaction();
            session.delete(s);
            trx.commit();
            return s;
        }
    }

    public static Student LoadByID(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT s from Student s where s.dBId=:id", Student.class).setParameter("id", id).uniqueResult();
        }
    }

    public static List<Student> LoadAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT s from Student s ", Student.class).list();
        }
    }

    public static Student LoadByEmail(String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT s from Student s where s.email like :email", Student.class).setParameter("email", email).uniqueResult();
        }
    }
}
