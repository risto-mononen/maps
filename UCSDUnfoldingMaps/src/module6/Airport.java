package module6;

import java.util.HashSet;
import java.util.Set;

import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.data.ShapeFeature;
import de.fhpotsdam.unfolding.geo.Location;

public class Airport extends PointFeature implements Comparable<Airport> {

	private String id;

	@Override
	public String toString() {
		return "Airport [routes=" + routes.size() + ", Id=" + id + "]";
	}

	public Airport(PointFeature feature) {
		super(feature.location);
		id = feature.getId();
		routes = new HashSet<ShapeFeature>();
	}

	Set<ShapeFeature> routes;

	@Override
	public int compareTo(Airport o) {
		return routes.size() - o.routes.size();
	}
}
