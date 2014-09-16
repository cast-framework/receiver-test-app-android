package com.castframework.android;

import android.app.Activity;
import android.support.v4.app.Fragment;

public class CastFragment extends Fragment {

    CastmanagerHost host;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof CastmanagerHost) {
            host = (CastmanagerHost) activity;
        } else throw new ClassCastException("Activity must implement CastmanagerHost!");
    }

    protected CastmanagerHost getCastManagerHost() {
        return host;
    }
}