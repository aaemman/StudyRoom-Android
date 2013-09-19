package canal5FrameworkLibrary;

import java.lang.reflect.Field;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

/*
 * 
 * 
 * a thread that separates the json objects
 */

public class JSONParser {

	public Object parse(JSONObject responseObject, String responseType) {

		// Create a new model object based on the responseType
		Object parsedObject = createObject(responseType);

		// Get a list of the attributes of the model object to use as key values
		// for JSON parsing

		Class<?> clazz = parsedObject.getClass();

		for (Field field : clazz.getDeclaredFields()) {
			field.setAccessible(true);
			if (responseObject.optJSONArray(field.getName()) != null) {
				try {
					JSONArray responseArray = responseObject.getJSONArray(field
							.getName());
					ArrayList<Object> nestedArray = new ArrayList<Object>();
					for (int i = 0; i < responseArray.length(); i++) {
						nestedArray.add(new JSONParser().parse(
								responseObject.getJSONObject(field.getName()),
								field.getName()));
					}

					try {
						field.set(parsedObject, nestedArray);
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else if (responseObject.optJSONObject(field.getName()) != null) {
				// if the current field is actually a nested JSON, create a new
				// JSONParser and deal with it

				try {
					field.set(parsedObject, new JSONParser().parse(
							responseObject.getJSONObject(field.getName()),
							field.getName()));
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else if (responseObject.opt(field.getName().toString()) != null) {
				try {
					Log.i(this.getClass().getSimpleName(), field.getName());
					field.set(parsedObject, responseObject.get(field.getName()));
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

		// return parsed object
		return parsedObject;

	}

	private Object createObject(String className) {
		Object object = null;
		try {
			Class classDefinition = Class.forName(className);
			object = classDefinition.newInstance();
		} catch (InstantiationException e) {
			System.out.println(e);
		} catch (IllegalAccessException e) {
			System.out.println(e);
		} catch (ClassNotFoundException e) {
			System.out.println(e);
		}
		return object;
	}

}
