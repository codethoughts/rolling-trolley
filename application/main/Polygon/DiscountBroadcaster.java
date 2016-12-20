
public class DiscountBroadcaster extends IBroadcaster {
  void changePrice() {
    notifyObservers();
  }
}
