package Services;

import Model.*;

import java.util.Collection;

/**
 * Created by Eiger on 6/23/16.
 */
public class ShoppingMallService {
  private ShoppingMallService() {

  }

  private static ShoppingMallService instance = new ShoppingMallService();

  public static ShoppingMallService getInstance() {
    return instance;
  }

  public Integer registerNewStore( Store store ) {
    ShoppingMallDAO.instance().save(store);
    return store.getId();
  }

  public void deleteStore( String id ) {
    ShoppingMallDAO.instance().delete(id);
  }

  public void enter( Customer customer ) {
    ShoppingMall.getInstance().addCustomer(customer);
  }

  public void exit( Customer customer ) {
    ShoppingMall.getInstance().removeCustomer(customer);
  }

  public Store searchStore( Integer id ) {
    return (Store) ShoppingMallDAO.instance().search(Store.class);
  }

  public Collection<Store> getStores() {
    return ShoppingMallDAO.instance().search(Store.class);
  }

  public Customer searchCustomer( Integer id ) {
    return (Customer) ShoppingMallDAO.instance().search(id, Customer.class);
  }

  public Customer searchCustomer( String name ) {
    Customer customer = null;

    Collection<Customer> customers = ShoppingMallDAO.instance().search(Customer.class);

    for ( Customer c : customers ) {
      if ( c.getName() == name ) {
        customer = c;
      }
    }

    return customer;
  }

  public Collection<Customer> getCustomers() {
    return ShoppingMall.getInstance().getCustomers();
  }

  public void getCart( Customer customer ) {
    customer.setCart(new Cart(customer));
  }

  public void addToCart( Cart cart, Item item ) {
    cart.addItem(item);
  }

  public void removeFromCart( Cart cart, Item item ) {
    cart.removeItem(item);
  }

  public void undoItemInCart( Cart cart ) {
    cart.undo();
  }

  public void redoItemInCart( Cart cart ) {
    cart.redo();
  }

  public Integer registerNewCustomer( Customer customer ) {
    ShoppingMallDAO.instance().save(customer);
    return customer.getId();
  }


}
