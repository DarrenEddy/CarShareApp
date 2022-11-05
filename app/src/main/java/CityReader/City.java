package CityReader;

import java.util.Locale;

public class City {
	int id;
	String name;
	String province;
	String country;
	float lat;
	float lng;


	public City(int cityId, String cityName, String prov, String countryName, float cityLat, float cityLng)
	{
		id=cityId;
		name=cityName;
		province = prov;
		country=countryName;
		lat=cityLat;
		lng=cityLng;
	}
	public City()
	{
		id=0;
		name="";
		province = "";
		country="";
		lat=0;
		lng=0;
	}

	public int getId(){return id;}
	public String getName(){return name;}
	public String latAndLon()
	{
		return "lat: " + this.lat + ", lon:" + this.lng;
	}



	public double distance(City other)
	{
		int R = 6371;// Earth Radius in KM
		double y1 = this.lat * Math.PI/180;
		double y2 = other.lat * Math.PI/180;

		double y = (y2-y1);
		double x = (other.lng-this.lng)*Math.PI/180;

		double a = Math.sin(y/2)*Math.sin(y/2) +
				Math.cos(y1)*Math.cos(y2)*Math.sin(x/2)*Math.sin(x/2);

		double c = 2 * Math.atan2(Math.sqrt(a),Math.sqrt(1-a));
		return R*c;
	}

	public int compareForId(String city)
	{
		if (city.toLowerCase().equals(this.name.toLowerCase())){return this.id;}
		return 0;
	}

	@Override
	public String toString() {
		return "City [name=" + name + ", province=" + province + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {return true;}
		if (!(obj instanceof City)) {return false;}
		City cityObj = (City) obj;
		if (cityObj.id == this.id) {return true;}

		return false;
	}

}