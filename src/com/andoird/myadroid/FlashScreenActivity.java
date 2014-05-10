package com.andoird.myadroid;

import com.example.myandoird.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import android.widget.FrameLayout.LayoutParams;

public class FlashScreenActivity extends Activity {
	protected boolean _active = true;
	protected int _splashTime = 3000;

	TextView text_touch;
	boolean blinkOn = false;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.flashscreen);

		text_touch = (TextView) findViewById(R.id.text_touch);
		android.view.Display display = ((android.view.WindowManager) getSystemService(Context.WINDOW_SERVICE))
				.getDefaultDisplay();
		int pos = (int) (display.getHeight() * 0.7);
		text_touch.setTop(pos);

		FrameLayout.LayoutParams para = new FrameLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		para.setMargins(0, pos, 0, 0);
		text_touch.setLayoutParams(para);

		Blink tmrBlink = new Blink(500, new Runnable() {

			public void run() {
				if (blinkOn) {
					// text_touch.setVisibility(View.VISIBLE);
					text_touch.setTextColor(getResources().getColor(
							R.color.touch_color1));
				} else {
					// text_touch.setVisibility(View.INVISIBLE);
					text_touch.setTextColor(getResources().getColor(
							R.color.touch_color2));
				}
				blinkOn = !blinkOn;
			}
		});
		tmrBlink.start();

		/* End Blink */

		text_touch.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				_active = false;

			}
		});

		// thread for displaying the SplashScreen
		Thread splashTread = new Thread() {
			@Override
			public void run() {
				try {
					int waited = 0;
					while (_active && (waited < _splashTime)) {
						sleep(100);
						if (_active) {
							waited += 100;
						}
					}
				} catch (InterruptedException e) {
					// do nothing
				} finally {
					finish();
					startActivity(new Intent(getApplicationContext(),
							MainActivity.class));
					// stop();
				}
			}
		};

		splashTread.start();

	}

}
