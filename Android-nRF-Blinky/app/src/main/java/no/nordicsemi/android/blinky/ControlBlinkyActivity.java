/*
 * Copyright (c) 2015, Nordic Semiconductor
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *
 *  Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 *  Neither the name of copyright holder nor the names of its
 *   contributors may be used to endorse or promote products derived from
 *   this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package no.nordicsemi.android.blinky;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import no.nordicsemi.android.blinky.profile.BleProfileService;
import no.nordicsemi.android.blinky.service.BlinkyService;

public class ControlBlinkyActivity extends AppCompatActivity {
	private BlinkyService.BlinkyBinder mBlinkyDevice;
	// private Button mActionOnOff, mActionConnect, mActionFoo;
	private Button mActionConnect;
	private Button mActionLedA, mActionLedB, mActionLedC, mActionLedD;
	private Button mActionPwm1;
	private Button mActionPwm2;
	private Button mActionPwm3;
	private Button mPlayA1, mPlayB1, mPlayC2, mPlayD2, mPlayE2, mPlayF2, mPlayG2;
	// private ImageView mImageBulb;
	private View mParentView;
	private View mBackgroundView;
	private boolean LEDsOn = false;

	private ServiceConnection mServiceConnection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			mBlinkyDevice = (BlinkyService.BlinkyBinder) service;

			if (mBlinkyDevice.isConnected()) {
				mActionConnect.setText(getString(R.string.action_disconnect));

				if (mBlinkyDevice.isOn()) {
					// mImageBulb.setImageDrawable(ContextCompat.getDrawable(ControlBlinkyActivity.this, R.drawable.bulb_on));
					// mActionOnOff.setText(getString(R.string.turn_off));
				} else {
					// mImageBulb.setImageDrawable(ContextCompat.getDrawable(ControlBlinkyActivity.this, R.drawable.bulb_off));
					// mActionOnOff.setText(getString(R.string.turn_on));
				}

				if (mBlinkyDevice.isButtonPressed()) {
					mBackgroundView.setVisibility(View.VISIBLE);
				} else {
					mBackgroundView.setVisibility(View.INVISIBLE);
				}
			} else {
				mActionConnect.setText(getString(R.string.action_connect));
			}
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			mBlinkyDevice = null;
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_control_device);

		Intent i = getIntent();
		final String deviceName = i.getStringExtra(BlinkyService.EXTRA_DEVICE_NAME);
		final String deviceAddress = i.getStringExtra(BlinkyService.EXTRA_DEVICE_ADDRESS);

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setTitle(deviceName);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		//mActionOnOff = (Button) findViewById(R.id.button_blinky);
		//mActionFoo = (Button) findViewById(R.id.fooButton);
		mActionLedA = (Button) findViewById(R.id.ledAButton);
		mActionLedB = (Button) findViewById(R.id.ledBButton);
		mActionLedC = (Button) findViewById(R.id.ledCButton);
		mActionLedD = (Button) findViewById(R.id.ledDButton);
		mActionPwm1 = (Button) findViewById(R.id.pwm1Button);
		mActionPwm2 = (Button) findViewById(R.id.pwm2Button);
		mActionPwm3 = (Button) findViewById(R.id.pwm3Button);
		mPlayA1 = (Button) findViewById(R.id.playA1);
		mPlayB1 = (Button) findViewById(R.id.playB1);
		mPlayC2 = (Button) findViewById(R.id.playC2);
		mPlayD2 = (Button) findViewById(R.id.playD2);
		mPlayE2 = (Button) findViewById(R.id.playE2);
		mPlayF2 = (Button) findViewById(R.id.playF2);
		mPlayG2 = (Button) findViewById(R.id.playG2);
		// mActionFoo = (Button) findViewById(R.id.fooButton);
		mActionConnect = (Button) findViewById(R.id.action_connect);
		//mImageBulb = (ImageView) findViewById(R.id.img_bulb);
		mBackgroundView = findViewById(R.id.background_view);
		mParentView = findViewById(R.id.relative_layout_control);

		mActionLedA.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mBlinkyDevice != null && mBlinkyDevice.isConnected()) {
					mBlinkyDevice.sendByte((byte)1);
				} else {
					showError(getString(R.string.please_connect));
				}
			}
		});

		mActionLedB.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mBlinkyDevice != null && mBlinkyDevice.isConnected()) {
					mBlinkyDevice.sendByte((byte)2);
				} else {
					showError(getString(R.string.please_connect));
				}
			}
		});

		mActionLedC.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mBlinkyDevice != null && mBlinkyDevice.isConnected()) {
					mBlinkyDevice.sendByte((byte)3);
				} else {
					showError(getString(R.string.please_connect));
				}
			}
		});

		mActionLedD.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mBlinkyDevice != null && mBlinkyDevice.isConnected()) {
					mBlinkyDevice.sendByte((byte)4);
				} else {
					showError(getString(R.string.please_connect));
				}
			}
		});

		mActionPwm1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mBlinkyDevice != null && mBlinkyDevice.isConnected()) {
					if (mActionPwm1.getText().equals(getString(R.string.pwm1_off))) {
						mBlinkyDevice.sendByte((byte)5);
						mActionPwm1.setText(getString(R.string.pwm1_on));
					} else {
						mBlinkyDevice.sendByte((byte)6);
						mActionPwm1.setText(getString(R.string.pwm1_off));
					}
				} else {
					showError(getString(R.string.please_connect));
				}
			}
		});

		mActionPwm2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mBlinkyDevice != null && mBlinkyDevice.isConnected()) {
					if (mActionPwm2.getText().equals(getString(R.string.pwm2_off))) {
						mBlinkyDevice.sendByte((byte)7);
						mActionPwm2.setText(getString(R.string.pwm2_on));
					} else {
						mBlinkyDevice.sendByte((byte)8);
						mActionPwm2.setText(getString(R.string.pwm2_off));
					}
				} else {
					showError(getString(R.string.please_connect));
				}
			}
		});

		mActionPwm3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mBlinkyDevice != null && mBlinkyDevice.isConnected()) {
					mBlinkyDevice.sendByte((byte) 9);
				} else {
					showError(getString(R.string.please_connect));
				}
			}
		});

		mPlayA1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mBlinkyDevice != null && mBlinkyDevice.isConnected()) {
					mBlinkyDevice.sendByte((byte) 10);
				} else {
					showError(getString(R.string.please_connect));
				}
			}
		});

		mPlayB1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mBlinkyDevice != null && mBlinkyDevice.isConnected()) {
					mBlinkyDevice.sendByte((byte) 11);
				} else {
					showError(getString(R.string.please_connect));
				}
			}
		});

		mPlayC2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mBlinkyDevice != null && mBlinkyDevice.isConnected()) {
					mBlinkyDevice.sendByte((byte) 12);
				} else {
					showError(getString(R.string.please_connect));
				}
			}
		});

		mPlayD2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mBlinkyDevice != null && mBlinkyDevice.isConnected()) {
					mBlinkyDevice.sendByte((byte) 13);
				} else {
					showError(getString(R.string.please_connect));
				}
			}
		});

		mPlayE2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mBlinkyDevice != null && mBlinkyDevice.isConnected()) {
					mBlinkyDevice.sendByte((byte) 14);
				} else {
					showError(getString(R.string.please_connect));
				}
			}
		});

		mPlayF2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mBlinkyDevice != null && mBlinkyDevice.isConnected()) {
					mBlinkyDevice.sendByte((byte) 15);
				} else {
					showError(getString(R.string.please_connect));
				}
			}
		});

		mPlayG2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mBlinkyDevice != null && mBlinkyDevice.isConnected()) {
					mBlinkyDevice.sendByte((byte) 16);
				} else {
					showError(getString(R.string.please_connect));
				}
			}
		});

/*		mActionFoo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mBlinkyDevice != null && mBlinkyDevice.isConnected()) {
					if (LEDsOn) {
						mBlinkyDevice.sendByte((byte)2);
						LEDsOn = false;
					} else {
						mBlinkyDevice.sendByte((byte)0);
						LEDsOn = true;
					}
				} else {
					showError(getString(R.string.please_connect));
				}
			}
		});

		mActionOnOff.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mBlinkyDevice != null && mBlinkyDevice.isConnected()) {
					if (mActionOnOff.getText().equals(getString(R.string.turn_on))) {
						mBlinkyDevice.send(true);
					} else {
						mBlinkyDevice.send(false);
					}
				} else {
					showError(getString(R.string.please_connect));
				}
			}
		});*/

		LocalBroadcastManager.getInstance(this).registerReceiver(mBlinkyUpdateReceiver, makeGattUpdateIntentFilter());

		final Intent intent = new Intent(this, BlinkyService.class);
		intent.putExtra(BlinkyService.EXTRA_DEVICE_ADDRESS, deviceAddress);
		startService(intent);
		bindService(intent, mServiceConnection, 0);

		mActionConnect.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mBlinkyDevice != null && mBlinkyDevice.isConnected()) {
					mBlinkyDevice.disconnect();
				} else {
					startService(intent);
					bindService(intent, mServiceConnection, 0);
				}
			}
		});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				onBackPressed();
		}
		return true;
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		if (mBlinkyDevice != null && mBlinkyDevice.isConnected())
			mBlinkyDevice.disconnect();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unbindService(mServiceConnection);
		LocalBroadcastManager.getInstance(this).unregisterReceiver(mBlinkyUpdateReceiver);

		mServiceConnection = null;
		mBlinkyDevice = null;
	}

	private BroadcastReceiver mBlinkyUpdateReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			final String action = intent.getAction();

			switch (action) {
				case BlinkyService.BROADCAST_LED_STATE_CHANGED: {
					final boolean flag = intent.getBooleanExtra(BlinkyService.EXTRA_DATA, false);
					if (flag) {
		//				mImageBulb.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.bulb_on));
		//				mActionOnOff.setText(getString(R.string.turn_off));
					} else {
		//				mImageBulb.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.bulb_off));
		//				mActionOnOff.setText(getString(R.string.turn_on));
					}
					break;
				}
				case BlinkyService.BROADCAST_BUTTON_STATE_CHANGED: {
					final boolean flag = intent.getBooleanExtra(BlinkyService.EXTRA_DATA, false);
					if (flag) {
						mBackgroundView.setVisibility(View.VISIBLE);
					} else {
						mBackgroundView.setVisibility(View.INVISIBLE);
					}
					break;
				}
				case BlinkyService.BROADCAST_CONNECTION_STATE: {
					final int value = intent.getIntExtra(BlinkyService.EXTRA_CONNECTION_STATE, BlinkyService.STATE_DISCONNECTED);
					switch (value) {
						case BleProfileService.STATE_CONNECTED:
							mActionConnect.setText(getString(R.string.action_disconnect));
							break;
						case BleProfileService.STATE_DISCONNECTED:
							mActionConnect.setText(getString(R.string.action_connect));
		//					mActionOnOff.setText(getString(R.string.turn_on));
		//					mImageBulb.setImageDrawable(ContextCompat.getDrawable(ControlBlinkyActivity.this, R.drawable.bulb_off));
							break;
					}
					break;
				}
				case BlinkyService.BROADCAST_ERROR: {
					final String message = intent.getStringExtra(BlinkyService.EXTRA_ERROR_MESSAGE);
					final int code = intent.getIntExtra(BlinkyService.EXTRA_ERROR_CODE, 0);
					showError(getString(R.string.error_msg, message, code));
					break;
				}
			}
		}
	};

	private static IntentFilter makeGattUpdateIntentFilter() {
		final IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(BlinkyService.BROADCAST_LED_STATE_CHANGED);
		intentFilter.addAction(BlinkyService.BROADCAST_BUTTON_STATE_CHANGED);
		intentFilter.addAction(BlinkyService.BROADCAST_CONNECTION_STATE);
		intentFilter.addAction(BlinkyService.BROADCAST_ERROR);
		return intentFilter;
	}

	private void showError(final String error) {
		Snackbar.make(mParentView, error, Snackbar.LENGTH_LONG).show();
	}
}

