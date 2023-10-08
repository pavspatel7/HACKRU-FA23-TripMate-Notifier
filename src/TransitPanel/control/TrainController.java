package TransitPanel.control;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TrainController {

    @FXML
    private ListView<String> trainList;
    private ObservableList<String> trains;

    @FXML
    private Text transfer;

    @FXML
    private Text travelTime;
    
    public void start(String depart, String dest)
    {
    	trains = FXCollections.observableArrayList();
		trainList.setItems(trains);
    	
    	TransitData data = TransitData.getInstance();
    	int trainId = data.getTrain(depart, dest);
    	trains.add(String.valueOf(trainId));
    	ArrayList<String> res = data.getETA(trainId, depart, dest);
    	String departTime = res.get(0);
    	String destTime = res.get(1);
    	travelTime.setText("Travel Time: " + res.get(2));
    	
    	trainList.setCellFactory(lv ->
		{
    	ListCell<String> cell = new ListCell<>()
	    {
    	protected void updateItem(String item, boolean empty)
        {
    		super.updateItem(item, empty);
            if(empty || item == null)
            {
                setText(null);
                setGraphic(null);
            }
            else
            {
                VBox v1 = new VBox();
                Label l1 = new Label(departTime);
                Label l3 = new Label("#"+String.valueOf(trainId));
                Label l2 = new Label(destTime);
                v1.getChildren().addAll(l1, l3, l2);
                
                VBox v2 = new VBox();
                Label l4 = new Label("|");
                Label l5 = new Label("|");
                Label l6 = new Label("|");
                v2.getChildren().addAll(l4, l5, l6);
                
                VBox v3 = new VBox();  
                Label l7 = new Label(depart);
                Label l8 = new Label(" ");
                Label l9 = new Label(dest);
                v3.getChildren().addAll(l7, l8, l9);
                
                v1.setPadding(new Insets(10, 10, 10, 10));
                v2.setPadding(new Insets(10, 10, 10, 10));
                v3.setPadding(new Insets(10, 10, 10, 10));
                
                HBox h1 = new HBox();
                h1.getChildren().addAll(v1, v2, v3);
                setGraphic(h1);
            }
        }
    };
	    
	    cell.setOnMouseClicked(event ->
	    {
	        if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2)
	        {
	            try
	            {
	            	//String train = trainList.getSelectionModel().getSelectedItem();
	            	FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/TrainDetailStops.fxml"));
	                Parent train = loader.load();
	                TrainDetailStopsController controller = loader.getController();
	                Scene trainScene = new Scene(train);
	                controller.start(depart, dest);
	                Stage mainStage = (Stage) trainList.getScene().getWindow();
	                mainStage.setScene(trainScene);
	            }
	            catch (Exception e)
	            {
	                e.printStackTrace();
	            }
	        }
	    });
	    return cell;
	});
    	
    }

}