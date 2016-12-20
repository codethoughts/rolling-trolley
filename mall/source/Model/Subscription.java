package Model;

import Enums.SubscriptionType;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Subscription implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @Enumerated
  public SubscriptionType type;
  private Date createdAt;
  @ManyToOne(cascade = CascadeType.PERSIST)
  public Store store;
  @ManyToOne(cascade = CascadeType.PERSIST)
  public Customer customer;

  public Subscription( SubscriptionType type, Customer customer, Store store ) {
    setCreatedAt(new Date());
    setType(type);
    setCustomer(customer);
    setStore(store);
  }

  public Integer getId() {
    return id;
  }

  public void setId( Integer id ) {
    this.id = id;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public SubscriptionType getType() {
    return type;
  }

  public void setType( SubscriptionType type ) {
    this.type = type;
  }

  public Store getStore() {
    return store;
  }

  public void setStore( Store store ) {
    this.store = store;
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer( Customer customer ) {
    this.customer = customer;
  }

  public void setCreatedAt( Date createdAt ) {
    this.createdAt = createdAt;
  }

  @Override
  public boolean equals( Object obj ) {
    Subscription o = (Subscription) obj;
    if ( o == null ) return false;
    return (o.getStore() == getStore() &&
            o.getType() == getType() &&
            o.getCustomer() == getCustomer()) || o.getId() == getId();
  }

  @Override
  public int hashCode() {
    HashCodeBuilder hcb = new HashCodeBuilder();
    hcb.append(getId());
    hcb.append(getCreatedAt());
    hcb.append(getCustomer());
    hcb.append(getStore());
    hcb.append(getType());

    return hcb.toHashCode();
  }

}
