package TransitPanel.control;

import java.io.*;
import java.util.*;

public class TransitData {
	
	private static TransitData instance = null;
	
	private Map<Integer, List<String>> dataMap;
	private Set<String> stations = new HashSet<>();
	
	public Set<String> getStations(){
		return stations;
	}
	
	public Set<String> getDestinations(String departureStation){
		Map<Integer, List<String>> dataMap = this.getDataMap();
		Set<String> destinations = new HashSet<>();
		for(Map.Entry<Integer, List<String>> routes : dataMap.entrySet()) {
			if(routes.getValue().contains(departureStation))
				destinations.addAll(routes.getValue());
		}
		return destinations;
	}
	
	public static TransitData getInstance()
	{
		if(instance == null)
		{
			instance = new TransitData();
		}
		return instance;
	}
	
	public static TransitData readFromAFile() throws IOException
	{
		TransitData data = TransitData.getInstance();
		File file = new File("src/TransitPanel/control/TransitDataMy.txt");
		file.createNewFile();
		Map<Integer, List<String>> dataMap = new HashMap<Integer, List<String>>();
		Set<String> stations = new HashSet<String>();
		Scanner sc = new Scanner(file);
		while(sc.hasNextLine()) 
		{
			String dataLine = sc.nextLine();
			String[] inputs = dataLine.split(",");
			List<String> temp;
			dataLine = dataLine.substring(dataLine.indexOf(',')+1);
			stations.add(inputs[2]);
			if(dataMap.keySet().contains(Integer.valueOf(inputs[0].trim()))) {
				temp = new ArrayList<String>(dataMap.get(Integer.valueOf(inputs[0].trim())));
			}
			else
				temp = new ArrayList<String>();
			
			temp.add(dataLine);
			dataMap.put(Integer.valueOf(inputs[0].trim()), temp);
		}
		data.dataMap = dataMap;
		System.out.println(dataMap);
		data.stations = stations;
		sc.close();
		return data;
	}
	
	public Map<Integer, List<String>> getDataMap() {
		return this.dataMap;
	}
	
	public void setDataMap(Map<Integer, List<String>> dataMapIn) {
		this.dataMap = new HashMap<Integer, List<String>>(dataMapIn);
	}
}
