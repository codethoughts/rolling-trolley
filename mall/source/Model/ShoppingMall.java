package Model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Eiger on 6/23/16.
 */
public class ShoppingMall {

  private static ShoppingMall instance = new ShoppingMall();
  private Set<Customer> currentCustomers;

  private ShoppingMall() {
    currentCustomers = new HashSet<>();
  }

  public static ShoppingMall getInstance() {
    return instance;
  }

  public void addCustomer( Customer customer ) {
    currentCustomers.add(customer);
  }

  public void removeCustomer( Customer customer ) {
    currentCustomers.remove(customer);
  }

  public Collection<Customer> getCustomers() {
    return currentCustomers;
  }

  public Set<Customer> getCurrentCustomers() {
    return currentCustomers;
  }

  public void setCurrentCustomers( Set<Customer> currentCustomers ) {
    this.currentCustomers = currentCustomers;
  }

}
