package Services;

import Model.Cart;
import Model.Item;
import Model.Receipt;

import java.util.Collection;

/**
 * Created by Eiger on 6/23/16.
 */
public class PaymentSystem {
  private static PaymentSystem instance = new PaymentSystem();
  
  public static PaymentSystem getInstance() {
    return instance;
  }
  
  private PaymentSystem() {
  }

  private Receipt generateReceipt( Cart cart ) {
    Receipt receipt = new Receipt(cart.getItems());
    receipt.setBuyer(cart.getOwner());
    return receipt;
  }

  private void saveReceipt( Receipt receipt ) {
    receipt.getBuyer().addPurchase(receipt);
  }

  public void buy( Cart cart ) {
    saveReceipt(generateReceipt(cart));
    reset(cart);
  }

  public Double calcPriceToPay( Collection<Item> items ) {
    Double toPay = 0.0;

    for ( Item item : items )
      toPay += item.calculateTotalPrice();

    return toPay;
  }

  public Double calcPriceNoDiscount( Collection<Item> items ) {
    Double toPay = 0.0;
    for ( Item item : items ) {
      toPay += item.getPrice();
    }
    return toPay;
  }

  public void reset( Cart cart ) {
    ShoppingMallService.getInstance().getCart(cart.getOwner());
  }

}
