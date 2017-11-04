package grid;

import java.util.ArrayList;

public abstract class AbstractGrid<E> implements Grid<E> {
	
	public boolean isEmpty(Location loc) {
		if (!isValid(loc)) {
			throw new IllegalArgumentException("Location " + loc + 
			        " is not valid");
		}
		return get(loc) == null;
	}
	
	public ArrayList<E> getNeighbors(Location loc) {
		ArrayList neighbors = new ArrayList();
		for (Location neighborLoc : getOccupiedAdjacentLocations(loc))
			neighbors.add(get(neighborLoc));
		return neighbors;
	}

	public ArrayList<Location> getValidAdjacentLocations(Location loc) {
		ArrayList locs = new ArrayList();

		int d = 0;
		for (int i = 0; i < 8; i++) {
			Location neighborLoc = loc.getAdjacentLocation(d);
			if (isValid(neighborLoc))
				locs.add(neighborLoc);
			d += 45;
		}
		return locs;
	}

	public ArrayList<Location> getEmptyAdjacentLocations(Location loc) {
		ArrayList locs = new ArrayList();
		for (Location neighborLoc : getValidAdjacentLocations(loc)) {
			if (get(neighborLoc) == null)
				locs.add(neighborLoc);
		}
		return locs;
	}

	public ArrayList<Location> getOccupiedAdjacentLocations(Location loc) {
		ArrayList locs = new ArrayList();
		for (Location neighborLoc : getValidAdjacentLocations(loc)) {
			if (get(neighborLoc) != null)
				locs.add(neighborLoc);
		}
		return locs;
	}

	public String toString() {
		String s = "{";
		for (Location loc : getOccupiedLocations()) {
			if (s.length() > 1)
				s = s + ", ";
			s = s + loc + "=" + get(loc);
		}
		return s + "}";
	}
}