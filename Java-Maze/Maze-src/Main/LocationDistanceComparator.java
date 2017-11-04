package Main;

import java.util.Comparator;

import grid.Location;

public class LocationDistanceComparator implements Comparator<LocationPathSearchTuple>{

	private Location relativeLocation;
	
	public LocationDistanceComparator(Location relativeLocation)
	{
		this.relativeLocation = relativeLocation;
	}
	
	@Override
	public int compare(LocationPathSearchTuple o1, LocationPathSearchTuple o2) {
		// TODO Auto-generated method stub
		if (relativeLocation.getDistanceTo(o1.loc) < relativeLocation.getDistanceTo(o2.loc))
			return -1; 
		else if (relativeLocation.getDistanceTo(o1.loc) > relativeLocation.getDistanceTo(o2.loc))
			return 1;
		return 0; 
	}
}
