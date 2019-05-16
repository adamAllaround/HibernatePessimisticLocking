# JPA Pessimistic Locking with Hibernate

This project presents how to lock database rows pessimistically with JPA using Hibernate.
This is a code behind [allAroundJava's article](https://allaroundjava.com/pessimistic-locking-hibernate/ ) on Pessimistic Locking. 
Take a look there for detailed explanation how this concurrency control option works, and how it is different than Optimistic Locking.

You'll find two ways how you can lock the rows/tables pessimistically: 
* By running a query
* By executing a EntityManager.find with a LockModeType setting.
Both of those options can be found in CarDaoImpl class. 

This project uses a dependency called `com.vmlens.concurrent-junit` which helps run Junit Tests in several threads to demonstrate Pessimistic Locking. 
As a result of running test methods locking table rows concurrently, PessimisticLockExceptions will be raised. 
This is a project where the test methods fail on purpose.

## Running the project
* Clone the repository to your environment
* run ```mvn clean install -DskipTests=true``` This should download the h2 database dependency into your maven directory
* go to your home directory. On linux this will be ```cd /home/[youor username]```
* navigate to h2 database maven directory ```cd .m2/repository/com/h2database/h2/1.4.197```
* run the h2 jar with ```java -jar h2-1.4.197.jar```
* in h2 configuration page configure the database to run as Generic H2 (Server)
* navigate to project directory and run ```mvn clean install```
* tests should be ran against your local in-memory h2 database.

### Don't forget to visit [allAroundJava](https://allaroundjava.com/) for more Java tutorials and analyses. 

Good luck !
