package grid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UnboundedGrid<E> extends AbstractGrid<E>
{
  private Map<Location, E> occupantMap;

  public UnboundedGrid()
  {
    this.occupantMap = new HashMap();
  }

  public int getNumRows() {
    return -1;
  }

  public int getNumCols() {
    return -1;
  }

  public boolean isValid(Location loc) {
    return true;
  }

  public ArrayList<Location> getOccupiedLocations() {
    ArrayList a = new ArrayList();
    for (Location loc : this.occupantMap.keySet())
      a.add(loc);
    return a;
  }

  public E get(Location loc) {
    if (loc == null)
      throw new NullPointerException("loc == null");
    return this.occupantMap.get(loc);
  }

  public E put(Location loc, E obj) {
    if (loc == null)
      throw new NullPointerException("loc == null");
    if (obj == null)
      throw new NullPointerException("obj == null");
    return this.occupantMap.put(loc, obj);
  }

  public E remove(Location loc) {
    if (loc == null)
      throw new NullPointerException("loc == null");
    return this.occupantMap.remove(loc);
  }

  public Location getRandomLocation()
  {
    return null;
  }
}