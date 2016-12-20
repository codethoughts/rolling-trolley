package Services;

import Model.Customer;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.Collection;

import static Services.PersistenceService.getInstance;


/**
 * Created by Eiger on 6/23/16.
 */
public class ShoppingMallDAO <T extends Serializable> {

  private ShoppingMallDAO() {

  }

  private static final ShoppingMallDAO instance = new ShoppingMallDAO();

  public static ShoppingMallDAO instance() {
    return instance;
  }

  private void persist( T obj ) {
    getInstance().entityManager.persist(obj);
  }

  private void remove( T obj ) {
    getInstance().entityManager.remove(obj);
  }


  public T search( Object id, Class _ ) {
    return (T) PersistenceService.getInstance().entityManager.find(_, id);
  }

  public Collection<T> search( Class _ ) {

    CriteriaBuilder cb = PersistenceService.getInstance().entityManager.getCriteriaBuilder();
    CriteriaQuery<T> query = cb.createQuery(_);
    Root<T> productRoot = query.from(_);

    query.select(productRoot);

    Collection<T> xs = PersistenceService.getInstance().entityManager.createQuery
            (query).getResultList();

    return xs;
  }

  public void save( T obj ) {
    getInstance().entityManager.getTransaction().begin();
    persist(obj);
    getInstance().entityManager.getTransaction().commit();
  }

  public void delete( T obj ) {
    getInstance().entityManager.getTransaction().begin();
    remove(obj);
    getInstance().entityManager.getTransaction().commit();
  }

  public Customer search( String name ) {

    Customer customer = null;

    Collection<Customer> result = (Collection<Customer>) search(Customer.class);

    for ( Customer c : result ) {
      if ( c.getName() == name ) {
        customer = c;
        break;
      }
    }

    return customer;
  }

}
