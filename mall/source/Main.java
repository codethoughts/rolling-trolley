import Enums.DiscountType;
import Enums.StoreType;
import Enums.SubscriptionType;
import Model.Customer;
import Model.CustomerSubscriptionObserverAdapter;
import Model.Item;
import Price.*;
import Services.CustomerSubscriptionService;
import Services.ShoppingMallDAO;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

class Session {
  private static Session instance = new Session();

  private Session() {

  }

  public static Session getInstance() {
    return instance;
  }

  public MyCustomer customer;
}

@Entity
class MyStore implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  String name;
  @Enumerated
  StoreType type;
  @OneToMany(orphanRemoval = true, mappedBy = "store", cascade = CascadeType.ALL)
  Set<MySubscription> subscriptions = new HashSet<>();
  @OneToMany(orphanRemoval = true, mappedBy = "store", cascade = CascadeType.ALL)
  Collection<MyItem> items = new ArrayList<>();
  @Transient
  Collection<MyCustomer> visitors = new ArrayList<>();

  MyStore( String name ) {
    this.name = name;
  }

}

@Entity
class MyItem implements Serializable, Cloneable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  String name;
  Double price = 0.0;
  @ManyToOne(cascade = CascadeType.PERSIST)
  MyStore store;
  @Enumerated
  DiscountType discount;

  MyItem( String name ) {
    this.name = name;
    discount = DiscountType.None;
  }

}

@Entity
class MyCustomer implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  String name;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "buyer", orphanRemoval = true)
  Collection<MyReceipt> history = new ArrayList<>();
  @Transient
  MyCart cart;
  @Transient
  Boolean isOnline = false;

  MyCustomer( String name ) {
    this.name = name;
    cart = new MyCart(this);
  }

  @OneToMany(orphanRemoval = true, mappedBy = "customer", cascade = CascadeType.ALL)
  Set<MySubscription> subscriptions = new HashSet<>();

  @Override
  public String toString() {
    return name;
  }

  @Override
  public boolean equals( Object obj ) {
    if ( obj == null ) return false;
    MyCustomer c = (MyCustomer) obj;
    return c.name == name || c.id == id;
  }
}

class MyCart {
  MyCustomer owner;
  Collection<MyItem> items = new ArrayList<>();
  CommandStack commands = new CommandStack();

  MyCart( MyCustomer owner ) {
    this.owner = owner;
  }

  void add( MyItem item ) {
    AddItemToCart action = new AddItemToCart(this, item);
    commands.execute(action);
  }

  void remove( MyItem item ) {
    RemoveItemFromCart action = new RemoveItemFromCart(this, item);
    commands.execute(action);
  }

  void undo() {
    commands.undo();
  }

  void redo() {
    commands.redo();
  }

}

class AddItemToCart implements Command {
  MyCart cart;
  MyItem item;

  AddItemToCart( MyCart cart, MyItem item ) {
    this.cart = cart;
    this.item = item;
  }

  @Override
  public void execute() {
    cart.items.add(item);
  }

  @Override
  public void undo() {
    cart.items.remove(item);
  }

  @Override
  public void redo() {
    cart.items.add(item);
  }
}

class RemoveItemFromCart implements Command {
  MyCart cart;
  MyItem item;

  RemoveItemFromCart( MyCart cart, MyItem item ) {
    this.cart = cart;
    this.item = item;
  }

  @Override
  public void execute() {
    cart.items.remove(item);
  }

  @Override
  public void undo() {
    cart.items.add(item);
  }

  @Override
  public void redo() {
    cart.items.remove(item);
  }
}

@Entity
class MySubscription implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @ManyToOne(cascade = CascadeType.PERSIST)
  MyCustomer customer;
  @ManyToOne(cascade = CascadeType.PERSIST)
  MyStore store;
  @Enumerated
  SubscriptionType type;

  @Override
  public boolean equals( Object obj ) {
    if ( obj == null ) return false;
    MySubscription s = (MySubscription) obj;
    return (s.store == store && s.customer == customer && s.type == type) || s.id == id;
  }
}

@Entity
class PurchasedItem implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  String item_name;
  Double item_price;
  String store_name;
  @Enumerated
  StoreType type;
  @ManyToOne(cascade = CascadeType.PERSIST)
  MyReceipt receipt;

  PurchasedItem( MyItem item, MyReceipt receipt ) {
    this.item_name = item.name;
    this.item_price = PaymentSystem.getInstance().calculateTotalPrice(item);
    this.store_name = item.store.name;
    this.type = item.store.type;
    this.receipt = receipt;
  }

}

