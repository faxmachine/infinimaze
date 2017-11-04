package hexagon;

import grid.Location;

public class HexagonLocation extends Location {
	
	public HexagonLocation(int row, int col) {
		super(row, col);
	}

	public Location getAdjacentLocation(int direction) {
		int adjustedDirection = (direction + 30) % 360;
		if (adjustedDirection < 0) {
			adjustedDirection += 360;
		}
		adjustedDirection = adjustedDirection / 60 * 60;

		int dc = 0;
		int dr = 0;
		if (adjustedDirection == 0) {
			dr = -1;
		} else if (adjustedDirection == 60) {
			dc = 1;
			if (getRow() % 2 == 1) {
				dr = -1;
			}
		} else if (adjustedDirection == 120) {
			dc = 1;
			if (getRow() % 2 == 0) {
				dr = 1;
			}
		} else if (adjustedDirection == 180) {
			dr = 1;
		} else if (adjustedDirection == 240) {
			dc = -1;
			if (getRow() % 2 == 0) {
				dr = -1;
			}
		} else if (adjustedDirection == 300) {
			dc = -1;
			if (getRow() % 2 == 1) {
				dr = -1;
			}
		}

		return new HexagonLocation(getRow() + dr, getCol() + dc);
	}

	/*public int getDirectionToward(Location target) {
		int dx = target.getCol() - getCol();
		int dy = target.getRow() - getRow();

		int angle = (int) Math.toDegrees(Math.atan2(-dy, dx));
		int compassAngle = 60 - angle;

		compassAngle += 30;
		if (compassAngle < 0) {
			compassAngle += 360;
		}
		return compassAngle / 60 * 60;
	}*/

}
