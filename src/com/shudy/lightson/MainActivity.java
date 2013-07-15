package com.shudy.lightson;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.google.analytics.tracking.android.EasyTracker;

public class MainActivity extends Activity {

	private boolean lighton;
	private boolean lightsos;

	private ImageButton button;
	private Button but_sos;
	private Camera camera;
	private Parameters params;
	private TextView textView;
	// admob
	private AdView ad;
	private AdRequest adRequest;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		ad = new AdView(this, AdSize.BANNER, "a151cac0b8cb6c2");
		LinearLayout layout_publi = (LinearLayout) findViewById(R.id.LayoutPubli);
		layout_publi.addView(ad);

		// ADMOB
		adRequest = new AdRequest();

		setTestMode(true);
		ad.loadAd(adRequest);

		button = (ImageButton) findViewById(R.id.Button1);
		but_sos = (Button) findViewById(R.id.toggleSos);
		textView = (TextView) findViewById(R.id.textview1);
		textView.setVisibility(View.INVISIBLE);
		lighton = false;
		lightsos = false;

		try {
			camera = Camera.open();
			params = camera.getParameters();
		} catch (Exception e) {
		}

		/*
		 * runable_on = new Runnable() {
		 * 
		 * @Override public void run() {
		 * params.setFlashMode(Parameters.FLASH_MODE_TORCH);
		 * camera.setParameters(params); camera.startPreview(); } };
		 * 
		 * runable_off = new Runnable() {
		 * 
		 * @Override public void run() {
		 * params.setFlashMode(Parameters.FLASH_MODE_OFF);
		 * camera.setParameters(params); camera.startPreview(); } };
		 */

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
				SosAsyntask sos = new SosAsyntask();
				sos.execute();
				if (!lightsos) {
				} else {

				}
				lightsos = !lightsos;
			}
		});
		EasyTracker.getInstance().activityStart(this);
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
	protected void onStop() {
		if (camera != null) {
			camera.release();
		}
		super.onStop();
		EasyTracker.getInstance().activityStop(this);
	}

	@Override
	protected void onDestroy() {
		ad.destroy();
		super.onDestroy();
	}

	public void ligthOn() {
		params.setFlashMode(Parameters.FLASH_MODE_TORCH);
		camera.setParameters(params);
		camera.startPreview();
	}

	public void ligthOff() {
		params.setFlashMode(Parameters.FLASH_MODE_OFF);
		camera.setParameters(params);
		camera.startPreview();
	}

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
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
			/*
			 * try{
			 * 
			 * 
			 * Thread.sleep(250);
			 * 
			 * Thread.sleep(250); //S ligthOn(); Thread.sleep(250); ligthOff();
			 * Thread.sleep(500);
			 * 
			 * ligthOn(); Thread.sleep(250); ligthOff(); Thread.sleep(500);
			 * 
			 * ligthOn(); Thread.sleep(250); ligthOff(); Thread.sleep(500);
			 * 
			 * //O ligthOn(); Thread.sleep(1000); ligthOff(); Thread.sleep(500);
			 * 
			 * ligthOn(); Thread.sleep(1000); ligthOff(); Thread.sleep(500);
			 * 
			 * ligthOn(); Thread.sleep(1000); ligthOff(); Thread.sleep(500); //S
			 * ligthOn(); Thread.sleep(250); ligthOff(); Thread.sleep(500);
			 * 
			 * ligthOn(); Thread.sleep(250); ligthOff(); Thread.sleep(500);
			 * 
			 * ligthOn(); Thread.sleep(250); ligthOff(); Thread.sleep(500);
			 * 
			 * }catch(Exception e){ return false; }
			 */

			return true;
		}

		@Override
		public void onPostExecute(Boolean hasLoggedAccount) {
			if (hasLoggedAccount) {
			} else {
			}
		}
	}

}
