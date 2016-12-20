import TransferObjects.Customer;

public class CustomerPriceNotification implements IObserver {

  void subscribeToService( Customer customer) {

  }

  void unsubscribeFromService(Customer customer) {

  }

  @Override
  public void onNotify() {
    System.out.println("Sending notification to customer about price change");
  }
}
