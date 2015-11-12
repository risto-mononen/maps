package module4;

import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import processing.core.PGraphics;

/** Implements a visual marker for earthquakes on an earthquake map
 * 
 * @author UC San Diego Intermediate Software Development MOOC team
 * @author Risto Mononen
 *
 */
public abstract class EarthquakeMarker extends SimplePointMarker
{
	
	// Did the earthquake occur on land?  This will be set by the subclasses.
	protected boolean isOnLand;

	// The radius of the Earthquake marker
	// You will want to set this in the constructor, either
	// using the thresholds below, or a continuous function
	// based on magnitude. 
	protected float radius;

	protected int r;

	protected int g;

	protected int b;

	protected static final float DEF_RADIUS = 9;
	
	
	/** Greater than or equal to this threshold is a moderate earthquake */
	public static final float THRESHOLD_MODERATE = 5;
	/** Greater than or equal to this threshold is a light earthquake */
	public static final float THRESHOLD_LIGHT = 4;

	/** Greater than or equal to this threshold is an intermediate depth */
	public static final float THRESHOLD_INTERMEDIATE = 70;
	/** Greater than or equal to this threshold is a deep depth */
	public static final float THRESHOLD_DEEP = 300;

	// ADD constants for colors

	
	// abstract method implemented in derived classes
	public abstract void drawEarthquake(PGraphics pg, float x, float y);
	
	// constructor
	public EarthquakeMarker (PointFeature feature) 
	{
		super(feature.getLocation());
		// Add a radius property and then set the properties
		java.util.HashMap<String, Object> properties = feature.getProperties();
		float magnitude = Float.parseFloat(properties.get("magnitude").toString());
		properties.put("radius", 2*magnitude );
		setProperties(properties);
		this.radius = 1.75f*getMagnitude();
	}
	

	// calls abstract method drawEarthquake and then checks age and draws X if needed
	public void draw(PGraphics pg, float x, float y) {
		// save previous styling
		pg.pushStyle();
			
		// determine color of marker from depth
		final Float depth = getDepth();
		colorDetermine(depth);
		
		// call abstract method implemented in child class to draw marker shape
		drawEarthquake(pg, x, y);
		
		// OPTIONAL TODO: draw X over marker if within past day		
		
		// reset to previous styling
		pg.popStyle();
		
	}
	
	// determine color of marker from depth
	// We suggest: Deep = red, intermediate = blue, shallow = yellow
	// But this is up to you, of course.
	// You might find the getters below helpful.
	protected void colorDetermine(Float depth) {
		if (depth < THRESHOLD_LIGHT) {
			r=0xff;
			g=0xff;
			b=0x00;
		} else if (depth < THRESHOLD_INTERMEDIATE) {
			r=0x00;
			g=0x00;
			b=0xff;
		} else {
			r=0xff;
			g=0x00;
			b=0x00;
		}
	}
	
	
	/*
	 * getters for earthquake properties
	 */
	
	public float getMagnitude() {
		return Float.parseFloat(getProperty("magnitude").toString());
	}
	
	public float getDepth() {
		return Float.parseFloat(getProperty("depth").toString());	
	}
	
	public String getTitle() {
		return (String) getProperty("title");	
		
	}
	
	public float getRadius() {
		return Float.parseFloat(getProperty("radius").toString());
	}
	
	public boolean isOnLand()
	{
		return isOnLand;
	}


	public static void addKey(int x1, int x2, int y, int gap, EarthquakeCityMap earthquakeCityMap) {
		PGraphics pg = earthquakeCityMap.g;
		LandQuakeMarker quake = LandQuakeMarker.getInstance();
		quake.radius = DEF_RADIUS;

		quake.colorDetermine(THRESHOLD_LIGHT-1);
		quake.drawEarthquake(pg, x1, y);
		pg.fill(0);
		earthquakeCityMap.text("Shallow", x2, y);
		
		y += gap;
		quake.colorDetermine(THRESHOLD_INTERMEDIATE-1);
		quake.drawEarthquake(pg, x1, y);
		pg.fill(0);
		earthquakeCityMap.text("Intermediate", x2, y);
		
		y += gap;
		quake.colorDetermine(THRESHOLD_DEEP+1);
		quake.drawEarthquake(pg, x1, y);
		pg.fill(0);
		earthquakeCityMap.text("Deep", x2, y);
	}
	
	
}

