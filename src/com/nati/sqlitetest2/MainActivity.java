package com.nati.sqlitetest2;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	TextView mainTextView;
	MyPrePopulatedDBHelper mdh;
	TextView button;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mainTextView=(TextView)findViewById(R.id.list_view_text);
		button=(TextView)findViewById(R.id.button1);
		pull();
	}
	void pull()
	{
		mdh=new MyPrePopulatedDBHelper(getApplicationContext(), "tik");
		ArrayList<String> list=mdh.getAllLocations();
		for(int i=0;i<10;i++)
		{
			if(list.size()>i)
			{
				mainTextView.append(list.get(i)+"\n");
			}
		}
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ArrayList<String> list=mdh.getAllLocations();
				for(int i=0;i<10;i++)
				{
					if(list.size()>i)
					{
						mainTextView.append(list.get(i));
					}
				}
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
