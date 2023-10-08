package TransitPanel.control;

import java.io.*;
import java.util.*;

public class TransitData {
	
	private static TransitData instance = null;
	
	private Map<Integer, List<String>> dataMap;
	private List<String> stations;
	
	
	
	
	public List<String> getStations(){
		return stations;
	}
	
	public List<String> getDestinations(String departureStation){
		Map<Integer, List<String>> dataMap = this.getDataMap();
		List<String> destinations = new ArrayList<>();
		
		for(Map.Entry<Integer, List<String>> routes : dataMap.entrySet()) {
			if(routes.getValue().toString().contains(departureStation)) {
				boolean startHere = false;
				for(String str : routes.getValue()) {
					if(startHere)
						destinations.add(str.split(",")[1]);
					if(str.contains(departureStation))
						startHere = true;
				}
			}
		}
		return destinations;
	}
	
	public int getTrain(String depart, String dest) {
		Map<Integer, List<String>> dataMap = this.getDataMap();
		
		for(Map.Entry<Integer, List<String>> routes : dataMap.entrySet()) {
			if((routes.getValue().toString().contains(depart) && routes.getValue().toString().contains(dest)) 
					&& (routes.getValue().toString().indexOf(depart) < routes.getValue().toString().indexOf(dest))) {
				return routes.getKey();
			}
		}
		return 0;
	}
	
	public List<String> getStopsTime(int trainId){
		List<String> stopsTime = new ArrayList<>();
		TransitData data = TransitData.getInstance();
		
		for(String str : data.getDataMap().get(trainId)) {
			stopsTime.add(str.split(",")[1]+","+str.split(",")[2]);
		}
		return stopsTime;
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
		File file = new File("src/TransitPanel/control/TransitData.txt");
		file.createNewFile();
		Map<Integer, List<String>> dataMap = new HashMap<Integer, List<String>>();
		List<String> stations = new ArrayList<String>();
		Scanner sc = new Scanner(file);
		while(sc.hasNextLine()) 
		{
			String dataLine = sc.nextLine();
			String[] inputs = dataLine.split(",");
			List<String> temp;
			dataLine = dataLine.substring(dataLine.indexOf(',')+1);
			if(!stations.contains(inputs[2]))
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
	
	public ArrayList<String> getETA(int trainId, String depart, String dest) {
		String i = "", f = "";
		Map<Integer, List<String>> hm = this.getDataMap();
		List<String> l = new ArrayList<>(hm.get(trainId));
		for(String s: l)
		{
			if(s.contains(depart))
				i  = (s.substring(s.length()-5));
			if(s.contains(dest))
				f = (s.substring(s.length()-5));
		}
		
		ArrayList<String> res = new ArrayList<String>();
		res.add(i);
		res.add(f);
		
		String[] time1Parts = i.split(":");
	    String[] time2Parts = f.split(":");
	    int hour1 = Integer.parseInt(time1Parts[0]);
        int minute1 = Integer.parseInt(time1Parts[1]);
        int hour2 = Integer.parseInt(time2Parts[0]);
        int minute2 = Integer.parseInt(time2Parts[1]);
        int diff = ((hour2*60) + minute2) - ((hour1*60) + minute1);
        int xhr = diff/60;
        int xmin = diff%60;
        String h = ((xhr<10)?("0"+String.valueOf(xhr)):(String.valueOf(xhr))) + ":" + ((xmin<10)?("0"+String.valueOf(xmin)):(String.valueOf(xmin)));
		res.add(h);
		return res;
	}
}
