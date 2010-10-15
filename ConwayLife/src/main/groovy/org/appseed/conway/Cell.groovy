package org.appseed.conway

interface Cell {
	Cell getNorth()
	Cell getNortheast()
	Cell getNorthwest()
	Cell getEast()
	Cell getWest()
	Cell getSouth()
	Cell getSoutheast()
	Cell getSouthwest()
	
	boolean getAlive()
	
	Cell evolve()
}
