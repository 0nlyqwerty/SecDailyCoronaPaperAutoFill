package com.bearguy.secdailycoronapaperautofill;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {
	private WebView mWebView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		final String TAG = "BEAR";
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mWebView = findViewById(R.id.activity_main_webView);
		WebSettings webSettings = mWebView.getSettings();
		webSettings.setJavaScriptEnabled(true);

		mWebView.setWebChromeClient(new WebChromeClient() {
			@Override
			public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
				return super.onJsAlert(view, url, message, result); // super.onJsAlert는 false를 return한다
			}
		});
		Intent intent = getIntent();
		String action = intent.getAction();
		Uri data = intent.getData();
		Log.e(TAG,"bin "+action + ":"+data);

		if(data != null){
			mWebView.loadUrl(data.toString());
		}

	}
}