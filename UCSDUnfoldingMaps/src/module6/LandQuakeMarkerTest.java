/**
 * 
 */
package module6;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.Marker;
import parsing.ParseFeed;
import processing.core.PApplet;

/**
 * @author risto
 *
 */
public class LandQuakeMarkerTest {

	
	private ArrayList<LandQuakeMarker> quakeMarkers;

	@Before
	public void setup()
	{
		String earthquakesURL = "quiz2.atom";
		PApplet papplet = new PApplet();
		List<PointFeature> earthquakes = ParseFeed.parseEarthquake(papplet, earthquakesURL);
	    quakeMarkers = new ArrayList<LandQuakeMarker>();
	    
	    for(PointFeature feature : earthquakes) {
		    quakeMarkers.add(new LandQuakeMarker(feature));
	    }
	}

	@Test
	public void testCompareTo() {
		LandQuakeMarker a = quakeMarkers.get(0);
		LandQuakeMarker b = quakeMarkers.get(1);
		b.setProperty("magnitude", a.getMagnitude() + 2.3);
		assertTrue(a.compareTo(a) == 0);
		assertTrue(a.compareTo(b) < 0);
		assertTrue(b.compareTo(a) < 0);
	}

}
