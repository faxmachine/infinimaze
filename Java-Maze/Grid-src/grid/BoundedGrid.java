package grid;

import java.util.ArrayList;

public class BoundedGrid<E> extends AbstractGrid<E> {
	private Object[][] occupantArray;

	public BoundedGrid(int rows, int cols) {
		if (rows <= 0)
			throw new IllegalArgumentException("rows <= 0");
		if (cols <= 0)
			throw new IllegalArgumentException("cols <= 0");
		this.occupantArray = new Object[rows][cols];
	}

	public int getNumRows() {
		return this.occupantArray.length;
	}

	public int getNumCols() {
		return this.occupantArray[0].length;
	}

	public boolean isValid(Location loc) {
		return (loc.getRow() >= 0) && (loc.getRow() < getNumRows())
				&& (loc.getCol() >= 0) && (loc.getCol() < getNumCols());
	}

	public ArrayList<Location> getOccupiedLocations() {
		ArrayList theLocations = new ArrayList();

		for (int r = 0; r < getNumRows(); r++) {
			for (int c = 0; c < getNumCols(); c++) {
				Location loc = new Location(r, c);
				if (get(loc) != null) {
					theLocations.add(loc);
				}
			}
		}
		return theLocations;
	}

	public E get(Location loc) {
		if (!isValid(loc)) {
			throw new IllegalArgumentException("Location " + loc
					+ " is not valid");
		}
		return (E) this.occupantArray[loc.getRow()][loc.getCol()];
	}

	public E put(Location loc, E obj) {
		if (!isValid(loc)) {
			throw new IllegalArgumentException("Location " + loc
					+ " is not valid");
		}
		if (obj == null) {
			throw new NullPointerException("obj == null");
		}

		E oldOccupant = (E) get(loc);
		this.occupantArray[loc.getRow()][loc.getCol()] = obj;
		return oldOccupant;
	}

	public E remove(Location loc) {
		if (!isValid(loc)) {
			throw new IllegalArgumentException("Location " + loc
					+ " is not valid");
		}

		E r = (E) get(loc);
		this.occupantArray[loc.getRow()][loc.getCol()] = null;
		return r;
	}

	public Location getRandomLocation() {
		int randomRow = (int) (Math.random() * 10.0D * getNumRows())
				% getNumRows();
		int randomCol = (int) (Math.random() * 10.0D * getNumCols())
				% getNumCols();
		return new Location(randomRow, randomCol);
	}
}