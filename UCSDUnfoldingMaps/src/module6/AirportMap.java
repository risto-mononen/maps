package module6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.data.ShapeFeature;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.SimpleLinesMarker;
import de.fhpotsdam.unfolding.utils.MapUtils;
import parsing.ParseFeed;
import processing.core.PApplet;

/** An applet that shows airports (and routes)
 * on a world map.  
 * @author Adam Setters and the UC San Diego Intermediate Software Development
 * MOOC team
 *
 */
public class AirportMap extends PApplet {
	
	UnfoldingMap map;
	private List<Marker> airportList;
	List<Marker> routeList;
	Map<Integer, Airport> airports;
	
	public void setup() {
		// setting up PAppler
		size(800,600, OPENGL);
		
		// setting up map and default events
		map = new UnfoldingMap(this, 50, 50, 750, 550);
		MapUtils.createDefaultEventDispatcher(this, map);
		
		// get features from airport data
		List<PointFeature> features = ParseFeed.parseAirports(this, "airports.dat");
		
		// list for markers, hashmap for quicker access when matching with routes
		airportList = new ArrayList<Marker>();
		airports = new HashMap<Integer, Airport>(features.size());
		HashMap<Integer, Location> locations = new HashMap<Integer, Location>();
		
		// create markers from features
		for(PointFeature feature : features) {
			AirportMarker m = new AirportMarker(feature);
	
			m.setRadius(5);
			airportList.add(m);
			
			// put airport in hashmap with OpenFlights unique id for key
			locations.put(Integer.parseInt(feature.getId()), feature.getLocation());
			airports.put(Integer.parseInt(feature.getId()), new Airport(feature));
		}
		
		
		// parse route data
		List<ShapeFeature> routes = ParseFeed.parseRoutes(this, "routes.dat");
		routeList = new ArrayList<Marker>();
		for(ShapeFeature route : routes) {
			
			// get source and destination airportIds
			int source = Integer.parseInt((String)route.getProperty("source"));
			int dest = Integer.parseInt((String)route.getProperty("destination"));
			
			// get locations for airports on route
			if(locations.containsKey(source) && locations.containsKey(dest)) {
				route.addLocation(locations.get(source));
				route.addLocation(locations.get(dest));
				airports.get(source).routes.add(route);
				airports.get(dest).routes.add(route);
//				System.out.println(source + " to " + dest);
			}
		}
		
		// Add top ten airports to routeList
		// TODO: clumsy picture, make it interactive
		SortedSet<Airport> aports = new TreeSet<Airport>(Comparator.reverseOrder());
		aports.addAll(airports.values());
		int topPorts = 10;
		for(Airport port : aports) {
			System.out.println(port);
			if(topPorts-- <= 0)
				break;
			int routeCount = 10;
			for(ShapeFeature route : port.routes) {
				if(routeCount-- <= 0)
					break;	
				SimpleLinesMarker sl = new SimpleLinesMarker(route.getLocations(), route.getProperties());
				System.out.println(sl.getProperties());
				routeList.add(sl);
			}
		}		
		
		
		//UNCOMMENT IF YOU WANT TO SEE ALL ROUTES
		map.addMarkers(routeList);
		
//		map.addMarkers(airportList);
		
	}
	
	public void draw() {
		background(0);
		map.draw();
		
	}
	

}
