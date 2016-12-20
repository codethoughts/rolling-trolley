/**
 * Created by Eiger on 6/27/16.
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

public class CustomerDashboard {


  static Scene window;

  @FXML
  TableView<MyStore> storesTableView = new TableView<>();

  @FXML
  Label visitorCountLabel = new Label();

//  public void start() throws IOException {
//    Parent root = null;
//    window = new Scene(Login.rootLayout);
//    root = FXMLLoader.load(getClass().getResource("./customer_dashboard.fxml"));
//    Login..setScene(new Scene(root));
//
//    TableColumn<MyStore, String> nameColumn = new TableColumn<>("Store");
//    nameColumn.setMinWidth(100);
//    nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
//
//    TableColumn<MyStore, String> categoryColumn = new TableColumn<>("Category");
//    categoryColumn.setMinWidth(100);
//    categoryColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
//
////    TableColumn<MyStore, Integer> itemsColumn = new TableColumn<>("Items");
////    itemsColumn.setMinWidth(100);
////    itemsColumn.setCellValueFactory(new PropertyValueFactory<>("items"));
////
////    TableColumn<MyStore, Integer> visitorsColumn = new TableColumn<>("Visitors");
////    visitorsColumn.setMinWidth(100);
////    visitorsColumn.setCellValueFactory(new PropertyValueFactory<>("visitors"));
////
////    TableColumn<MyStore, CheckBox> discountColumn = new TableColumn<>("Discount");
////    discountColumn.setMinWidth(100);
////    discountColumn.setCellValueFactory(new PropertyValueFactory<>("discount"));
////
////    TableColumn<MyStore, CheckBox> newItemColumn = new TableColumn<>("New Item");
////    newItemColumn.setMinWidth(100);
////
//    storesTableView.setItems(getStores());
//    window.show();
//  }

  public ObservableList<MyStore> getStores() {
    ObservableList<MyStore> stores = FXCollections.observableArrayList();
    stores.addAll(ShoppingMallService.getInstance().getStores());
    return stores;
  }

  public void onLogoutPressed() {
    visitorCountLabel.setText(ShoppingMallService.getInstance().getVisitorCount().toString());
    ShoppingMallService.getInstance().exit(Session.getInstance().customer);

  }

}
