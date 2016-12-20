package Services;

import Enums.DiscountType;
import Model.Customer;
import Model.Item;
import Model.Store;

import java.util.Collection;

/**
 * Created by Eiger on 6/22/16.
 */
public class StoreManagementService {

  private static StoreManagementService instance = new StoreManagementService();

  public static StoreManagementService getInstance() {
    return instance;
  }

  private StoreManagementService() {

  }

  public void addNewItem( Store store, Item item ) {
    store.addItem(item);
    CustomerSubscriptionService.instance().broadcastNewItem(item);
    ShoppingMallDAO.instance().save(store);
  }

  public void removeItem( Item item ) {
    if ( item.getStore() == null ) return;
    item.getStore().removeItem(item);
    ShoppingMallDAO.instance().save(item.getStore());
  }

  public Collection<Item> getItems( Store store ) {
    return store.getItems();
  }

  public void makeDiscount( Item item, DiscountType type ) {
    item.setDiscount(type);
    CustomerSubscriptionService.instance().broadcastDiscount(item);
    ShoppingMallDAO.instance().save(item);
  }

  Collection<Customer> getCurrentCustomers( Store store ) {
    return store.getCurrentCustomers();
  }

  public void enter( Store store, Customer customer ) {
    store.addCustomer(customer);
  }

  public void exit( Store store, Customer customer ) {
    store.removeCustomer(customer);
  }
}

