package Maze;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

import grid.BoundedGrid;
import grid.Grid;
import grid.Location;

import Main.*;

public class Maze extends BoundedGrid<MazeCorner> {
	
	//protected Grid<MazeCorner> mazeGrid; 
	
	int locationRange = 6;
	double[] emptyCellRatioValues = {.45, .65}; 
	int visibleRange = 15;
	
	Random random; 

	public Maze(int row, int col) 
	{
		super(row, col);
		random = new Random();
	}
	
	public Maze(int row, int col, long randomSeed) 
	{
		super(row, col);
		random = new Random(randomSeed);
	}
	
	/*
	 * 
	 */
	public void generateMaze(Location startLocation)
	{
		System.out.println("Start Location: " + startLocation);
		Queue<Location> locationQueue = new LinkedList();
		this.put(startLocation, new MazeCorner(startLocation));
		locationQueue.add(startLocation);
		
		while (locationQueue.peek() != null)
		{
			Location loc = locationQueue.poll(); 
			
			MazeCorner locCorner = this.get(loc);
		
			removeWalls(locCorner);
			if (locCorner.numWalls() > 3)
				continue; 

			int numRandSpots = 4 - locCorner.numWalls();
			
			for (int i = 0; i < numRandSpots; i++)
			{
				List<Location> locationsInRange = this.getAccessableEmptyLocationsInRange(loc, locationRange);
				if (locationsInRange.size() == 0)
				{
					continue;  
				}
				
				int randIndex = random.nextInt(locationsInRange.size());
				
				Location to = locationsInRange.get(randIndex);
				LocationPathSearchTuple path = generatePathToPoint(loc, to);
				 
				this.put(path.loc, new MazeCorner(path.loc));
				fillPath(path);
				locationQueue.add(path.loc);
			}
			
			fillWalls();
		}
	}
		
	public LocationPathSearchTuple generatePathToPoint(Location from, Location to)
	{
		if (!(this.isValid(from) && this.isValid(to)))
			return null; 
		
		 PriorityQueue<LocationPathSearchTuple> locationQueue = new PriorityQueue(100, new LocationDistanceComparator(to));
		 List<Location> visitiedLocationList = new ArrayList();
		 LocationPathSearchTuple fromTuple = new LocationPathSearchTuple(from, null, this);
		 
		 locationQueue.add(fromTuple);
		 
		 while (!locationQueue.isEmpty()) 
		 {
			 LocationPathSearchTuple path = locationQueue.poll();
			 visitiedLocationList.add(path.loc);
			 if (path.loc.equals(to))
			 {
				return path;  
			 }
			 
			 for (int i = 0; i < 4; i++)
			 {
				 
				 int direction = i * 90; 
				 Location next = path.loc.getAdjacentLocation(direction);
				 if (!this.isValid(next))
					 continue; 
				 
				 if (path.prev == null || !next.equals(path.prev.loc))
				 {
					 if (canTravelFromLocationToNeighborLocation(path.loc, next))
					 {
						 if (!visitiedLocationList.contains(next))
							 locationQueue.add(new LocationPathSearchTuple(next, path, this));
					 }
				 }
			 }
			 
		 }
		 return null; 
	}
	
	public void fillPath(LocationPathSearchTuple path)
	{

		if (path.prev == null)
			return; 
		
		
		Location prevLocation = path.prev.loc;
		MazeCorner locCorner;
		
		if (this.isEmpty(path.loc))
		{
			this.put(path.loc, new MazeCorner(path.loc));
		}
		
		locCorner = this.get(path.loc);
		int direction = path.loc.getDirectionToward(prevLocation) / 90; 
		locCorner.removeWall(direction);
		
		direction = prevLocation.getDirectionToward(path.loc) / 90;
		if (this.isEmpty(prevLocation))
		{
			MazeCorner prevCorner = new MazeCorner(prevLocation);
			prevCorner.removeWall(direction);
			this.put(prevLocation, prevCorner);
		}
		else 
		{
			System.out.println(path.loc + " -> " + prevLocation);
			// There should never be a case where the path is blocked by a wall I dont think 
			// But if the case comes up here is the spot to handle it
		
		}
		
		fillPath(path.prev);
		
	}
	
	public List<Location> getEndLocations()
	{
		List<Location> endCornerList = new ArrayList();
		for (Location loc: this.getOccupiedLocations())
		{
			MazeCorner corner = this.get(loc);
			if (corner.numWalls() == 3)
			{
				endCornerList.add(loc); 
			}
		}
		return endCornerList; 
	}
	
	
	private List<Location> getAccessableEmptyLocationsInRange(Location from, int range)
	{
		List<Location> emptyLocations = new ArrayList();
		for (Location loc: getAccessableLocationsInRange(from, range))
		{
			if (this.isEmpty(loc)) 
			{
				emptyLocations.add(loc);
			}
		}
		return emptyLocations;
	}
	
