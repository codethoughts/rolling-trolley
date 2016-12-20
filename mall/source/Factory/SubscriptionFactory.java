package Factory;

import Enums.SubscriptionType;
import Model.Customer;
import Model.Store;
import Model.Subscription;

/**
 * Created by Eiger on 6/25/16.
 */
public class SubscriptionFactory {
  private static SubscriptionFactory instance = new SubscriptionFactory();

  private SubscriptionFactory() {

  }

  public static SubscriptionFactory getInstance() {
    return instance;
  }

  public Subscription createSubscription( SubscriptionType type, Customer customer, Store store ) {
    return new Subscription(type, customer, store);
  }

}
