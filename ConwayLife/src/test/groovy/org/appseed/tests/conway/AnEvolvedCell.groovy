package org.appseed.tests.conway

import groovy.lang.MissingPropertyException;

import org.appseed.conway.Cell;
import org.appseed.conway.EvolvedCell;
import org.appseed.conway.LifeRule;

import spock.lang.Specification;

class AnEvolvedCell extends ACell {
	def lifeRule=Mock(LifeRule)
	def parentCell=Mock(Cell)
	
	def 'has a parent cell and a life rule'() {
		given:
			def aCell=new EvolvedCell(parent:parentCell, lifeRule: lifeRule)
		expect:
			aCell.parent == parentCell
			aCell.lifeRule == lifeRule
	}
	
	def 'is equal to another EvolvedCell if their parents and life rules are equal'() {
		given:
			def aCell=new EvolvedCell(parent:parentCell, lifeRule: lifeRule)
			def otherCell=new EvolvedCell(parent:parentCell, lifeRule: lifeRule)
			def otherParentCell=Mock(Cell)
			def otherLifeRule=Mock(LifeRule)
			def otherCellWithOtherParent=new EvolvedCell(parent:otherParentCell, lifeRule: lifeRule)
			def otherCellWithOtherLifeRule=new EvolvedCell(parent:parentCell, lifeRule: otherLifeRule)
			def yetAnotherCell=new EvolvedCell(parent:otherParentCell, lifeRule: otherLifeRule)
		expect:
			aCell == otherCell
			aCell != otherCellWithOtherParent
			otherCell != otherCellWithOtherParent
			aCell != otherCellWithOtherLifeRule
			otherCell != otherCellWithOtherLifeRule
			aCell != yetAnotherCell
			otherCell != yetAnotherCell
	}
	
	def 'parent and lifeRule are read only'() {
		given:
			def aCell=new EvolvedCell(parent:parentCell, lifeRule: lifeRule)
			def otherParentCell=Mock(Cell)
			def otherLifeRule=Mock(LifeRule)
			
		when:
			aCell.parent = otherParentCell
		then:
			thrown(Exception)
			aCell.parent == parentCell
			
		when:
			aCell.lifeRule = otherLifeRule
		then:
			thrown(Exception)
			aCell.lifeRule == lifeRule
	}
	
	protected Cell newCell() {
		new EvolvedCell(parent:parentCell, lifeRule: lifeRule)
	}
	
	protected void defineInteractionsForAccessingNeighbor(aCell, direction) {
		def parentCell = aCell.parent
		def parentNeighborCell = Mock(Cell)
		
		1 * parentCell."$direction" >> parentNeighborCell
	}
	
	def 'has as neighbors an EvolvedCell built from the parent neighbor and with the same LifeRule'() {
		given: 'a neighbor at a direction'
			def aCell=new EvolvedCell(parent:parentCell, lifeRule: lifeRule)
			def parentNeighborCell = Mock(Cell)

		when:
			def neighborCell=aCell."$direction"	
		then:
			1 * parentCell."$direction" >> parentNeighborCell

			neighborCell instanceof EvolvedCell
			neighborCell.parent == parentNeighborCell
			neighborCell.lifeRule == lifeRule

		where:
			direction  << ['northwest',	'north', 'northeast', 'west', 'east', 'southwest', 'south', 'southeast'] 
	}
	
	def 'evolves into a EvolvedCell with itself as parent and the same LifeRule'() {
		given: 'a cell with a given life value and a LifeRule'
			def oldCell=new EvolvedCell(parent:parentCell, lifeRule: lifeRule)
		
		when: 'evolved'
			def newCell=oldCell.evolve()
		then: 'generates an EvolvedCell with itself as parent and same LifeRule'
			newCell != null
			newCell instanceof EvolvedCell
			newCell.parent == oldCell
			newCell.lifeRule == lifeRule
	}
	
	def 'it is alive if its LifeRule applied to its parent says so'() {
		given:
			def aCell=new EvolvedCell(parent:parentCell, lifeRule: lifeRule)
		
		when:
			def isAlive = aCell.alive
		then: 
			1 * lifeRule.willBeAlive(parentCell) >> nextLifeValue
			
			isAlive == nextLifeValue
			
		where:
			nextLifeValue << [true, false]
	}
}
