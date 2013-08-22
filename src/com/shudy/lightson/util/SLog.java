package com.shudy.lightson.util;

public class SLog {
	public static final boolean LOG_ENABLED = true;
	private static String logTag = null;

	public static String geSLogTag() {
		if (logTag == null) {
			return (SLog.class.getPackage().getName());
		}
		return logTag;
	}

	public static void seSLogTag(String logTag) {

		SLog.logTag = logTag;
	}

	public static void v(String verbose) {
		if (LOG_ENABLED) {
			android.util.Log.v(geSLogTag(), verbose);
		}
	}

	public static void d(String debug) {
		if (LOG_ENABLED) {
			android.util.Log.d(geSLogTag(), debug);
		}
	}

	public static void i(String info) {
		if (LOG_ENABLED) {
			android.util.Log.i(geSLogTag(), info);
		}
	}

	public static void w(String warning) {
		if (LOG_ENABLED) {
			android.util.Log.w(geSLogTag(), warning);
		}
	}

	public static void e(String error) {
		if (LOG_ENABLED) {
			android.util.Log.e(geSLogTag(), error);
		}
	}

}