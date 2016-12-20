import Factory.CustomerFactory;
import Model.Customer;
import Services.ShoppingMallService;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Eiger on 6/23/16.
 */
public class PersistenceTest {

  @Test
  public void saveCustomer() {
    Customer customer = CustomerFactory.getInstance().createCustomer("Boris");
    ShoppingMallService.getInstance().registerNewCustomer(customer);

    Customer saved = ShoppingMallService.getInstance().searchCustomer(customer.getId());

    assertEquals(customer, saved);

  }

}
