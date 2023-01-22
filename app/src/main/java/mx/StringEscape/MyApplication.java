package mx.StringEscape;

import android.app.Application;

public class MyApplication extends Application {
	private static MyApplication application;

	public static MyApplication getApplication() {
		return application;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		application = this;
		CrashHandler.init(this);
	}
}
