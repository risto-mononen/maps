package module6;

import java.util.HashSet;
import java.util.Set;

import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.data.ShapeFeature;
import processing.core.PGraphics;

public class Airport extends PointFeature implements Comparable<Airport> {
	private static final int WHITE = 255;
	private AirportMarker marker;

	@Override
	public String toString() {
		return String.format("Airport %s, %s [routes=%d, Id=%s]", getProperty("name"), getProperty("city"), routes.size(), 
				getId());
	}

	public Airport(PointFeature feature, AirportMarker m) {
		super(feature.location);
		properties = feature.properties;
		setId(feature.getId());
		setMarker(m);
		routes = new HashSet<ShapeFeature>();
	}

	Set<ShapeFeature> routes;
	private static int maxRoutes = 0;
	@Override
	public int compareTo(Airport o) {
		return routes.size() - o.routes.size();
	}

	public void draw(PGraphics pg, float x, float y) {
		// Save previous drawing style
		pg.pushStyle();
		maxRoutes = Math.max(routes.size(), maxRoutes);
		int shade = WHITE*routes.size()/maxRoutes;
		pg.fill(shade);
		float radius = routes.size();
		pg.ellipse(location.x, location.y, radius, radius);
		// Restore previous drawing style
		pg.popStyle();
	}

	public AirportMarker getMarker() {
		return marker;
	}

	public void setMarker(AirportMarker marker) {
		this.marker = marker;
	}

}
