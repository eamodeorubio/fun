package org.appseed.conway

final class SeedCell extends AbstractCell {
	private SeedCell _north, _south, _east, _west, _northeast, _southeast, _northwest, _southwest
	private boolean _alive
	
	def SeedCell(LifeRule lifeRule) {
		super(lifeRule)
	}
	
	def SeedCell(definition) {
		super(definition.lifeRule)
		this._alive=definition.alive
	}
	
	boolean getAlive() {
		this._alive
	}
	
	protected Cell getCell(direction, oppositeDirection, alive=false) {
		if(!this."_$direction") {
			this."_$direction"=new SeedCell(alive:alive, lifeRule:lifeRule)
			this."_$direction"."_$oppositeDirection"=this
		} else if (alive)
			throw new IllegalStateException("A dead cell existed at $direction, cannot create an alive one there")
		
		this."_$direction"
	}
	
	Cell getNorthIsAlive() {
		getCell('north', 'south', true)
	}
	
	Cell getNortheastIsAlive() {
		getCell('northeast', 'southwest', true)
	}
	
	Cell getNorthwestIsAlive() {
		getCell('northwest', 'southeast', true)
	}
	
	Cell getSouthIsAlive() {
		getCell('south', 'north', true)
	}
	
	Cell getSoutheastIsAlive() {
		getCell('southeast', 'northwest', true)
	}
	
	Cell getSouthwestIsAlive() {
		getCell('southwest', 'northeast', true)
	}
	
	Cell getEastIsAlive() {
		getCell('east', 'west', true)
	}
	
	Cell getWestIsAlive() {
		getCell('west', 'east', true)
	}
}
