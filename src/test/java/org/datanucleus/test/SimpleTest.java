package org.datanucleus.test;

import org.junit.*;
import javax.persistence.*;

import static org.junit.Assert.*;
import mydomain.model.*;
import org.datanucleus.util.NucleusLogger;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SimpleTest
{

    private static EntityManagerFactory emf;

    @BeforeClass
    public static void beforeAll() {
        emf = Persistence.createEntityManagerFactory("MyTest");
    }

    @AfterClass
    public static void afterAll() {
        emf.close();
    }

    @Test
    public void testSimple()
    {
        NucleusLogger.GENERAL.info(">> test START");

        executeInTransaction((em) -> {
            final Set<Person> resultSet =
                    new HashSet<>(em.createQuery("SELECT p FROM Person p", Person.class).getResultList());
            assertTrue(resultSet.isEmpty());
        });

        final Set<Person> personList = IntStream.range(1, 2)
                .mapToObj(i -> {
                    final Person person = new Person.Builder().uniqueName("person " + i).build();
                    person.setLastAddress(new Address.Builder().person(person).city("CI").city("Abidjan").build());
                    return person;
                })
                .collect(Collectors.toSet());

        //Persist with version
        executeInTransaction((em) -> em.persist(personList));

        //Perform an operation
        final Map<Long, Person> newPersonList = personList.stream()
                .collect(Collectors.toMap(AbstractBaseJpaEntity::getId,
                        p -> new Person.Builder()
                                .uuid(p.getUuid())
                                .uniqueName(p.getUniqueName() + " (renamed)").build()));

        //Now read them all
        executeInTransaction((em) -> {
            final Set<Person> resultSet =
                    new HashSet<>(em.createQuery("SELECT p FROM Person p", Person.class).getResultList());

            assertEquals(personList, resultSet);

            resultSet.forEach(p -> {
                p.setUniqueName(newPersonList.get(p.getId()).getUniqueName());
                em.merge(p);
            });
        });

        executeInTransaction((em) -> {
            final Set<Person> resultSet =
                    new HashSet<>(em.createQuery("SELECT p FROM Person p", Person.class).getResultList());

            assertEquals(new HashSet<>(newPersonList.values()), resultSet);
            resultSet.forEach(p -> assertEquals(newPersonList.get(p.getId()).getUniqueName(), p.getUniqueName()));
        });

        NucleusLogger.GENERAL.info(">> test END");
    }

    private void executeInTransaction(final TestRunnable runnable) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try
        {
            tx.begin();

            runnable.run(em);

            em.flush();

            tx.commit();
        }
        catch (Throwable thr)
        {
            NucleusLogger.GENERAL.error(">> Exception in operation execution", thr);
            fail("Failed test : " + thr.getMessage());
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            em.close();
        }
    }

    @FunctionalInterface
    private interface TestRunnable {
        void run(EntityManager em);
    }
}
