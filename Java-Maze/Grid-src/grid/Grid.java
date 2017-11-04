package grid;

import java.util.ArrayList;

public abstract interface Grid<E>
{
  public abstract int getNumRows();

  public abstract int getNumCols();

  public abstract boolean isValid(Location paramLocation);
  
  public abstract boolean isEmpty(Location paramLocation);

  public abstract E put(Location paramLocation, E paramE);

  public abstract E remove(Location paramLocation);

  public abstract E get(Location paramLocation);

  public abstract ArrayList<Location> getOccupiedLocations();

  public abstract ArrayList<Location> getValidAdjacentLocations(Location paramLocation);

  public abstract ArrayList<Location> getEmptyAdjacentLocations(Location paramLocation);

  public abstract ArrayList<Location> getOccupiedAdjacentLocations(Location paramLocation);

  public abstract ArrayList<E> getNeighbors(Location paramLocation);

  public abstract Location getRandomLocation();
}