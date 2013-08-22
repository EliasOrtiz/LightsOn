package com.shudy.lightson.morse;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;

public class Morse {

	private Context mContext;
	private Parameters mParams;
	private Camera mCamera;

	public Morse(Context context, Parameters params, Camera camera) {
		super();
		this.mContext = context;
		this.mParams = params;
		this.mCamera = camera;
	}

	public void ligthOn() {
		this.mParams.setFlashMode(Parameters.FLASH_MODE_TORCH);
		this.mCamera.setParameters(mParams);
		this.mCamera.startPreview();
	}

	public void ligthOff() {
		this.mParams.setFlashMode(Parameters.FLASH_MODE_OFF);
		this.mCamera.setParameters(mParams);
		this.mCamera.startPreview();
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

	public void m_A(Context ctx) {
		dot();
		dash();
	}

	public void m_B(Context ctx) {

	}
}