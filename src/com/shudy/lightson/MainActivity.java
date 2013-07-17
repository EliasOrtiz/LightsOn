package com.shudy.lightson;

import java.util.TooManyListenersException;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.google.analytics.tracking.android.EasyTracker;
import com.shudy.lightson.util.Constants;

public class MainActivity extends Activity {

	private boolean lighton;
	private boolean lightsos;

	private ImageButton button;
	private Button but_sos;
	private ToggleButton but_rotation_screen;
	private Camera camera;
	private Parameters params;
	private TextView textView;
	private SharedPreferences settings;
	private boolean allowed_screen_rotation;
	private LinearLayout layout_publi;
	// admob
	private AdView ad;
	private AdRequest adRequest;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		findViews();

		// ADMOB
		
		layout_publi.addView(ad);
		
		adRequest = new AdRequest();
		setTestMode(true);
		ad.loadAd(adRequest);
		//END ADMOB

		settings = getSharedPreferences(Constants.USER_PREFERENCES, 0);
		lighton = false;
		lightsos = false;

		try {
			camera = Camera.open();
			params = camera.getParameters();
		} catch (Exception e) {
			//"Error de camara
		}
		
		textView.setVisibility(View.INVISIBLE);		
		allowed_screen_rotation = settings.getBoolean(Constants.SCREEN_MOVE, true);
		screenRotation();
		
		setListeners();
		EasyTracker.getInstance().activityStart(this);
	}
	
	private void screenRotation() {
		if(allowed_screen_rotation == true ) {

			but_rotation_screen.setChecked(true);
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
		}
		else {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}
		
	}
	
	private void findViews() {
		ad = new AdView(this, AdSize.BANNER, "a151cac0b8cb6c2");
		layout_publi = (LinearLayout) findViewById(R.id.LayoutPubli);
		button = (ImageButton) findViewById(R.id.Button1);
		but_sos = (Button) findViewById(R.id.toggleSos);
		textView = (TextView) findViewById(R.id.textview1);
		but_rotation_screen = (ToggleButton) findViewById(R.id.but_screen_rotation);
		
	}
	private void setListeners() {
		
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!lighton) {
					params.setFlashMode(Parameters.FLASH_MODE_TORCH);
					textView.setVisibility(View.VISIBLE);

				} else {
					params.setFlashMode(Parameters.FLASH_MODE_OFF);
					textView.setVisibility(View.INVISIBLE);
				}
				camera.setParameters(params);
				lighton = !lighton;
				camera.startPreview();
			}
		});
		
		but_sos.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				textView.setVisibility(View.VISIBLE);
				SosAsyntask sos = new SosAsyntask();
				sos.execute();
				if (!lightsos) {
				} else {

				}
				lightsos = !lightsos;
			}
		});
		
		but_rotation_screen.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				allowed_screen_rotation  = isChecked;
				screenRotation();
			}
		});
	}

	private void setTestMode(boolean test) {

		if (test) {
			adRequest.addTestDevice(AdRequest.TEST_EMULATOR);
			// GALAXY S3 --Elías
			adRequest.addTestDevice("6D7C0138CC30E340358299979DDD11B9");
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	

	@Override
	protected void onPause() {
		SharedPreferences.Editor editor = settings.edit();
	    editor.putBoolean(Constants.SCREEN_MOVE, allowed_screen_rotation);
	    editor.commit();
		super.onPause();
	}

	@Override
	protected void onStop() {
		if (camera != null) {
			camera.release();
		}
		
		SharedPreferences.Editor editor = settings.edit();
	    editor.putBoolean(Constants.SCREEN_MOVE, allowed_screen_rotation);
	    editor.commit();
		
		super.onStop();
		EasyTracker.getInstance().activityStop(this);
	}

	@Override
	protected void onDestroy() {
		ad.destroy();
		super.onDestroy();
	}

	/**
	 * Lights On
	 */
	public void ligthOn() {
		params.setFlashMode(Parameters.FLASH_MODE_TORCH);
		camera.setParameters(params);
		camera.startPreview();
	}

	/**
	 * Lights Off
	 */
	public void ligthOff() {
		params.setFlashMode(Parameters.FLASH_MODE_OFF);
		camera.setParameters(params);
		camera.startPreview();
	}

	/**
	 * DASH
	 */
	public void dash() {
		try {
			ligthOn();
			Thread.sleep(750);
			ligthOff();
			Thread.sleep(250);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	/**
	 * DOT
	 */
	public void dot() {
		try {
			ligthOn();
			Thread.sleep(250);
			ligthOff();
			Thread.sleep(250);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * SEND S.O.S.
	 */
	private class SosAsyntask extends AsyncTask<Void, Void, Boolean> {

		@Override
		public Boolean doInBackground(Void... params) {
			try {
				dot();
				dot();
				dot();
				Thread.sleep(250);
				dash();
				dash();
				dash();
				Thread.sleep(250);
				dot();
				dot();
				dot();
			} catch (InterruptedException e) {
				e.printStackTrace();
				return false;
			}
			return true;
		}

		@Override
		public void onPostExecute(Boolean correcto) {
			textView.setVisibility(View.INVISIBLE);
			if (correcto) {
			} else {
			}
		}
	}
}
