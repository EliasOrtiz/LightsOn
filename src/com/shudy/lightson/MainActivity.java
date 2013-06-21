package com.shudy.lightson;

import com.google.analytics.tracking.android.EasyTracker;

import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends Activity {
	
	private boolean lighton;
	private boolean lightint;
	
	private ImageButton button;
	private ToggleButton intermittent;
	private Camera camera;
	private Parameters params;
	private TextView textView;
	private Runnable runable_on;
	private Runnable runable_off;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		button = (ImageButton)findViewById(R.id.Button1);
		intermittent = (ToggleButton)findViewById(R.id.toggleIntermitent);
		textView = (TextView)findViewById(R.id.textview1);
		textView.setVisibility(View.INVISIBLE);
		lighton = false;
		lightint = false;
		
		try {
			camera = Camera.open();
			params = camera.getParameters();
		}catch(Exception e) {
		}
		
		runable_on = new Runnable() {
			
			@Override
			public void run() {
				
				params.setFlashMode(Parameters.FLASH_MODE_TORCH);
				
				
			}
		};
		
		runable_off = new Runnable() {
			
			@Override
			public void run() {
				params.setFlashMode(Parameters.FLASH_MODE_OFF);				
			}
		};
		
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!lighton) {
					params.setFlashMode(Parameters.FLASH_MODE_TORCH);
					textView.setVisibility(View.VISIBLE);
					
				}
				else
				{
					params.setFlashMode(Parameters.FLASH_MODE_OFF);
					textView.setVisibility(View.INVISIBLE);
				}
				camera.setParameters(params);
				lighton = !lighton;
				camera.startPreview();
			}
		});
		intermittent.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!lightint) {
				}
			}
		});
		EasyTracker.getInstance().activityStart(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onStop() {
		if(camera != null) {
			camera.release();
		}
		super.onStop();
		EasyTracker.getInstance().activityStop	(this);
	}
	
}
