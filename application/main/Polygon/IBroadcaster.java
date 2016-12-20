import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Eiger on 5/19/16.
 */
public abstract class IBroadcaster {

  private Collection<IObserver> observers = new ArrayList();

  public void attach( IObserver observer ) {
    observers.add(observer);
  }

  public void detach( IObserver observer ) {
    observers.remove(observer);
  }

  protected void notifyObservers() {
    observers.forEach(IObserver::onNotify);
  }
}

interface IObserver {
  void onNotify();
}

