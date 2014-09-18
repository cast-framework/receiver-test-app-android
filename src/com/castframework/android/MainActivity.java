/*
 * Copyright (C) 2014 Google Inc. All Rights Reserved. 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at 
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 */

package com.castframework.android;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.widget.Toast;

import com.castframework.android.framework.CastManager;
import com.davidtschida.android.cards.R;

/**
 * Main activity to send messages to the receiver.
 */
public class MainActivity extends ActionBarActivity implements CastmanagerHost, DialogInterface.OnClickListener {

	private static final String TAG = MainActivity.class.getSimpleName();

    CastManager mCastManager;
    private Menu optionsMenu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        // 1. Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setTitle("Pick Environment")
                .setItems(R.array.environment_names, this);

        // 3. Get the AlertDialog from create()
        AlertDialog dialog = builder.create();
        dialog.show();

        initActionBar();
    }

    private void initActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(
                R.color.blue));
    }

    private void startCastManager(String app_id) {
        mCastManager = new CastManager(this, app_id);
        invalidateOptionsMenu();

        WelcomeFragment w = new WelcomeFragment();
        getCastmanager().setConnectedListener(w);
        getCastmanager().setOnMessageRecievedListener(w);

        getSupportFragmentManager().beginTransaction().add(R.id.content, w).commit();
    }

	@Override
	protected void onResume() {
		super.onResume();
		if(mCastManager != null) {
            mCastManager.onResume(this);
        }
	}

	@Override
	protected void onPause() {
		super.onPause();
        if(mCastManager != null) {
            mCastManager.onPause(this);
        }
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
        if(mCastManager != null) {
            mCastManager.onDestroy(this);
        }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        Toast.makeText(getApplicationContext(), "menu", Toast.LENGTH_SHORT).show();
        if(mCastManager != null) {
            Toast.makeText(getApplicationContext(), "sdfsff", Toast.LENGTH_SHORT).show();
            mCastManager.setMenu(menu);
            mCastManager.onResume(this);
        }

		return true;
	}

    @Override
    public CastManager getCastmanager() {
        return mCastManager;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        String[] environment_ids = getResources().getStringArray(R.array.environment_ids);
        startCastManager(environment_ids[which]);
    }
}
