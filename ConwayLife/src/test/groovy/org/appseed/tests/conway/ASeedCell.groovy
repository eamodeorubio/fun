package org.appseed.tests.conway

import groovy.lang.MissingPropertyException;

import org.appseed.conway.Cell;
import org.appseed.conway.EvolvedCell;
import org.appseed.conway.SeedCell;
import org.appseed.conway.LifeRule;

import spock.lang.Specification;

class ASeedCell extends ACell {
	def lifeRule=Mock(LifeRule)
	
	def 'has a life value and a life rule'() {
		given:
			def aCell=new SeedCell(alive:aLifeValue, lifeRule: lifeRule)
		
		expect:
			aCell.alive == aLifeValue
			aCell.lifeRule == lifeRule
			
		where:
			aLifeValue << [true, false]
	}
	
	def 'alive is read only'() {
		given:
			def aCell=new SeedCell(alive:aLifeValue, lifeRule: lifeRule)
		
		when:
			aCell.alive = !aLifeValue
		then:
			thrown(Exception)
			aCell.alive == aLifeValue
			
		where:
			aLifeValue << [true, false]
	}
	
	def 'lifeRule is read only'() {
		given:
			def aCell=new SeedCell(alive:aLifeValue, lifeRule: lifeRule)
			def otherLifeRule = Mock(LifeRule)
			
		when:
			aCell.lifeRule = otherLifeRule
		then:
			thrown(Exception)
			aCell.lifeRule == lifeRule
			
		where:
			aLifeValue << [true, false]
	}
	
	def 'is dead by default'() {
		given:
			def defaultCell=new SeedCell(lifeRule)
		expect:
			! defaultCell.alive
	}
	
	protected Cell newCell() {
		new SeedCell(lifeRule: lifeRule)
	}
	
	protected void defineInteractionsForAccessingNeighbor(aCell, direction) {
		// NO Interactions needed
	}
	
	def 'has as neighbors SeedCells too, which are dead by default and have same LifeRule'() {
		given: 'a neighbor at a direction'
			def aCell=new SeedCell(alive:lifeValue, lifeRule: lifeRule)
			def neighborCell = aCell."$direction"
			def neighborCellAgain = aCell."$direction"
		expect:
			neighborCell instanceof SeedCell
			! neighborCell.alive
			neighborCell.lifeRule == aCell.lifeRule
		
		where:
			direction   | lifeValue
			'northwest' | true
			'north'     | true
			'northeast' | true
			'west'      | true
			'east'      | true
			'southwest' | true
			'south'     | true
			'southeast' | true
			'northwest' | false
			'north'     | false
			'northeast' | false
			'west'      | false
			'east'      | false
			'southwest' | false
			'south'     | false
			'southeast' | false
	}
	
	
	def 'can have alive neighbours if specified'() {
		given:
			def aCell=new SeedCell(alive:lifeValue, lifeRule: lifeRule)
		
		when:
			def neighbourCell=aCell."${direction}IsAlive"
		then:
			neighbourCell != null
			neighbourCell.is(aCell."$direction")
			neighbourCell.alive
			
		where:
			direction   | lifeValue
			'northwest' | true
			'north'     | true
			'northeast' | true
			'west'      | true
			'east'      | true
			'southwest' | true
			'south'     | true
			'southeast' | true
			'northwest' | false
			'north'     | false
			'northeast' | false
			'west'      | false
			'east'      | false
			'southwest' | false
			'south'     | false
			'southeast' | false
	}
	
	def 'cannot specify a neighbour as alive if it was already specified as dead'() {
		given:
			def aCell=new SeedCell(alive:lifeValue, lifeRule: lifeRule)
		
		when:
			def neighbourCell=aCell."$direction"
			aCell."${direction}IsAlive"
		then:
			thrown(IllegalStateException)
			! neighbourCell.alive
		
		where:
			direction   | lifeValue
			'northwest' | true
			'north'     | true
			'northeast' | true
			'west'      | true
			'east'      | true
			'southwest' | true
			'south'     | true
			'southeast' | true
			'northwest' | false
			'north'     | false
			'northeast' | false
			'west'      | false
			'east'      | false
			'southwest' | false
			'south'     | false
			'southeast' | false
	}
	
	def 'evolves into a EvolvedCell with itself as parent and the same LifeRule'() {
		given: 'a cell with a given life value and a LifeRule'
			def oldCell = new SeedCell(alive:lifeValue, lifeRule:lifeRule)
		
		when: 'evolved'
			def newCell=oldCell.evolve()
		then: 'generates an EvolvedCell with itself as parent and same LifeRule'
			newCell != null
			newCell instanceof EvolvedCell
			newCell.parent == oldCell
			newCell.lifeRule == lifeRule
			
		where:
			lifeValue << [true, false]
	}
}
