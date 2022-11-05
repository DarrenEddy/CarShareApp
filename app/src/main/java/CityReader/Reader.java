package CityReader;

import android.renderscript.ScriptGroup;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Hashtable;


public class Reader {

    public static String[] citiesArray(InputStream is)
    {
    	
    	String line ="";
    	String[] out = new String[] {};
    	ArrayList<String> formatArrayL = new ArrayList<String>();
		BufferedReader br= new BufferedReader(new InputStreamReader(is));
		try
        {
        	String[] header = br.readLine().split("\",\"");

        	while((line =br.readLine())!= null) 
        	{
        		String[] city = line.split("\",\"");

				city[0] = city[0].split("\"")[1];
				String temp = city[0]+", "+city[3]; //FORMAT FOR HOW CITIES LOOK
				formatArrayL.add(temp);

				//re build array for size of arraylist O(2n)
				out = new String[formatArrayL.size()];
				for (int i= 0; i<formatArrayL.size();i++) 
				{
					out[i] = formatArrayL.get(i);
				}
        	}
        	return out;
        }

        catch (Exception e)
        {
			System.out.println(e.toString());
        }
        return out;
    }

	public static ArrayList<City> getCityArrayList(InputStream is)
	{
		// id 11, City_Ascii 1,prov 3, country N/A, lat 4, lng 5

		String line ="";

		ArrayList<City> cityTable = new ArrayList<>();
		BufferedReader br= new BufferedReader(new InputStreamReader(is));
		try {
			String[] header = br.readLine().split("\",\"");

			while((line =br.readLine())!= null)
			{
				String[] city = line.split("\",\"");
				//add here
				//city[0] = city[0].split("\"")[1];
				city[11] = city[11].split("\"")[0];

				cityTable.add(new City(
						Integer.parseInt(city[11]),
						city[1],
						city[3],
						"Canada",
						Float.parseFloat(city[4]),
						Float.parseFloat(city[5])
				));




			}


		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return cityTable;
	}

}
