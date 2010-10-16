package org.appseed.conway

abstract class AbstractCell implements Cell {
	private LifeRule _lifeRule
	
	def AbstractCell(LifeRule lifeRule) {
		this._lifeRule=lifeRule
	}
	
	Cell getNorth() {
		getCell('north', 'south')
	}
	
	Cell getNortheast() {
		getCell('northeast', 'southwest')
	}
	
	Cell getNorthwest() {
		getCell('northwest', 'southeast')
	}
	
	Cell getSouth() {
		getCell('south', 'north')
	}
	
	Cell getSoutheast() {
		getCell('southeast', 'northwest')
	}
	
	Cell getSouthwest() {
		getCell('southwest', 'northeast')
	}
	
	Cell getEast() {
		getCell('east', 'west')
	}
	
	Cell getWest() {
		getCell('west', 'east')
	}
	
	Cell evolve() {
		new EvolvedCell(parent:this, lifeRule:this.lifeRule)
	}
	
	LifeRule getLifeRule() {
		this._lifeRule
	}
	
	protected abstract Cell getCell(direction, oppositeDirection);
}