@Entity
class MyReceipt implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "receipt")
  Collection<PurchasedItem> items = new ArrayList<>();
  @ManyToOne(cascade = CascadeType.PERSIST)
  MyCustomer buyer;
  Date date;

  MyReceipt( Collection<MyItem> items, MyCustomer buyer ) {
    date = new Date();
    items.forEach(item -> {
      this.items.add(new PurchasedItem(item, this));
    });
    this.buyer = buyer;
  }
}

class CustomerSubscriptionServiceProvider {
  private static CustomerSubscriptionServiceProvider instance = new CustomerSubscriptionServiceProvider();

  private CustomerSubscriptionServiceProvider() {
  }

  public static CustomerSubscriptionServiceProvider getInstance() {
    return instance;
  }

  public void subscribe( MyCustomer customer, MyStore store, SubscriptionType type ) {
    MySubscription subscription = new MySubscription();
    subscription.customer = customer;
    subscription.store = store;
    subscription.type = type;
    customer.subscriptions.add(subscription);
    ShoppingMallDAO.instance().save(customer);
    ShoppingMallDAO.instance().save(store);
  }

  public void unsubscribe( MyCustomer customer, MyStore store, SubscriptionType type ) {
    customer.subscriptions.removeIf(s -> s.store == store && s.type == type);
    store.subscriptions.removeIf(s -> s.customer == customer && s.type == type);
    ShoppingMallDAO.instance().save(customer);
    ShoppingMallDAO.instance().save(store);
  }
}

class CustomerWindowDashboard extends CustomerSubscriptionObserverAdapter {

  CustomerWindowDashboard( Customer customer ) {
    super(customer);
  }

  @Override
  public void onDiscountNotify( Item item ) {
    Dialog.show(item);
  }

  @Override
  public void onItemNotify( Item item ) {
    Dialog.show(item);
  }
}

class Dialog {
  static void show(Item item) {

  }
}

class StoreManagementService {
  private static StoreManagementService instance = new StoreManagementService();

  private StoreManagementService() {
  }

  public static StoreManagementService getInstance() {
    return instance;
  }

  public void enter( MyCustomer customer, MyStore store ) {
    ShoppingMallService.getInstance().getStores().forEach(s -> {
      s.visitors.remove(customer);
    });
    store.visitors.add(customer);
  }

  public void exit( MyCustomer customer, MyStore store ) {
    store.visitors.remove(customer);
  }

  public void addItem( MyItem item, MyStore store ) {
    item.store = store;
    store.items.add(item);
    ShoppingMallDAO.instance().save(store);
    ShoppingMallDAO.instance().save(item);
  }

  public void removeItem( MyItem item, MyStore store ) {
    store.items.remove(item);
    ShoppingMallDAO.instance().save(store);
  }

}

class MyMall {
  private static MyMall instance = new MyMall();

  Set<MyCustomer> customers = new HashSet<>();

  private MyMall() {

  }

  public static MyMall getInstance() {
    return instance;
  }
}

class ShoppingMallService {
  private static ShoppingMallService instance = new ShoppingMallService();

  private ShoppingMallService() {
  }

  public static ShoppingMallService getInstance() {
    return instance;
  }

  public void enter( MyCustomer customer ) {
    customer.isOnline = true;
    Session.getInstance().customer = customer;
    MyMall.getInstance().customers.add(customer);
  }

  public void exit( MyCustomer customer ) {
    customer.isOnline = false;
    Session.getInstance().customer = null;
    MyMall.getInstance().customers.remove(customer);
  }

  public Collection<MyStore> getStores() {
    return ShoppingMallDAO.instance().search(MyStore.class);
  }

  public void addItemToCart( MyItem item, MyCart cart ) {
    cart.add(item);
  }

  public void removeItemFromCart( MyItem item, MyCart cart ) {
    cart.remove(item);
  }

  public void undoCartChange( MyCart cart ) {
    cart.undo();
  }

  public void redoCartUndo( MyCart cart ) {
    cart.redo();
  }

  public MyCustomer searchCustomer( String name ) {
    MyCustomer customer = null;

    Collection<MyCustomer> result =
            (Collection<MyCustomer>) ShoppingMallDAO.instance().search(MyCustomer.class);

    for ( MyCustomer c : result ) {
      if ( c.name == name ) {
        customer = c;
        break;
      }
    }

    return customer;
  }

