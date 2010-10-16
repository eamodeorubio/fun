package org.appseed.tests.conway

import groovy.lang.MissingPropertyException;

import org.appseed.conway.Cell;

import spock.lang.Specification;

abstract class ACell extends Specification {
	def 'has neighbors at eight different directions'() {
		given: 'a neighbor at a direction'
			def aCell=newCell()
			
		when:
			def neighborCell = aCell."$direction"
			def neighborCellAgain = aCell."$direction"
		then:
			interaction {
				defineInteractionsForAccessingNeighbor(aCell, direction)
			}
			neighborCell != null
			neighborCell != aCell
			neighborCell.is(neighborCellAgain)

		where:
			direction  << ['northwest',	'north', 'northeast', 'west', 'east', 'southwest', 'south', 'southeast']
	}
	
	def 'it is the neighbor of each one of its neighbors in the opposite direction'() {
		given:
			def aCell=newCell()
		
		when:
			def neighborCell = aCell."$direction"
		then:
			interaction {
				defineInteractionsForAccessingNeighbor(aCell, direction)
			}
			
			neighborCell."$opositeDirection".is(aCell)
		
		where:
			direction   | opositeDirection
			'northwest' | 'southeast'
			'north'     | 'south'
			'northeast' | 'southwest'
			'west'      | 'east'
			'east'      | 'west'
			'southwest' | 'northeast'
			'south'     | 'north'
			'southeast' | 'northwest'
	}
	
	abstract protected Cell newCell();
	
	abstract protected void defineInteractionsForAccessingNeighbor(aCell, direction);
}
