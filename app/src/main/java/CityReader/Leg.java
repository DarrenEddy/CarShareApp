package CityReader;

import java.util.ArrayList;

//idea is to manage the trip as an array list of stops so {Toronto, Kingston, Montreal} would be two trips one 
//Toronto To Kingston and another Kingston to Montreal

public class Leg {
    ArrayList<City> places;
    ArrayList<Boolean> commited;
    
    public Leg(City from, City to)
    {
        places = new ArrayList<>();
        commited= new ArrayList<>();
        
        places.add(from);
        places.add(to);
        
        commited.add(false);
    }
    
    public City[] toFrom() 
    {
    	return new City[] {places.get(0),places.get(places.size()-1)};
    }

    public void add(City stop) 
    {
    	//stops need to be added in order they would be on the route
    	//Going to need a lot of work -- Q_Q
    	
    	
    	//Currently finds shortest crows flight distances for all possible placements of stops while maintaining Start and end
    	//if one out of the way is given can get weird as roads do not follow crows flight
    	
    	// O(n^2) NOT GOOD could use another BFS to sort it. or Dynamic to reduce calls of allDistances
    	
    	
    	int minPos = 1;
    	int minDistance = -1;
    	
    	for (int i = 1; i < places.size(); i++) 
    	{
    		places.add(i,stop);
    		
    		if (minDistance == -1) 
    		{
    			minDistance = this.allDistances();
    		}
    		
    		else if (this.allDistances() < minDistance) 
    		{
    			minPos = i;
    			minDistance = this.allDistances();
    		}
    		
    		places.remove(i);
    		
    		
    	}
    	
    	places.add(minPos,stop);
    	
    	
    }
    	
    public int allDistances() 
    {
    	int sumDistance = 0;
    	
    	for (int i = 1; i < places.size(); i++) 
    	{
    		sumDistance += places.get(i-1).distance(places.get(i));
    	}
    	
    	return sumDistance;
    }	
        
    public boolean allCommited() 
    {
    	//returns true if all the trips are commited to
    	for (Boolean i : commited) {if (!i) {return false;}}
    	return true;
    }

	@Override
	public String toString() {
		String build = "";
		for (int i = 1; i < places.size(); i++) 
		{
			build += places.get(i-1).name + " -> " + places.get(i).name+",  ";
		}
		return "Leg [trips=" + build + "]";
	}
    
    
    
    
    	

}
