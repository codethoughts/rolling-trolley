package Database;

import Storage.PersistenceService;
import TransferObjects.Customer;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.PersistenceException;

import static org.junit.Assert.*;

/**
 * Created by Eiger on 5/10/16.
 */
public class ConnectionTest {

  static PersistenceService hb = PersistenceService.getInstance();

  @BeforeClass
  public static void setup() {
    try {
      hb.init();
    } catch ( PersistenceException e ) {
      System.out.println("DB is unavailable");
    }
  }

  @Test
  public void shouldPersistObject() {

    Customer objToPersist = new Customer();

    try {
      hb.execute(() -> {
        hb.persist(objToPersist);
      });
    } catch ( Exception e ) {
      assertFalse(true);
    }

    Customer objFound = (Customer) hb.search(objToPersist.getId(),
            Customer.class);

    assertEquals(objToPersist, objFound);

  }

  @Test
  public void shouldThrowExceptionWhenNoDBConnection() {
    try {
      hb.init();
    } catch ( PersistenceException e ) {
      assertTrue(true);
    }
  }

}
