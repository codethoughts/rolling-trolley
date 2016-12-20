import Enums.StoreType;
import Factory.CustomerFactory;
import Factory.StoreFactory;
import Model.Customer;
import Model.Store;
import Services.ShoppingMallService;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Eiger on 6/23/16.
 */
public class ShoppingMallServiceTest {
  
  @Test
  public void testRegisterNewStore() throws Exception {
    Store store = StoreFactory.getInstance().createStore("ABC", StoreType.SHOE_STORE);
    Integer id = ShoppingMallService.getInstance().registerNewStore(store);
    Store foundObject = ShoppingMallService.getInstance().searchStore(id);
    assertNotNull(foundObject);
  }

  @Test
  public void testDeleteStore() throws Exception {
    Store store = StoreFactory.getInstance().createStore("ABC", StoreType.SHOE_STORE);
    Integer id = ShoppingMallService.getInstance().registerNewStore(store);
    ShoppingMallService.getInstance().deleteStore(id.toString());

    Store foundObject = ShoppingMallService.getInstance().searchStore(id);

    assertNull(foundObject);
  }

  @Test
  public void testEnter() throws Exception {
    Customer customer = CustomerFactory.getInstance().createCustomer("Boris");
    ShoppingMallService.getInstance().enter(customer);
    boolean isHere = ShoppingMallService.getInstance().getCustomers().contains(customer);

    assertTrue(isHere);

  }

  @Test
  public void testExit() throws Exception {
    Customer customer = CustomerFactory.getInstance().createCustomer("Boris");
    ShoppingMallService.getInstance().enter(customer);
    ShoppingMallService.getInstance().exit(customer);

    boolean isHere = ShoppingMallService.getInstance().getCustomers().contains(customer);

    assertFalse(isHere);
  }
}