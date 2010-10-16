package org.appseed.conway

final class ConwayLifeRule implements LifeRule {

	@Override
	public boolean willBeAlive(Cell cell) {
		def liveCount=0
		liveCount+=cell.north.alive?1:0;
		liveCount+=cell.northeast.alive?1:0;
		liveCount+=cell.northwest.alive?1:0;
		liveCount+=cell.east.alive?1:0;
		liveCount+=cell.west.alive?1:0;
		liveCount+=cell.south.alive?1:0;
		liveCount+=cell.southeast.alive?1:0;
		liveCount+=cell.southwest.alive?1:0;
		if(liveCount<2)
			return false
		if(liveCount==3)
			return true
		if(liveCount>3)
			return false
		return cell.alive
	}

}
