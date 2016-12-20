import Enums.DiscountType;
import Enums.StoreType;
import Factory.CustomerFactory;
import Factory.ItemFactory;
import Factory.StoreFactory;
import Model.Customer;
import Model.Item;
import Model.Store;
import Services.StoreManagementService;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Eiger on 6/23/16.
 */
public class StoreManagementServiceTest {
  
  @Test
  public void testAddNewItem() throws Exception {
    Store store = StoreFactory.getInstance().createStore("ABC", StoreType.BOOK_STORE);
    Item item = ItemFactory.getInstance().createItem("Banana", store, 5.0);
    StoreManagementService.getInstance().addNewItem(store, item);
    boolean isAdded = StoreManagementService.getInstance().getItems(store).contains(item);
    assertTrue(isAdded);
  }

  @Test
  public void testRemoveItem() throws Exception {
    Store store = StoreFactory.getInstance().createStore("ABC", StoreType.BOOK_STORE);
    Item item = ItemFactory.getInstance().createItem("Banana", store, 4.0);
    StoreManagementService.getInstance().addNewItem(store, item);
    StoreManagementService.getInstance().removeItem(item);
    boolean isRemoved = !StoreManagementService.getInstance().getItems(store).contains(item);
    assertTrue(isRemoved);
  }

  @Test
  public void testMakeDiscount() throws Exception {
    Store store = StoreFactory.getInstance().createStore("Bazaar", StoreType.BOOK_STORE);
    Item item = ItemFactory.getInstance().createItem("Strawberry", store, 20.0);
    StoreManagementService.getInstance().makeDiscount(item, DiscountType.Discount_50);
    assertEquals(DiscountType.Discount_50, item.getDiscount());
  }

  @Test
  public void testCurrentCustomersCounter() throws Exception {
    Store store = StoreFactory.getInstance().createStore("Newbies", StoreType.BOOK_STORE);
    Customer customer = CustomerFactory.getInstance().createCustomer("Me");
    Customer customer1 = CustomerFactory.getInstance().createCustomer("Friend");

    StoreManagementService.getInstance().enter(store, customer);
    StoreManagementService.getInstance().enter(store, customer1);

    assertEquals(2, store.getCurrentCustomers().size());

    StoreManagementService.getInstance().exit(store, customer);

    assertEquals(1, store.getCurrentCustomers().size());
  }

}