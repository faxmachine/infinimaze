package hexagon;

import grid.BoundedGrid;
import grid.Location;

import java.util.ArrayList;

public class HexagonGrid<E> extends BoundedGrid<E> {

	public HexagonGrid(int rows, int cols) {
		super(rows, cols);
		// TODO Auto-generated constructor stub
	}

	public ArrayList<Location> getValidAdjacentLocations(Location loc) {
		ArrayList locs = new ArrayList();

		int d = 0;
		for (int i = 0; i < 6; i++) {
			Location neighborLoc = loc.getAdjacentLocation(d);
			if (isValid(neighborLoc))
				locs.add(neighborLoc);
			d += 60;
		}
		return locs;
	}
	
	public ArrayList<Location> getOccupiedLocations() {
		ArrayList theLocations = new ArrayList();

		for (int r = 0; r < getNumRows(); r++) {
			for (int c = 0; c < getNumCols(); c++) {
				Location loc = new HexagonLocation(r, c);
				if (get(loc) != null) {
					theLocations.add(loc);
				}
			}
		}
		return theLocations;
	}

}
