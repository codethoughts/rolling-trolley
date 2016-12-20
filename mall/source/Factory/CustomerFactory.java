package Factory;

import Model.Customer;

/**
 * Created by Eiger on 6/23/16.
 */
public class CustomerFactory {
  private static CustomerFactory instance = new CustomerFactory();

  public static CustomerFactory getInstance() {
    return instance;
  }

  private CustomerFactory() {

  }

  public Customer createCustomer( String name ) {
    return new Customer(name);
  }
}
