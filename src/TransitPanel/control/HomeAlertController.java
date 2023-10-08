package TransitPanel.control;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class HomeAlertController {

	@FXML
    private HBox alertPanel;

    
    @FXML
    private Text eta;

    @FXML
    private Text lastStop;

    @FXML
    private Text nextStop;

    @FXML
    private Text time;

    @FXML
    private Text trainNo;
    
    private ScheduledExecutorService executorService; // Declare the executorService at the class level
    

	public void start(List<String> stopsTime, String depart, String dest) {
		LocalTime currentTime = LocalTime.now();
		time.setText(currentTime.format(DateTimeFormatter.ofPattern("hh:mm")));
		TransitData data = TransitData.getInstance();
		
		int trainId = data.getTrain(depart, dest);
		trainNo.setText(trainNo.getText()+" "+trainId);
		lastStop.setText(lastStop.getText()+" "+dest);
		
		int timeInterval = 3;
		
		List<String> temp = new ArrayList<>(stopsTime);
		for(String str : stopsTime) {
			if(str.contains(depart))
				break;
			temp.remove(str);
		}
		executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(() -> updateTextField(temp, trainId, depart), 0, timeInterval, TimeUnit.SECONDS);
    }

    private void updateTextField(List<String> dataList, int trainId, String depart) {
        String nextValue = dataList.remove(1).split(",")[0];

        Platform.runLater(() -> nextStop.setText(nextStop.getText().split(":")[0]+": "+nextValue));
        String dest = lastStop.getText().split(":")[1].trim();
        TransitData data = TransitData.getInstance();
        ArrayList<String> res = data.getETA(trainId, nextValue, dest);
		String ETA = res.get(2);
		eta.setText(eta.getText().split(":")[0]+": "+ ETA);
		
        
        if (dest.equals(nextValue)) {
            executorService.shutdown();
        }
    }
}
    