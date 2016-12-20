package Model;

import Enums.SubscriptionType;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Eiger on 6/22/16.
 */
@Entity
public class Customer implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String name;
  @Transient
  private Cart cart;
  @OneToMany(cascade = CascadeType.ALL)
  private Collection<Receipt> purchases = new ArrayList<>();
  @OneToMany(orphanRemoval = true, mappedBy = "customer", cascade = CascadeType.ALL)
  private Set<Subscription> subscriptions = new HashSet<>();

  public Customer() {
    setName("");
  }

  public Customer( String name ) {
    super();
    this.name = name;
  }

  public Integer getId() {
    return id;
  }

  public void setId( Integer id ) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName( String name ) {
    this.name = name;
  }

  public Cart getCart() {
    return cart;
  }

  public void setCart( Cart cart ) {
    this.cart = cart;
  }

  public Collection<Receipt> getPurchases() {
    return purchases;
  }

  public void setPurchases( Collection<Receipt> purchases ) {
    this.purchases = purchases;
  }

  public Set<Subscription> getSubscriptions() {
    return subscriptions;
  }

  public void setSubscriptions( Set<Subscription> subscriptions ) {
    this.subscriptions = subscriptions;
  }

  public void addSubscription( Subscription subscription ) {
    subscription.setCustomer(this);
    subscriptions.add(subscription);
  }

  public void removeSubscription( Subscription subscription ) {
    subscriptions.remove(subscription);
  }

  public void removeSubscription( Store store, SubscriptionType type ) {
    subscriptions.removeIf(s -> s.getStore() == store && s.getType() == type);
  }

  public void addPurchase( Receipt receipt ) {
    receipt.setBuyer(this);
    purchases.add(receipt);
  }

  public void removePurchase( Receipt receipt ) {
    purchases.remove(receipt);
  }

  @Override
  public boolean equals( Object obj ) {
    Customer o = (Customer) obj;
    if ( o == null ) return false;
    return o.getName() == getName() || o.getId() == getId();
  }

  @Override
  public int hashCode() {
    HashCodeBuilder hcb = new HashCodeBuilder();
    hcb.append(getId());
    hcb.append(getName());
    return hcb.toHashCode();
  }
}
