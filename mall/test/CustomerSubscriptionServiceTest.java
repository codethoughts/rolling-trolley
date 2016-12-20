import Enums.DiscountType;
import Enums.StoreType;
import Factory.CustomerFactory;
import Factory.ItemFactory;
import Factory.StoreFactory;
import Model.Customer;
import Model.Item;
import Model.Store;
import Model.Subscription;
import Services.CustomerSubscriptionService;
import Services.StoreManagementService;
import org.junit.Test;

import java.util.Collection;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertFalse;

/**
 * Created by Eiger on 6/22/16.
 */
public class CustomerSubscriptionServiceTest {

  @Test
  public void testSubscribeOnNewItemUpdates() {
    Customer customer = CustomerFactory.getInstance().createCustomer("Boris");
    Store store = StoreFactory.getInstance().createStore("ABC", StoreType.GAME_STORE);
    Item item = ItemFactory.getInstance().createItem("Dur", store, 30.0);

    CustomerSubscriptionService.instance().subscribeOnNewItemAppear(customer, store);
    StoreManagementService.getInstance().addNewItem(store, item);

    boolean isFound = false;
    Collection<Subscription> c = CustomerSubscriptionService.instance()
            .getNewItemAppearSubscriptions();
    for ( Subscription s : c) {
      if ( s.customer == customer && s.store == store ) {
        isFound = true;
      }
    }
    assertTrue(isFound);
  }

  @Test
  public void testSubscribeOnDiscountUpdates() {
    Customer customer = CustomerFactory.getInstance().createCustomer("Boris");
    Store store = StoreFactory.getInstance().createStore("ABC", StoreType.GAME_STORE);
    Item item = ItemFactory.getInstance().createItem("Dur", store, 20.0);

    CustomerSubscriptionService.instance().subscribeOnDiscount(customer, store);
    StoreManagementService.getInstance().makeDiscount(item, DiscountType.Discount_50);

    boolean isFound = false;
    for ( Subscription s : CustomerSubscriptionService.instance()
            .getDiscountSubscriptions() ) {
      if ( s.customer == customer && s.store == store ) {
        isFound = true;
      }
    }
    assertTrue(isFound);

  }

  @Test
  public void testUnsubscribeFromDiscountUpdates() {
    Customer customer = CustomerFactory.getInstance().createCustomer("Boris");
    Store store = StoreFactory.getInstance().createStore("ABC", StoreType.GAME_STORE);
    Item item = ItemFactory.getInstance().createItem("Dur", store, 10.0);

    CustomerSubscriptionService.instance().subscribeOnDiscount(customer, store);

    CustomerSubscriptionService.instance().unsubscribeFromDiscountUpdates(customer,
            store);

    boolean isFound = false;
    for ( Subscription s : CustomerSubscriptionService.instance().getDiscountSubscriptions() ) {
      if ( s.customer == customer && s.store == store ) {
        isFound = true;
      }
    }
    assertFalse(isFound);
    StoreManagementService.getInstance().makeDiscount(item, DiscountType.Discount_50);
  }

  @Test
  public void testUnsubscribeFromNewItemUpdates() {
    Customer customer = CustomerFactory.getInstance().createCustomer("Boris");
    Store store = StoreFactory.getInstance().createStore("ABC", StoreType.GAME_STORE);
    Item item = ItemFactory.getInstance().createItem("Dur", store, 40.0);

    CustomerSubscriptionService.instance().subscribeOnNewItemAppear(customer, store);

    CustomerSubscriptionService.instance().unsubscribeFromNewItemAppearUpdates(customer,
            store);

    boolean isFound = false;
    for ( Subscription s : CustomerSubscriptionService.instance()
            .getNewItemAppearSubscriptions() ) {
      if ( s.customer == customer && s.store == store ) {
        isFound = true;
      }
    }
    assertFalse(isFound);
    StoreManagementService.getInstance().addNewItem(store, item);
  }

  @Test
  public void testResetCustomerSubscriptions() {
    Customer customer = CustomerFactory.getInstance().createCustomer("Boris");
    Store store = StoreFactory.getInstance().createStore("ABC", StoreType.GAME_STORE);
    Item item = ItemFactory.getInstance().createItem("Dur", store, 20.0);

    CustomerSubscriptionService.instance().subscribeOnNewItemAppear(customer, store);
    CustomerSubscriptionService.instance().subscribeOnDiscount(customer, store);

    CustomerSubscriptionService.instance().unsubscribeFromStoreUpdates(customer, store);

    boolean isFound = false;
    for ( Subscription s : CustomerSubscriptionService.instance().getAllSubscriptions()
            ) {
      if ( s.customer == customer && s.store == store ) {
        isFound = true;
      }
    }
    assertFalse(isFound);
    StoreManagementService.getInstance().addNewItem(store, item);
    StoreManagementService.getInstance().makeDiscount(item, DiscountType.Discount_50);
  }

}
