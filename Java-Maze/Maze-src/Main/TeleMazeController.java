package Main;

import java.util.ArrayList;
import java.util.List;

import Maze.*;
import grid.Location;

public class TeleMazeController {

	public final TeleMaze originMaze; 
	
	TeleMaze currTeleMaze;
	
	public TeleMazeController(int row, int col)
	{
		originMaze = new TeleMaze(row, col);
	}
	
	public TeleMazeController(int row, int col, long seed)
	{
		originMaze = new TeleMaze(row, col, seed);
	}
	
	public TeleMazeController(TeleMaze originMaze)
	{
		this.originMaze = originMaze;
		
		//currInfMaze = new InfiniteMaze(15, 20, new Location(0, 0));
		//displayMazes();
	}
	
	public boolean canLinkMazesAtLocation(Maze from, Maze to, Location loc)
	{
		int row = from.getNumRows();
		int col = from.getNumCols();
		
		if (row != to.getNumRows() || col != to.getNumCols())
			return false;
		if (from.isValid(loc))
			return false;
		
		MazeCorner fromCorner = from.get(loc);
		MazeCorner toCorner = to.get(loc);
		
		for (int i = 0; i < 4; i++)
		{
			if (!fromCorner.hasWall(i) && !toCorner.hasWall(i))
				return false;
		}
		
		return true;
	}
	
	public List<Maze> getNeighboringMazes(TeleMaze infMaze)
	{
		List<Maze> mazeList= new ArrayList();
		mazeList.add(infMaze);
		for (Maze maze: infMaze.getMap().values())
		{
			mazeList.add(maze);
		}
		return mazeList; 
	}
	
	public LocationPathSearchTuple getPathBetweenTeleMazes(TeleMaze fromMaze, Location fromLoc, TeleMaze destMaze, Location destLoc)
	{
		LocationPathSearchTuple mazePath = getTeleMazePath(fromMaze, fromLoc, destMaze, destLoc);
		if (mazePath == null)
			return null; 
		
		TeleMaze maze = destMaze; 
		LocationPathSearchTuple totalPath = new LocationPathSearchTuple(mazePath.loc, null, mazePath.maze);
		while (mazePath.prev != null)
		{
			Location a = mazePath.loc; 
			Location b = mazePath.prev.loc; 
			
			LocationPathSearchTuple path = maze.generatePathToPoint(b, a);
			totalPath.prev = path.prev; 
			mazePath = mazePath.prev; 
			
			maze = maze.getMap().get(b);
		}
		
		return totalPath;
	}
	
	
	private LocationPathSearchTuple getTeleMazePath(TeleMaze fromMaze, Location fromLoc, TeleMaze destMaze, Location destLoc)
	{
		int depthFrom, depthDest, dist; 
		LocationPathSearchTuple fromPath, destPath, tempdestPath; 
		
		depthFrom = fromMaze.depth;
		depthDest = destMaze.depth;
		
		if (depthFrom < 0)
			return null;
		if (depthDest < 0)
			return null; 

		fromPath = new LocationPathSearchTuple(fromLoc, null, fromMaze);
		destPath = tempdestPath = new LocationPathSearchTuple(destLoc, null, destMaze);
		
		do 
		{
			
			depthFrom = fromMaze.depth;
			depthDest = destMaze.depth;
			
			if (depthFrom == depthDest)
			{
				if (fromMaze.equals(destMaze))
				{
					tempdestPath.prev = new LocationPathSearchTuple(tempdestPath.loc, fromPath, fromMaze);
					return destPath;  
				}
			}		
			if (depthFrom >= depthDest)
			{
				fromLoc = getTeleSpotToPreviousMaze(fromMaze);
				if (fromLoc != null)
				{
					fromPath = new LocationPathSearchTuple(fromLoc, fromPath, fromMaze);
					fromMaze = fromMaze.getMap().get(fromLoc); 
				}			
			}
			if (depthDest >= depthFrom)
			{
				destLoc = getTeleSpotToPreviousMaze(destMaze);
				if (destLoc != null)
				{

					tempdestPath.prev = new LocationPathSearchTuple(tempdestPath.loc, new LocationPathSearchTuple(destLoc, null, destMaze), tempdestPath.maze);
					tempdestPath = tempdestPath.prev; 
					destMaze = fromMaze.getMap().get(destLoc);
				}
				 
			}
			dist = Math.max(depthFrom, depthDest);
						
		} while (dist > 0);
		
		
		
		return null;
	}
	
	public Maze getVisibleMazeInRange(TeleMaze infMaze, Location viewLocation, int range)
	{
		Maze visibleMaze = new Maze(infMaze.getNumRows(), infMaze.getNumCols());
		
		
		
		visibleMaze.put(viewLocation, infMaze.get(viewLocation));
		
		//LocationPathSearchTuple path = new LocationPathSearchTuple(viewLocation, null);
		for (int i = 0; i < 4; i++)
		{
			int direction = i * 90; 
			Location next = viewLocation.getAdjacentLocation(direction);
			
			
			
		}
		return null;
		
	}
	
	
	public Location getTeleSpotToPreviousMaze(TeleMaze infMaze)
	{
		if (infMaze.depth == 0)
		{
			return null; 
		}
		
		for (Location loc: infMaze.getMap().keySet())
		{
			TeleMaze prev = infMaze.getMap().get(loc);
			if (prev.depth < infMaze.depth)
				return loc; 
		}
		return null;
	}
	
	
	
	
	
	/*public void displayVisibleMaze(InfiniteMaze infMaze, Location viewPoint)
	{
		
		visibleMaze = new Maze(infMaze.getGrid().getNumRows(), infMaze.getGrid().getNumCols());
		List<Location> visibleLocations = infMaze.getAccessableLocationsInRange(viewPoint, 5); 
		
		for (Location loc: visibleLocations)
		{
			infMaze.getGrid().get(loc).type = MazeCorner.CornerType.VISABLE;
			visibleMaze.getGrid().put(loc, infMaze.getGrid().get(loc));
		}
		
		for (Location loc: infMaze.getMap().keySet())
		{
			InfiniteMaze inf = infMaze.getMap().get(loc);  
			
			if (visibleLocations.contains(loc))
			{
				for (Location vis: ((Maze)inf).getAccessableLocationsInRange(loc, 5))
				{
					visibleMaze.getGrid().put(vis, inf.getGrid().get(vis));
					visibleMaze.getGrid().get(vis).type = MazeCorner.CornerType.VISABLE;
					inf.getGrid().get(vis).type = MazeCorner.CornerType.VISABLE;
				}
			}
		}
		visibleMazeFrame.repaint(); 
		
	}*/
	
	
}
