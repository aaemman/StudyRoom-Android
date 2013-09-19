package canal5FrameworkLibrary;

import java.io.ByteArrayOutputStream;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;

public class Canal5 extends Application {
	 private static Canal5 s_instance;

	    public Canal5 ()
	    {
	        s_instance = this;
	    }

	    public static Context getContext()
	    {
	        return s_instance;
	    }

	    public static String staticGetString(int resId)
	    {
	        return s_instance.getString(resId);       
	    }
	    
		public static String encodePhoto(String fileUriString) {
			Uri fileUri = Uri.parse(fileUriString);
			Bitmap bm = BitmapFactory.decodeFile(fileUri.getPath());
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			bm.compress(Bitmap.CompressFormat.JPEG, 70, baos); 
			byte[] b = baos.toByteArray();

			String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);

			return encodedImage;
		}

		public static void startNewActivity(Activity CurrentActivity,
				Class<?> NextActivity) {
			// TODO Auto-generated method stub
				// TODO Auto-generated method stub
				Intent myIntent = new Intent(CurrentActivity, NextActivity);
				CurrentActivity.startActivity(myIntent);
			
		}

		public static Resources staticGetResources() {
			// TODO Auto-generated method stub
			return s_instance.getResources();
		}

		
}
