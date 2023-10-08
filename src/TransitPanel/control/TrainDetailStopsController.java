package TransitPanel.control;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TrainDetailStopsController {

    @FXML
    private Text trainDetailHeader;
    
    @FXML
    private Button onBoard;

    @FXML
    private ListView<String> trainStopList;
    private ObservableList<String> stops;
    
    @FXML
    private ListView<String> timeList;
    private ObservableList<String> times;
    
    
	public void start(String depart, String dest) {
		// TODO Auto-generated method stub
		stops = FXCollections.observableArrayList();
		trainStopList.setItems(stops);
		
		times = FXCollections.observableArrayList();
		timeList.setItems(times);
		
		TransitData data = TransitData.getInstance();
		int trainId = data.getTrain(depart, dest);
		
		trainDetailHeader.setText("ETA for Train # "+trainId+" to "+dest);
		
		List<String> stopsTime = new ArrayList<String>(data.getStopsTime(trainId));
		for(String str : stopsTime) {
			String stop = str.split(",")[0];
			String time = str.split(",")[1];
			
			stops.add(stop);
			times.add(time);
		}
		
		onBoard.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	try
                {
                	FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/HomeAlert.fxml"));
                    Parent train = loader.load();
                    HomeAlertController controller = loader.getController();
                    Scene trainScene = new Scene(train);
                    controller.start(stopsTime, depart, dest);
                    Stage mainStage = (Stage) timeList.getScene().getWindow();
                    mainStage.setScene(trainScene);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
	}
}