package TransitPanel.control;

import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

public class DepartureVisionController {

    @FXML
    private ListView<String> departurelist;
    private ObservableList<String> departures;
    
    
    @FXML
    private TextField departurename;

	public void start() {
		departures = FXCollections.observableArrayList();
		departurelist.setItems(departures);
		
		TransitData data = TransitData.getInstance();
		departures.addAll(data.getStations().stream().collect(Collectors.toList()));
		
		departurelist.setCellFactory(lv ->
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
                	setText(item);
            }
	    };
    	    
    	    cell.setOnMouseClicked(event ->
    	    {
    	        if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2)
    	        {
    	            try
    	            {
    	            	String aNm = departurelist.getSelectionModel().getSelectedItem();
    	            	FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Destination.fxml"));
    	                Parent destination = loader.load();
    	                DestinationController controller = loader.getController();
    	                Scene destinationScene = new Scene(destination);
    	                controller.start(aNm);
    	                Stage mainStage = (Stage) departurelist.getScene().getWindow();
    	                mainStage.setScene(destinationScene);
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