package module4;

import de.fhpotsdam.unfolding.data.PointFeature;
import processing.core.PGraphics;

/** Implements a visual marker for land earthquakes on an earthquake map
 * 
 * @author UC San Diego Intermediate Software Development MOOC team
 * @author Risto Mononen
 *
 */
public class LandQuakeMarker extends EarthquakeMarker {
	
	
	public LandQuakeMarker(PointFeature quake) {
		
		// calling EarthquakeMarker constructor
		super(quake);
		
		// setting field in earthquake marker
		isOnLand = true;
	}


	@Override
	public void drawEarthquake(PGraphics pg, float x, float y) {
		pg.pushStyle();
		pg.ellipse(x, y, radius, radius);
		pg.popStyle();
	}
	

	// Get the country the earthquake is in
	public String getCountry() {
		return (String) getProperty("country");
	}


	public static void addShape(PGraphics pg, int x, int y) {
		pg.pushStyle();
		pg.ellipse(x, y, DEF_RADIUS, DEF_RADIUS);
		pg.popStyle();
	}



		
}