  public void register( MyCustomer customer ) {
    ShoppingMallDAO.instance().save(customer);
  }

  public void register( MyStore store ) {
    ShoppingMallDAO.instance().save(store);
  }

  public Integer getVisitorCount() {
    return MyMall.getInstance().customers.size();
  }
}

class PaymentSystem {
  private static PaymentSystem instance = new PaymentSystem();

  private PaymentSystem() {

  }

  public static PaymentSystem getInstance() {
    return instance;
  }

  public Collection<MyReceipt> customerHistory( MyCustomer customer ) {
    return customer.history;
  }

  public void purchase( MyCart cart ) {
    if ( cart.items.isEmpty() ) return;
    MyReceipt receipt = generateReceipt(cart.items, cart.owner);
    cart.owner.history.add(receipt);
    ShoppingMallDAO.instance().save(cart.owner);
  }

  private MyReceipt generateReceipt( Collection<MyItem> items, MyCustomer buyer ) {
    return new MyReceipt(items, buyer);
  }

  public static Double calculateTotalPrice( MyItem item ) {
    switch ( item.discount ) {
      case Discount_10:
        return new Discount10().calculatePrice(item.price);
      case Discount_30:
        return new Discount30().calculatePrice(item.price);
      case Discount_50:
        return new Discount50().calculatePrice(item.price);
      case Discount_70:
        return new Discount70().calculatePrice(item.price);
      default:
        return new Original().calculatePrice(item.price);

    }
  }

}

class CommandStack {
  LinkedList<Command> commandStack = new LinkedList<>();
  LinkedList<Command> redoStack = new LinkedList<>();

  void execute( Command command ) {
    command.execute();
    commandStack.addFirst(command);
    redoStack.clear();
  }

  void undo() {
    if ( commandStack.isEmpty() ) return;
    Command command = commandStack.removeFirst();
    command.undo();
    redoStack.addFirst(command);
  }

  void redo() {
    if ( redoStack.isEmpty() ) return;
    Command command = redoStack.removeFirst();
    command.redo();
    commandStack.addFirst(command);
  }
}

interface Command {
  void execute();

  void undo();

  void redo();
}

public class Main {

  public static void main( String args[] ) {
//
//    MyCustomer customer = new MyCustomer("Dmitry");
//    MyStore store = new MyStore("ABC");
//    MyItem item = new MyItem("Durex");
//
//    ShoppingMallService.getInstance().register(customer);
//    ShoppingMallService.getInstance().register(store);
//
//    CustomerSubscriptionServiceProvider.getInstance().subscribe(customer, store, SubscriptionType.Discount);
//    CustomerSubscriptionServiceProvider.getInstance().unsubscribe(customer, store, SubscriptionType.Discount);
//
//    System.out.println(ShoppingMallService.getInstance().searchCustomer("Dmitry"));
//
//    StoreManagementService.getInstance().addItem(item, store);
//
//    ShoppingMallService.getInstance().addItemToCart(item, customer.cart);
////    ShoppingMallService.getInstance().undoCartChange(customer.cart);
//    PaymentSystem.getInstance().purchase(customer.cart);

    Customer customer = new Customer();

    CustomerWindowDashboard dashboard = new CustomerWindowDashboard(customer);
    CustomerSubscriptionService.instance().registerObserver(dashboard);


//    StoreManagementService.getInstance().removeItem(item, store);

//    ShoppingMallDAO.instance().save(customer);
//    ShoppingMallDAO.instance().save(store);

//    store.subscriptions.remove(subscription);
//    customer.subscriptions.remove(subscription);
//
//    ShoppingMallDAO.instance().save(customer);
//    ShoppingMallDAO.instance().save(store);

//    Store store = StoreFactory.getInstance().createStore("ABC", StoreType.SHOE_STORE);
//    Customer customer = CustomerFactory.getInstance().createCustomer("Dmitry");
//
//    ShoppingMallService.getInstance().registerNewCustomer(customer);
//    ShoppingMallService.getInstance().registerNewStore(store);
//
////    Customer me = ShoppingMallService.getInstance().searchCustomer("Dmitry");
//
//    CustomerSubscriptionService.instance().subscribeOnDiscount(customer, store);
//    CustomerSubscriptionService.instance().unsubscribeFromStoreUpdates(customer, store);

  }
}