package Storage;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

enum Schema {
  connector
}

public class PersistenceService <T extends Object, Serializable> {
  private static PersistenceService instance = new PersistenceService();
  
  public static PersistenceService getInstance() {
    return instance;
  }

  public void init() throws PersistenceException {
    EntityManagerFactory emf =
            Persistence.createEntityManagerFactory(Schema.connector.name());
    entityManager =
            emf.createEntityManager();
  }
  
  private PersistenceService() {
  }

  private static EntityManager entityManager;

  public void execute( ITransaction transaction )
          throws Exception {
    try {

      entityManager.getTransaction().begin();
      transaction.run();
      entityManager.getTransaction().commit();

    } catch ( Exception ex ) {
      throw ex;
    }
  }

  public void persist( T obj ) {
    entityManager.persist(obj);
  }

  public void remove( T obj ) {
    entityManager.remove(obj);
  }

  public T search( Object id, Class<T> _ ) {
    T obj = entityManager.find(_, id);
    return obj;
  }

}
