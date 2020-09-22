package com.bearguy.secdailycoronapaperautofill;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
	final String TAG = "MAIN_ACTIVITY";
	private Context mContext;
	private WebView mWebView;

	private EditText et_userName;
	private EditText et_empNo;
	private EditText et_telNumber;
	private Button btn_set;

	private int progressTwiceCallCounter = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mContext = this;

		mWebView = findViewById(R.id.activity_main_webView);

		et_userName = findViewById(R.id.et_name);
		et_empNo = findViewById(R.id.et_employee_number);
		et_telNumber = findViewById(R.id.et_phone_number);

		String userName = PreferenceManager.getString(mContext, "USER_NAME");
		String empNo = PreferenceManager.getString(mContext, "EMPLOYEE_NUMBER");
		String telNo = PreferenceManager.getString(mContext, "PHONE_NUMBER");

		et_userName.setText(userName);
		et_empNo.setText(empNo);
		et_telNumber.setText(telNo);

		btn_set = findViewById(R.id.btn_set_information);
		btn_set.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View view) {
				Log.d(TAG, "Set button clicked!");

				PreferenceManager.setString(mContext, "USER_NAME", et_userName.getText().toString());
				PreferenceManager.setString(mContext, "EMPLOYEE_NUMBER", et_empNo.getText().toString());
				PreferenceManager.setString(mContext, "PHONE_NUMBER", et_telNumber.getText().toString());

				setValues(mWebView);
			}
		});


		WebSettings webSettings = mWebView.getSettings();
		webSettings.setJavaScriptEnabled(true);

		mWebView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				super.onProgressChanged(view, newProgress);

				if (newProgress == 100 && ++progressTwiceCallCounter == 2) {
					progressTwiceCallCounter = 0;
					Log.d(TAG, "loading complete !");
					setValues(view);
				}
			}
		});
		Intent intent = getIntent();
		String action = intent.getAction();
		Uri data = intent.getData();
		Log.d(TAG, "received intent:" + action + "," + data);

		if (data != null) {
			mWebView.loadUrl(data.toString());    // 주어진 Url 을 mWebView 에 load
		}
	}

	private void setValues(WebView view) {
		Log.d(TAG, "Set Values!");
		view.evaluateJavascript("(function() { document.querySelector(\"input[value=SEC]\").click()})()", null);

		String userName = PreferenceManager.getString(mContext, "USER_NAME");
		String empNo = PreferenceManager.getString(mContext, "EMPLOYEE_NUMBER");
		String telNo = PreferenceManager.getString(mContext, "PHONE_NUMBER");

		view.evaluateJavascript("(function() { document.getElementById(\"userName\").value=\"" + userName + "\"})()", null);
		view.evaluateJavascript("(function() { document.getElementById(\"empNo\").value=\"" + empNo + "\"})()", null);
		view.evaluateJavascript("(function() { document.getElementById(\"telNumber\").value=\"" + telNo + "\"})()", null);

		view.evaluateJavascript("(function() { document.querySelector(\"[name=answer1][value=N]\").click()})()", null);
		view.evaluateJavascript("(function() { document.querySelector(\"[name=answer3][value=N]\").click()})()", null);
		view.evaluateJavascript("(function() { document.querySelector(\"[name=answer4][value=N]\").click()})()", null);
		view.evaluateJavascript("(function() { document.querySelector(\"[name=answer5][value=N]\").click()})()", null);
		view.evaluateJavascript("(function() { document.querySelector(\"[name=answer7][value=N]\").click()})()", null);
		view.evaluateJavascript("(function() { document.querySelector(\"input[name=ivChk1]\").checked=true})()", null);

		view.evaluateJavascript("goSave()", null);

		Toast.makeText(mContext.getApplicationContext(), "기본값 설정완료!",Toast.LENGTH_SHORT).show();
	}
}