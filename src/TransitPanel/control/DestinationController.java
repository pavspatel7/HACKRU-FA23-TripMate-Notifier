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

public class DestinationController {

    @FXML
    private ListView<String> destinationList;

    @FXML
    private TextField destinationName;
    private ObservableList<String> destinations;
    

	public void start(String departStation) {
		destinations = FXCollections.observableArrayList();
		destinationList.setItems(destinations);
		
		
		
		TransitData data = TransitData.getInstance();
		destinations.addAll(data.getDestinations(departStation).stream().collect(Collectors.toList()));
		
		destinationList.setCellFactory(lv ->
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
		            	String destinationStation = destinationList.getSelectionModel().getSelectedItem();
		            	FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Train.fxml"));
		                Parent train = loader.load();
		                TrainController controller = loader.getController();
		                Scene trainScene = new Scene(train);
		                controller.start(departStation, destinationStation);
		                Stage mainStage = (Stage) destinationList.getScene().getWindow();
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