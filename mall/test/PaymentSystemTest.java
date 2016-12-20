import Enums.DiscountType;
import Enums.StoreType;
import Factory.CustomerFactory;
import Factory.ItemFactory;
import Factory.StoreFactory;
import Model.Customer;
import Model.Item;
import Model.Store;
import Services.PaymentSystem;
import Services.ShoppingMallService;
import Services.StoreManagementService;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Eiger on 6/23/16.
 */
public class PaymentSystemTest {

  @Test
  public void testBuy() throws Exception {
    Store store = StoreFactory.getInstance().createStore("ABC", StoreType.BOOK_STORE);
    Item book = ItemFactory.getInstance().createItem("War & Peace", store, 10.90);
    StoreManagementService.getInstance().addNewItem(store, book);

    Customer customer = CustomerFactory.getInstance().createCustomer("Boris");
    ShoppingMallService.getInstance().getCart(customer);

    ShoppingMallService.getInstance().addToCart(customer.getCart(), book);

    PaymentSystem.getInstance().buy(customer.getCart());

    ShoppingMallService.getInstance().registerNewCustomer(customer);
    ShoppingMallService.getInstance().registerNewStore(store);

    assertEquals(1, customer.getPurchases().size());
  }

  @Test
  public void testCalcPriceToPay() throws Exception {
    Store store = StoreFactory.getInstance().createStore("ABC", StoreType.BOOK_STORE);
    Item book = ItemFactory.getInstance().createItem("War & Peace", store, 30.00);
    Item book1 = ItemFactory.getInstance().createItem("War", store, 100.00);
    StoreManagementService.getInstance().makeDiscount(book1, DiscountType.Discount_30);

    StoreManagementService.getInstance().addNewItem(store, book);
    StoreManagementService.getInstance().addNewItem(store, book1);

    Customer customer = CustomerFactory.getInstance().createCustomer("Boris");
    ShoppingMallService.getInstance().getCart(customer);

    ShoppingMallService.getInstance().addToCart(customer.getCart(), book);
    ShoppingMallService.getInstance().addToCart(customer.getCart(), book1);

    Double total =
            PaymentSystem.getInstance().calcPriceToPay(customer.getCart().getItems());

    Double _100 = 100.0;

    assertEquals(_100, total);

  }

  @Test
  public void testCalcPriceNoDiscount() throws Exception {

  }
}