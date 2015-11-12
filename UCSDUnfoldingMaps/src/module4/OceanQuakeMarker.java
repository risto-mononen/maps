package module4;

import de.fhpotsdam.unfolding.data.PointFeature;
import processing.core.PGraphics;

/** Implements a visual marker for ocean earthquakes on an earthquake map
 * 
 * @author UC San Diego Intermediate Software Development MOOC team
 * @author Risto Mononen
 *
 */
public class OceanQuakeMarker extends EarthquakeMarker {
	
	public OceanQuakeMarker(PointFeature quake) {
		super(quake);
		
		// setting field in earthquake marker
		isOnLand = false;
	}
	

	@Override
	public void drawEarthquake(PGraphics pg, float x, float y) {
		pg.fill(r, g, b);
		pg.rect(x, y, radius, radius);
	}


	public static void addShape(PGraphics pg, int x, int y) {
		pg.rect(x-DEF_RADIUS/2, y-DEF_RADIUS/2, DEF_RADIUS, DEF_RADIUS);
	}	


	

}
