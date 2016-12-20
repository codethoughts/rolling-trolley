package Services;

import Enums.DatabaseSchema;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.Serializable;

public class PersistenceService <T extends Serializable> {
  private static PersistenceService instance = new PersistenceService();

  public static PersistenceService getInstance() {
    return instance;
  }

  private PersistenceService() {
    EntityManagerFactory emf =
            Persistence.createEntityManagerFactory(DatabaseSchema.connector.name());
    entityManager =
            emf.createEntityManager();
  }

  public EntityManager entityManager;

  public void execute( Runnable transaction ) {
    entityManager.getTransaction().begin();
    transaction.run();
    entityManager.getTransaction().commit();
  }
}
