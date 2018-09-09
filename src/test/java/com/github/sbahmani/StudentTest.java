package com.github.sbahmani;

import javax.validation.ConstraintViolationException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author sjb
 */
public class StudentTest {

    @Test
    public void testStudentPersist() {
        Student s = new Student("ahmad", "ahmadi");
        String expected = "";
        try {
            StudentDAO.Persist(s);
        } catch (ConstraintViolationException e) {
            expected = e.getMessage();
        }

        assertEquals(expected, "Validation failed for classes [com.github.sbahmani.Student] during persist time for groups [javax.validation.groups.Default, ]\n"
                + "List of constraint violations:[\n"
                + "	ConstraintViolationImpl{interpolatedMessage='must be a well-formed email address', propertyPath=email, rootBeanClass=class com.github.sbahmani.Student, messageTemplate='{javax.validation.constraints.Email.message}'}\n"
                + "]");
        s.setEmail("a.ahmadi@gmail.com");
        StudentDAO.Persist(s);
        assertNotNull(s.getdBId());

        Student s2 = new Student("mamad", "a.ahmadi@gmail.com");
        try {
            StudentDAO.Persist(s2);
        } catch (org.hibernate.exception.ConstraintViolationException e) {
            expected = e.getMessage();
        }
        assertEquals(expected, "could not execute statement");

    }

}
