package Services;

import Enums.SubscriptionType;
import Factory.SubscriptionFactory;
import Interfaces.DiscountBroadcaster;
import Interfaces.NewItemBroadcaster;
import Model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import static Enums.SubscriptionType.Discount;
import static Enums.SubscriptionType.NewItem;

/**
 * Created by Eiger on 6/22/16.
 */
public class CustomerSubscriptionService implements NewItemBroadcaster, DiscountBroadcaster{
  private static CustomerSubscriptionService instance = new CustomerSubscriptionService();

  private CustomerSubscriptionService() {
    pool = new ArrayList<>();
  }

  public static CustomerSubscriptionService instance() {
    return instance;
  }

  private Collection<CustomerSubscriptionObserverAdapter> pool;

  public void broadcastNewItem( Item item ) {
    pool.forEach(s -> s.onItemNotify(item));
  }

  public void broadcastDiscount( Item item ) {
    pool.forEach(s -> s.onDiscountNotify(item));
  }

  public void registerObserver( CustomerSubscriptionObserverAdapter adapter ) {
    pool.add(adapter);
  }

  public void removeObserver( CustomerSubscriptionObserverAdapter adapter ) {
    pool.remove(adapter);
  }

  public Collection<Subscription> getDiscountSubscriptions() {
    return getAllSubscriptions().stream().filter(subscription ->
            subscription.type == Discount)
            .collect(Collectors.toList());
  }

  public Collection<Subscription> getNewItemAppearSubscriptions() {
    return getAllSubscriptions().stream().filter(subscription ->
            subscription.type == SubscriptionType.NewItem)
            .collect(Collectors.toList());
  }

  public Collection<Subscription> getAllSubscriptions() {
    Collection<Subscription> subscriptions = new ArrayList<>();
    ShoppingMallService.getInstance().getStores().forEach(store ->
            subscriptions.addAll(store.getSubscriptions()));
    return subscriptions;
  }

  void subscribeOnStoreUpdates( Customer customer, Store store ) {
    subscribe(customer, store, Discount);
    subscribe(customer, store, NewItem);
  }

  public void subscribeOnDiscount( Customer customer, Store store ) {
    subscribe(customer, store, Discount);
  }

  public void subscribeOnNewItemAppear( Customer customer, Store store ) {
    subscribe(customer, store, NewItem);
  }

  public void unsubscribeFromStoreUpdates( Customer customer, Store store ) {
    unsubscribe(customer, store, NewItem);
    unsubscribe(customer, store, Discount);
  }

  public void unsubscribeFromDiscountUpdates( Customer customer, Store store ) {
    unsubscribe(customer, store, Discount);
  }

  public void unsubscribeFromNewItemAppearUpdates( Customer customer, Store store ) {
    unsubscribe(customer, store, NewItem);
  }

  Collection<Subscription> getSubscriptions( Customer customer ) {
    return customer.getSubscriptions();
  }

  private void subscribe( Customer customer, Store store, SubscriptionType type ) {
    Subscription subscription =
            SubscriptionFactory.getInstance().createSubscription
                    (SubscriptionType.Discount, customer, store);
    ShoppingMallDAO.instance().save(subscription);
  }

  private void unsubscribe( Customer customer, Store store, SubscriptionType type ) {
    customer.removeSubscription(store, type);
    store.removeSubscription(customer, type);

    ShoppingMallDAO.instance().save(customer);
    ShoppingMallDAO.instance().save(store);
  }

}
