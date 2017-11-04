package Main;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import Maze.TeleMaze;
import Maze.Maze;
import Maze.MazeCorner;
import Maze.MazeDisplay;
import grid.Grid;
import grid.Location;

public class MazeTester {

	public static void main(String args[]) 
	{
		int wallLength = 1;
		int cellSize = 16; 
		
		int row, col;
		row = 15; 
		col = 20;
		Location startLocation = new Location(0, 0); 
		
		Maze maze = new Maze(row, col);
		maze.generateMaze(startLocation);
		
		displayGridFrame("Basic Maze", maze, wallLength, cellSize);
		
		TeleMaze teleMaze = new TeleMaze(row, col);
		teleMaze.maze.generateMaze(startLocation);
		teleMaze.expand(4, startLocation);
		
		displayGridFrame("TeleMaze", teleMaze.maze, wallLength, cellSize);
		int i = 1; 
		for (TeleMaze tmaze: teleMaze.getMap().values())
		{
			displayGridFrame("TeleMaze: Branch " + i, tmaze.maze, wallLength, cellSize);
			i++;
		}
		
		
		
		
		/*
		Location viewPoint = new Location(0, 0);
		
		//Location startLocation = new Location(0, 0);
		InfiniteMaze infMaze = new InfiniteMaze(row, col, 1);
		
		infMaze.name  = "originMaze";
		displayGridFrame(infMaze + "", infMaze.getGrid());
		System.out.println(infMaze.getMap().keySet().size());
		
		Maze visibleMaze = new Maze(row, col);
		
		for (Location loc: infMaze.getAccessableLocationsInRange(viewPoint, 5))
		{
			infMaze.getGrid().get(loc).type = MazeCorner.CornerType.VISABLE;
			visibleMaze.getGrid().put(loc, infMaze.getGrid().get(loc));
		}
		
		for (Location loc: infMaze.getMap().keySet())
		{
			InfiniteMaze inf = infMaze.getMap().get(loc); 
			inf.name = "Teleport from origin loc: " + loc; 
			
			if (infMaze.getAccessableLocationsInRange(viewPoint, 5).contains(loc))
			{
				for (Location vis: inf.getAccessableLocationsInRange(loc, 5))
				{
					visibleMaze.getGrid().put(vis, inf.getGrid().get(vis));
					visibleMaze.getGrid().get(vis).type = MazeCorner.CornerType.VISABLE;
					inf.getGrid().get(vis).type = MazeCorner.CornerType.VISABLE;
				}
			}
			displayGridFrame(inf.name, inf.getGrid());
		}
		
		displayGridFrame("Visible", visibleMaze.getGrid());
		
		
		
		
		/*Maze maze = new Maze(row, col); 
		System.out.println("Start");
		maze.generateMaze(new Location(0, 0));
		
		List<Location> endList = maze.getEndLocations(); 
		Location end = endList.get(new Random().nextInt(endList.size()));
		MazeCorner corner = maze.getGrid().get(end);
		corner.type = MazeCorner.CornerType.TELEPORT;
		
		Maze telemaze = new Maze(row, col);
		int openSide;
		for (openSide = 0; openSide < 4; openSide++)
		{
			if (!corner.hasWall(openSide))
				break; 
		}
		int direction = ((openSide + 2) % 4) * 90;
		Location temp = end; 
		for (int i = 0; i < 5; i++)
		{
			Location next = temp.getAdjacentLocation(direction);
			if (telemaze.getGrid().isValid(next))
			{
				temp = next; 
			}
			else 
			{
				direction += 90; 
			}
		}
		
		LocationPathSearchTuple path = telemaze.generatePathToPoint(end, temp);
		telemaze.fillPath(path);
		telemaze.generateMaze(temp);
		telemaze.getGrid().get(path.loc).removeWall(path.loc.getDirectionToward(path.prev.loc) / 90);
		telemaze.getGrid().get(path.prev.loc).removeWall(path.prev.loc.getDirectionToward(path.loc) / 90);
		telemaze.getGrid().get(end).type = MazeCorner.CornerType.TELEPORT;

		
		
		
		System.out.println("End");*/
		
		//displayGridFrame(maze.getGrid());
		//displayGridFrame(telemaze.getGrid());*/
		
	}
	
	public static void displayGridFrame(String name, Maze maze, int wallLength, int cellSize)
	{
		JFrame window = new JFrame(name);
		window.setDefaultCloseOperation(3);
		window.setContentPane(new MazeDisplay(maze, wallLength, cellSize));
		window.setVisible(true);
		window.pack();
	}
}
