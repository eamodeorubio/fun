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
		
		// Sadly the following didn't work in Spock
		// 1 * parentCell."$direction" >> parentNeighborCell
		// So a if - else if  is needed
		
		if(direction=='north')
			1 * parentCell.north >> parentNeighborCell
		else if(direction=='south')
			1 * parentCell.south >> parentNeighborCell
		else if(direction=='southeast')
			1 * parentCell.southeast >> parentNeighborCell
		else if(direction=='northeast')
			1 * parentCell.northeast >> parentNeighborCell
		else if(direction=='southwest')
			1 * parentCell.southwest >> parentNeighborCell
		else if(direction=='northwest')
			1 * parentCell.northwest >> parentNeighborCell
		else if(direction=='east')
			1 * parentCell.east >> parentNeighborCell
		else if(direction=='west')
			1 * parentCell.west >> parentNeighborCell
	}
	
	def 'has all neighbors with its LifeRule and they are EvolvedCell built from each parent\'s neighbor'() {
		given: 'a neighbor at a direction'
			def aCell=new EvolvedCell(parent:parentCell, lifeRule: lifeRule)
			def parentNeighborCell = Mock(Cell)

		when:
			def neighborCell=aCell."$direction"	
		then:
			interaction {
				defineInteractionsForAccessingNeighbor(aCell, direction) 
			}

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
