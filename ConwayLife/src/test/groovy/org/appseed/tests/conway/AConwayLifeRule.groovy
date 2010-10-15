package org.appseed.tests.conway

import org.appseed.conway.ConwayLifeRule;
import org.appseed.conway.Cell;

import spock.lang.Specification;

class AConwayLifeRule extends Specification {
	def aCell=Mock(Cell)
	def nCell=Mock(Cell)
	def sCell=Mock(Cell)
	def eCell=Mock(Cell)
	def wCell=Mock(Cell)
	def neCell=Mock(Cell)
	def nwCell=Mock(Cell)
	def seCell=Mock(Cell)
	def swCell=Mock(Cell)
	def lifeRule=new ConwayLifeRule()

	def stubLifeState(nwLife, nLife, neLife, wLife, eLife, swLife, sLife, seLife) {
		aCell.north >> nCell
		aCell.northeast >> neCell
		aCell.northwest >> nwCell
		aCell.east >> eCell
		aCell.west >> wCell
		aCell.south >> sCell
		aCell.southeast >> swCell
		aCell.southwest >> swCell
		
		nCell.alive >> nLife
		sCell.alive >> sLife
		eCell.alive >> eLife
		wCell.alive >> wLife
		neCell.alive >> neLife
		nwCell.alive >> nwLife
		seCell.alive >> seLife
		swCell.alive >> swLife
	}
	
	def 'says a cell with fewer than two live neighbours will become dead'() {
		given: 
			interaction {
				stubLifeState(nwLife, nLife, neLife, wLife, eLife, swLife, sLife, seLife)
			}
			
		when:
			def isAlive = lifeRule.willBeAlive(aCell)
		then:
			aCell.alive >> true
			! isAlive
			
		when:
			isAlive = lifeRule.willBeAlive(aCell)
		then:
			aCell.alive >> false
			! isAlive
		
		where:
			nwLife | nLife | neLife | wLife | eLife | swLife | sLife | seLife
			false  | false | false  | false | false | false  | false | false
			true   | false | false  | false | false | false  | false | false
			false  | true  | false  | false | false | false  | false | false
			false  | false | true   | false | false | false  | false | false
			false  | false | false  | true  | false | false  | false | false
			false  | false | false  | false | true  | false  | false | false
			false  | false | false  | false | false | true   | false | false
			false  | false | false  | false | false | false  | true  | false
			false  | false | false  | false | false | false  | false | true
	}
	
	def 'says a cell with more than three live neighbours will become dead'() {
		given:
			interaction {
				stubLifeState(nwLife, nLife, neLife, wLife, eLife, swLife, sLife, seLife)
			}
			
		when:
			def isAlive = lifeRule.willBeAlive(aCell)
		then:
			aCell.alive >> true
			! isAlive
			
		when:
			isAlive = lifeRule.willBeAlive(aCell)
		then:
			aCell.alive >> false
			! isAlive
		
		where:
			nwLife | nLife | neLife | wLife | eLife | swLife | sLife | seLife
			true   | true  | true   | true  | true  | true   | true  | true
			true   | false | true   | true  | true  | true   | true  | true
			true   | false | true   | true  | true  | true   | false | true
			false  | false | true   | true  | true  | true   | false | true
			false  | false | true   | true  | true  | true   | false | false
			false  | true  | true   | true  | true  | true   | true  | true
			false  | true  | true   | false | true  | true   | true  | true
			false  | true  | false  | true  | false | true   | true  | true
			true   | false | true   | false | true  | false  | true  | false
	}
	
	def 'says a live cell with two or three live neighbours will become alive'() {
		given:
			interaction {
				aCell.alive >> true
				stubLifeState(nwLife, nLife, neLife, wLife, eLife, swLife, sLife, seLife)
			}
		expect:
			lifeRule.willBeAlive(aCell)
		
		where:
			nwLife | nLife | neLife | wLife | eLife | swLife | sLife | seLife
			true   | true  | true   | false | false | false  | false | false
			false  | true  | true   | true  | false | false  | false | false
			false  | false | true   | true  | true  | false  | false | false
			false  | false | false  | true  | true  | true   | false | false
			false  | false | false  | false | true  | true   | true  | false
			false  | false | false  | false | false | true   | true  | true 
			true   | true  | false  | true  | false | false  | false | false
			false  | true  | true   | false | true  | false  | false | false
			false  | false | true   | true  | false | true   | false | false
			false  | false | false  | true  | true  | false  | true  | false
			false  | false | false  | false | true  | true   | false | true
			true   | false | false  | false | false | true   | true  | false
			true   | false | false  | true  | false | true   | false | false
			false  | true  | false  | false | true  | false  | true  | false
			false  | false | true   | false | false | true   | false | true
			false  | true  | false  | true  | false | false  | true  | false
			false  | false | true   | false | true  | false  | false | true
			true   | false | false  | true  | false | false  | true  | false
			true   | false | true   | false | false | false  | false | false
			false  | true  | false  | true  | false | false  | false | false
			false  | false | true   | false | true  | false  | false | false
			false  | false | false  | true  | false | true   | false | false
			false  | false | false  | false | true  | false  | true  | false
			false  | false | false  | false | false | true   | false | true
			false  | true  | false  | true  | false | false  | false | false
			false  | true  | false  | false | true  | false  | false | false
			false  | false | true   | false | false | true   | false | false
			false  | false | false  | true  | false | false  | true  | false
			false  | false | false  | false | true  | false  | false | true
			true   | false | false  | false | false | true   | false | false
			true   | false | false  | true  | false | false  | false | false
			false  | true  | false  | false | false | false  | true  | false
			false  | false | true   | false | false | false  | false | true
			false  | true  | false  | true  | false | false  | false | false
			false  | false | true   | false | false | false  | false | true
			true   | false | false  | false | false | false  | true  | false
	}
	
	def 'says a dead cell with three live neighbours will become alive'() {
		given:
			interaction {
				aCell.alive >> false
				stubLifeState(nwLife, nLife, neLife, wLife, eLife, swLife, sLife, seLife)
			}
		expect:
			lifeRule.willBeAlive(aCell)
		
		where:
			nwLife | nLife | neLife | wLife | eLife | swLife | sLife | seLife
			true   | true  | true   | false | false | false  | false | false
			false  | true  | true   | true  | false | false  | false | false
			false  | false | true   | true  | true  | false  | false | false
			false  | false | false  | true  | true  | true   | false | false
			false  | false | false  | false | true  | true   | true  | false
			false  | false | false  | false | false | true   | true  | true 
			true   | true  | false  | true  | false | false  | false | false
			false  | true  | true   | false | true  | false  | false | false
			false  | false | true   | true  | false | true   | false | false
			false  | false | false  | true  | true  | false  | true  | false
			false  | false | false  | false | true  | true   | false | true
			true   | false | false  | false | false | true   | true  | false
			true   | false | false  | true  | false | true   | false | false
			false  | true  | false  | false | true  | false  | true  | false
			false  | false | true   | false | false | true   | false | true
			false  | true  | false  | true  | false | false  | true  | false
			false  | false | true   | false | true  | false  | false | true
			true   | false | false  | true  | false | false  | true  | false
	}
}
