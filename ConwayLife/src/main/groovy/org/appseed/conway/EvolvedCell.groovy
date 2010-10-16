package org.appseed.conway

final class EvolvedCell extends AbstractCell {
	private EvolvedCell _north, _south, _east, _west, _northeast, _southeast, _northwest, _southwest
	private Cell _parent
	def private _alive=null
	
	def EvolvedCell(definition) {
		super(definition.lifeRule)
		this._parent=definition.parent
	}
	
	boolean getAlive() {
		if(this._alive==null)
			this._alive=this.lifeRule.willBeAlive(this.parent)
		this._alive
	}
	
	Cell getParent() {
		this._parent
	}
	
	protected Cell getCell(direction, oppositeDirection) {
		if(!this."_$direction") {
			this."_$direction"=new EvolvedCell(parent:this.parent."$direction", lifeRule:lifeRule)
			this."_$direction"."_$oppositeDirection"=this
		}
		this."_$direction"
	}
	
	boolean equals(otherCell) {
		if(otherCell==null||!(otherCell instanceof EvolvedCell))
			return false;
		return otherCell.lifeRule == this.lifeRule && otherCell.parent == this.parent
	}
}
