package com.example.studyroom;

import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import canal5FrameworkLibrary.PostDispatcher;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public void postQuestion(View v){
    	
    	EditText etTitle = (EditText)findViewById(R.id.etQuestionTitle);
    	EditText etDescription = (EditText)findViewById(R.id.etQuestionDescription);
    	
    	
    	
    	System.out.println("test post");
    	HashMap <String, String> postMap = new HashMap<String, String>();
    	
    	postMap.put("title", etTitle.getText().toString());
    	postMap.put("description", etDescription.getText().toString());
    	
    	PostDispatcher postQuestionDispatcher = new PostDispatcher("question", "model.question", postMap ,getString(R.string.questionsURL) , this);
    	System.out.println(postMap.toString());
    	postQuestionDispatcher.execute();
    }
    
}
