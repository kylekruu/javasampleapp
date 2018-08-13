package com.example.hp.gall8;

import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import java.util.List;

/**
 * Created by HP on 2/4/2018.
 */

public class MyPreferenceActivity extends PreferenceActivity
{
    @Override
    public void onBuildHeaders(List<Header> target)
    {
        loadHeadersFromResource(R.xml.header, target);
    }

    @Override
    protected boolean isValidFragment(String fragmentName)
    {
        return PreferenceManager.class.getName().equals(fragmentName);
    }
}