	public List<Location> getAccessableLocationsInRange(Location loc, int range)
	{
		List<Location> accessableLocationList = new ArrayList();
		Queue<LocationPathSearchTuple> pathQueue = new LinkedList();
		pathQueue.add(new LocationPathSearchTuple(loc, null, this));
		
		while(!pathQueue.isEmpty())
		{
			LocationPathSearchTuple path = pathQueue.poll();
			accessableLocationList.add(path.loc);
			
			if (path.depth >= range)
				continue; 
			
			for (int i = 0; i < 4; i++)
			{
				int direction = i * 90;
				Location next = path.loc.getAdjacentLocation(direction);
				
				if (path.prev != null && path.prev.loc.equals(next))
					continue;
				
				if (canTravelFromLocationToNeighborLocation(path.loc, next))
				{
					if (!accessableLocationList.contains(next))
					{
						LocationPathSearchTuple nextPath = new LocationPathSearchTuple(next, path, this);
						pathQueue.add(nextPath);
					}
				}
			}
		}
		
		return accessableLocationList; 
	}
	
	private boolean canTravelFromLocationToNeighborLocation(Location from, Location to)
	{
		if (!this.isValid(from))
			return false;
		if (!this.isValid(to))
			return false;
		
		int direction = from.getDirectionToward(to);
		
		if (!from.getAdjacentLocation(direction).equals(to))
			return false;
		
		if (!this.isEmpty(from))
		{
			MazeCorner fromCorner = this.get(from);
			if (fromCorner.hasWall(direction / 90))
			{
				return false; 
			}
		}
		
		if (!this.isEmpty(to))
		{
			MazeCorner toCorner = this.get(to);
			if (toCorner.hasWall(((direction + 180) % 360) / 90))
			{
				return false; 
			} 
		}
		return true;
	}
	
	private void removeWalls(MazeCorner corner)
	{
		int numPossiblePassageways = corner.numWalls(); 
		if (numPossiblePassageways < 1)
			return; 
		
		//double randValue = new Random().nextDouble();
		
		double emptyCellRatio = (double)this.getNumEmptyLocation() / (double)(this.getNumCols() * this.getNumRows());
		int numPassageways = 1; 
		
		for (int i = 0; i < this.emptyCellRatioValues.length; i++)
		{
			if (emptyCellRatio > emptyCellRatioValues[i])
			{
				numPassageways++;
			}
		}
		
		int bound = 3 - numPassageways;
		for (int i = 0; i < bound; i++)
		{
			double randValue = random.nextDouble();
			if (randValue > emptyCellRatio)
				numPassageways++; 
		}
	
			
		int k = random.nextInt(4); 
		for (int j = 0; j < 4; j++)
		{
			int side = (k + j) % 4;
			if (!corner.hasWall(side))
				continue; 
					
			float rand = random.nextFloat();
			float check = (float)numPassageways / (float)numPossiblePassageways;
			if (rand < check)
			{
				corner.removeWall(side);
				numPassageways--;
			}
			numPossiblePassageways--; 	
		}
	}
		
	private int getNumEmptyLocation()
	{
		int num = 0;
		for (int r = 0; r < this.getNumRows(); r++)
		{
			for (int c = 0; c < this.getNumCols(); c++)
			{
				Location loc = new Location(r, c); 
				if (this.isEmpty(loc))
				{
					num++;
				}
			}
		}
		return num;
	}
	
	private void fillWalls()
	{
		List<Location> occupiedLocations = this.getOccupiedLocations(); 
		for (Location loc: occupiedLocations)
		{
			MazeCorner corner = this.get(loc);
			for (int side = 0; side < 4; side++)
			{
				int direction = side * 90;
				Location sideLocation = loc.getAdjacentLocation(direction);
				if (corner.hasWall(side))
				{
					if (!this.isValid(sideLocation))
					{
						continue; 
					}
					
					if (!this.isEmpty(sideLocation))
					{
						MazeCorner sideCorner = this.get(sideLocation);
						if (!sideCorner.hasWall((side + 2) % 4))
						{
							sideCorner.addWall((side + 2) % 4);
						}
					}
				}
				else 
				{

					if (!this.isValid(sideLocation))
					{
						corner.addWall(side);
					}
				}
			}
		}
	}

	
	
}
