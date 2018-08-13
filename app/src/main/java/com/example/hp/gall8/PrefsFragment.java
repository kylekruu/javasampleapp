package com.example.hp.gall8;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by HP on 2/3/2018.
 */

public class PrefsFragment extends PreferenceFragment
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.prefs1);
    }
}
