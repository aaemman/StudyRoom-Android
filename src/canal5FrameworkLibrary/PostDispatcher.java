package canal5FrameworkLibrary;

import java.util.HashMap;

import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;


public class PostDispatcher extends AsyncTask<String, Integer, Object[]> {

	private Context context;
	private String requestURL;
	private HashMap<String, String> postMap;
	private JSONObject postObject;
	private final String key, responseType;

	public PostDispatcher(String key, String responseType,
			HashMap<String, String> postMap, String requestURL, Context context) {
		this.postMap = postMap;
		this.context = context;
		this.requestURL = requestURL;
		this.key = key;
		this.responseType = responseType;

	}

	@Override
	protected Object[] doInBackground(String... params) {

		JSONBuilder postJSON = new JSONBuilder(key, postMap, requestURL,
				context);
		postObject = postJSON.build();

		JSONsendReceiver srJSON = new JSONsendReceiver(requestURL, postObject, context);

		JSONResponseHandler jsonResponseHandler = null;
//		= new JSONResponseHandler(
//				new customJSONParser());

		Object[] responseObjectArray = null ;
//		= jsonResponseHandler.split(srJSON.send(),
//				key, responseType);
		
		srJSON.send();

		
		return responseObjectArray;
	}

	@Override
	protected void onPostExecute(Object[] result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);

		if (result != null && result.length > 0) {

			if (result[0].getClass().getSimpleName().equals("String")) {

				SharedPreferences prefs = context.getSharedPreferences(
						"com.canal5.dropchat", Context.MODE_PRIVATE);
				SharedPreferences.Editor editor = prefs.edit();
				editor.putString("auth_token", result[0].toString());
				editor.commit();

				

				if (!(prefs.getString("auth_token", "").equals(""))) {
//					LoginActivity.progressDialog.dismiss();
//					LoginActivity.invalidCredentialsDialog.dismiss();
					Intent i = context.getPackageManager()
							.getLaunchIntentForPackage(
									context.getPackageName());
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					context.startActivity(i);
				}

			} else if (result[0].getClass().getSimpleName().equals("Drop")) {
//				Drop[] resultDrops = new Drop[result.length];

//				for (int i = 0; i < result.length; i++) {
//					resultDrops[i] = (Drop) result[i];
//				}

//				PubFeedActivity.lvPubFeedDrops
//						.setAdapter(new PubFeedArrayAdapter(context,
//								R.id.lvPubFeed, resultDrops));
			}
		}

	}

}