package org.appseed.conway

final class ConwayLifeRule implements LifeRule {

	@Override
	public boolean willBeAlive(Cell cell) {
		return false;
	}

}
