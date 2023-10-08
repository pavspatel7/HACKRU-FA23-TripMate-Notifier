package transit.control;

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
		File file = new File("TransitData.txt");
		//file.createNewFile();
		try
		{
				Map<Integer, List<String>> dataMap = new HashMap<Integer, List<String>>();
				Scanner sc = new Scanner(file);
				while(sc.hasNext()) 
				{
					String dataLine = sc.nextLine();
					String[] inputs = dataLine.split(",");
					List<String> temp;
					System.out.println("RU HERE");
					dataLine = dataLine.substring(dataLine.indexOf(',')+1);
					if(dataMap.keySet().contains(inputs[0].trim())) {
						temp = new ArrayList<String>(dataMap.get(inputs[0].trim()));
					}
					else
						temp = new ArrayList<String>();
					
					temp.add(dataLine);
					dataMap.put(Integer.valueOf(inputs[0]), temp);
				}
				System.out.println(dataMap);
				data.dataMap = dataMap;
				sc.close();
		}
		catch(Exception e)
		{
	        e.printStackTrace();
	    }
		
		return data;
	}
	
	public Map<Integer, List<String>> getDataMap() {
		return this.dataMap;
	}
	
	public void setDataMap(Map<Integer, List<String>> dataMapIn) {
		this.dataMap = new HashMap<Integer, List<String>>(dataMapIn);
	}
}
