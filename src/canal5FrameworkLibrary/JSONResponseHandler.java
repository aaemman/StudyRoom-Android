package canal5FrameworkLibrary;

import java.util.ArrayList;
import java.util.List;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JSONResponseHandler {
	/*
	 * 
	 * and intermediate class to handle the splitting of the json objects when
	 * we do an index call
	 */

	private final JSONParser jsonParser;

	public JSONResponseHandler() {
		jsonParser = new JSONParser();
	}

	public JSONResponseHandler(JSONParser customJSONParser) {
		this.jsonParser = customJSONParser;
	}

	public Object[] split(String response, String key, String responseType) {

		if (response != null) {
			JSONObject data;
			JSONArray responseArray;
			JSONObject responseObject;
			ArrayList<Object> parsedResponseArray = new ArrayList<Object>();

			try {
				Log.i(this.getClass().getSimpleName(), "getting response data");

				data = ((JSONObject) new JSONObject(response))
						.getJSONObject("data");

				Log.i(this.getClass().getSimpleName(),
						"data = " + data.toString());

				if (data.optJSONArray(key) != null) {
					responseArray = (JSONArray) data.get(key);

					for (int i = 0; i < responseArray.length(); i++) {

						Log.i(this.getClass().getSimpleName(),
								"getting response item #" + i);
						responseObject = (JSONObject) responseArray.get(i);

						Log.i(this.getClass().getSimpleName(),
								"response item #" + i + "="
										+ responseObject.toString());

						parsedResponseArray.add(jsonParser.parse(
								responseObject, responseType));

					}
				} else if (data.optJSONObject(key) != null) {
					parsedResponseArray.add(jsonParser.parse(
							data.optJSONObject(key), responseType));

					Log.i(this.getClass().getSimpleName(),
							jsonParser.parse(data.optJSONObject(key),
									responseType)
									+ "---------------------------------");
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Object[] responseObjects = new Object[0];
			return parsedResponseArray.toArray(responseObjects);
		}
		return null;
	}

}
