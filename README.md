dn-issue-optimistic-locking-jpa
========

[JPA](https://en.wikipedia.org/wiki/Java_Persistence_API) Test case demonstrating an issue I reported to [DataNucleus JPA Provider](http://datanucleus.org) folks: https://github.com/datanucleus/datanucleus-rdbms/issues/307

In a nutshell, enabling field-based Optimistic Locking (using a [Version](https://docs.oracle.com/javaee/7/api/javax/persistence/Version.html)-annotated field) along with the [Level2 Cache](http://www.datanucleus.org/products/accessplatform/jpa/persistence.html#cache_level2) disabled did not work with DataNucleus, due to a weird NullPointerException (yup, the billion dollar one! ;)): **Exception thrown while loading remaining rows of query**

This test case contains a set of Maven profiles that run the same tests against other JPA providers. The tests pass as expected with EclipseLink and Hibernate, but not with DataNucleus.

- To run the tests against [DataNucleus](http://datanucleus.org) (default profile): `mvn clean compile test` or `mvn clean compile test -P datanucleus`

- To run the tests against [EclipseLink](http://www.eclipse.org/eclipselink/): `mvn clean compile test -P eclipselink`

- To run the tests against [Hibernate](http://hibernate.org): `mvn clean compile test -P hibernate`

