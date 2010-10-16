package org.appseed.tests.conway

import org.appseed.conway.ConwayLifeRule;
import org.appseed.conway.Cell;

import spock.lang.Specification;

class AConwayLifeRule extends Specification {
	def allPossibleStatesByAliveCount;
	
	def setupSpec() {
		allPossibleStatesByAliveCount=[:]
		def binary=[false, true];
		def allPossibleStates=[binary,binary,binary,binary,binary,binary,binary,binary].combinations();
		allPossibleStates.each { state ->
			def lifeCount = state.count(true)
			def statesWithCount=allPossibleStatesByAliveCount[lifeCount]
			if(!statesWithCount){
				statesWithCount=[]
				allPossibleStatesByAliveCount[lifeCount]=statesWithCount
			}
			statesWithCount.add(state)
		}
	}
	
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

	def stubLifeState(lifeState) {
		
		aCell.north >> nCell
		aCell.northeast >> neCell
		aCell.northwest >> nwCell
		aCell.east >> eCell
		aCell.west >> wCell
		aCell.south >> sCell
		aCell.southeast >> seCell
		aCell.southwest >> swCell

		def (nwLife, nLife, neLife, wLife, eLife, swLife, sLife, seLife)=lifeState
		
		nCell.alive >> nLife
		sCell.alive >> sLife
		eCell.alive >> eLife
		wCell.alive >> wLife
		neCell.alive >> neLife
		nwCell.alive >> nwLife
		seCell.alive >> seLife
		swCell.alive >> swLife
	}
	
	def lifeStatesWithNeighborsAlive(Object[] lifeCounts) {
		def r=[]
		for(lifeCount in lifeCounts)
			r.addAll allPossibleStatesByAliveCount[lifeCount]
		return r
	}
	
	def 'says a cell with fewer than two live neighbours will become dead'() {
		given: 
			interaction {
				stubLifeState(lifeState)
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
			lifeState << lifeStatesWithNeighborsAlive(0, 1)
	}
	
	def 'says a cell with more than three live neighbours will become dead'() {
		given:
			interaction {
				stubLifeState(lifeState)
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
			lifeState << lifeStatesWithNeighborsAlive(4, 5, 6, 7, 8)
	}
	
	def 'says a live cell with two or three live neighbours will become alive'() {
		given:
			interaction {
				aCell.alive >> true
				stubLifeState(lifeState)
			}
		expect:
			lifeRule.willBeAlive(aCell)
		
		where:
			lifeState << lifeStatesWithNeighborsAlive(2, 3)
	}
	
	def 'says a dead cell with three live neighbours will become alive'() {
		given:
			interaction {
				aCell.alive >> false
				stubLifeState(lifeState)
			}
		expect:
			lifeRule.willBeAlive(aCell)
		
		where:
			lifeState << lifeStatesWithNeighborsAlive(3)
	}
}
