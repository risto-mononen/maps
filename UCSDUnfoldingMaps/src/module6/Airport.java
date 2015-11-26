package module6;

import java.util.HashSet;
import java.util.Set;

import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.data.ShapeFeature;

public class Airport implements Comparable<Airport> {
	PointFeature feature;

	@Override
	public String toString() {
		return String.format("Airport %s, %s [routes=%d, Id=%s]", feature.getProperty("name"), 
				feature.getProperty("city"), routes.size(), feature.getId());
	}

	public Airport(PointFeature feature) {
		this.feature = feature;
		routes = new HashSet<ShapeFeature>();
	}

	Set<ShapeFeature> routes;

	@Override
	public int compareTo(Airport o) {
		return routes.size() - o.routes.size();
	}
}
