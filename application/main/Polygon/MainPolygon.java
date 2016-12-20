/**
 * Created by Eiger on 5/19/16.
 */
public class MainPolygon {
  public static void main( String args[] ) {
    DiscountBroadcaster discountBroadcaster = new DiscountBroadcaster();
    CustomerPriceNotification listener = new CustomerPriceNotification();

    discountBroadcaster.attach(listener);

    discountBroadcaster.changePrice();
    discountBroadcaster.detach(listener);
    discountBroadcaster.changePrice();
  }
}
