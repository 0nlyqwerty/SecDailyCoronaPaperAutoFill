package com.bearguy.secdailycoronapaperautofill;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
	final String TAG = "MAIN_ACTIVITY";
	private WebView mWebView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mWebView = findViewById(R.id.activity_main_webView);
		WebSettings webSettings = mWebView.getSettings();
		webSettings.setJavaScriptEnabled(true);

		mWebView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				super.onProgressChanged(view, newProgress);

				if (newProgress == 100) {
					Log.d(TAG,"loading complete !");
					setValues(view);
				}
			}
		});
		Intent intent = getIntent();
		String action = intent.getAction();
		Uri data = intent.getData();
		Log.d(TAG, "received intent:" + action + "," + data);

		if(data != null){
			mWebView.loadUrl(data.toString());	// 주어진 Url 을 mWebView 에 load
		}

		Button btn = findViewById(R.id.btn_set_information);
		btn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View view) {
				Log.d(TAG,"Set button clicked!");
				setValues(mWebView);
			}
		});
	}

	void setValues(WebView view){
		Log.d(TAG,"Set Values!");
		view.evaluateJavascript("(function() { document.querySelector(\"input[value=SEC]\").click()})()", null);
		view.evaluateJavascript("(function() { document.getElementById(\"userName\").value=\"1\"})()", null);
		view.evaluateJavascript("(function() { document.getElementById(\"empNo\").value=\"4\"})()", null);
		view.evaluateJavascript("(function() { document.getElementById(\"telNumber\").value=\"7\"})()", null);
	}
}