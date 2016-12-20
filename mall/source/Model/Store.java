package Model;

import Enums.StoreType;
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
public abstract class Store implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String name;
  @Enumerated
  private StoreType type;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "store", orphanRemoval = true)
  private Collection<Item> items = new ArrayList<>();
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "store", orphanRemoval = true)
  private Set<Subscription> subscriptions = new HashSet<>();
  @Transient
  private Set<Customer> currentCustomers = new HashSet<>();

  public Store() {
    setName("");
  }

  public Store( String name, StoreType type ) {
    super();
    setName(name);
    setType(type);
  }

  public Integer getId() {
    return id;
  }

  public void setId( Integer id ) {
    this.id = id;
  }

  public Collection<Item> getItems() {
    return items;
  }

  public void setItems( Collection<Item> items ) {
    this.items = items;
  }

  public Set<Customer> getCurrentCustomers() {
    return currentCustomers;
  }


  public String getName() {
    return name;
  }

  public void setName( String name ) {
    this.name = name;
  }


  public Set<Subscription> getSubscriptions() {
    return subscriptions;
  }

  public void setSubscriptions( Set<Subscription> subscriptions ) {
    this.subscriptions = subscriptions;
  }

  public void addSubscription( Subscription subscription ) {
    subscription.setStore(this);
  }

  public void removeSubscription( Customer customer, SubscriptionType type ) {
    subscriptions.removeIf(s -> s.getCustomer() == customer && s.getType() == type);
  }

  public void addItem( Item item ) {
    item.setStore(this);
  }

  public void removeItem( Item item ) {
    items.remove(item);
  }

  public StoreType getType() {
    return type;
  }

  public void setType( StoreType type ) {
    this.type = type;
  }

  public void addCustomer( Customer customer ) {
    currentCustomers.add(customer);
  }

  public void removeCustomer( Customer customer ) {
    currentCustomers.remove(customer);
  }

  @Override
  public boolean equals( Object obj ) {
    Store o = (Store) obj;
    if ( o == null ) return false;
    return o.getId() == getId();
  }

  @Override
  public int hashCode() {
    HashCodeBuilder hcb = new HashCodeBuilder();
    hcb.append(getId());
    hcb.append(getName());

    return hcb.toHashCode();
  }

}
