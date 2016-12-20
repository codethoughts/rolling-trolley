package Model;

import Interfaces.IDiscountObserver;
import Interfaces.INewItemObserver;

/**
 * Created by Eiger on 6/25/16.
 */
public abstract class CustomerSubscriptionObserverAdapter implements INewItemObserver,
        IDiscountObserver {

  Customer customer;

  protected CustomerSubscriptionObserverAdapter( Customer customer ) {
    this.customer = customer;
  }

  public abstract void onDiscountNotify( Item item );

  public abstract void onItemNotify( Item item );
}
