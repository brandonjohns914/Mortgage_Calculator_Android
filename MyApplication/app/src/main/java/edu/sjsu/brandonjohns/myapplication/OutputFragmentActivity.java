package edu.sjsu.brandonjohns.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

public  class OutputFragmentActivity extends MainActivity
{
    @Override
    protected Fragment createFragment() {
        Intent intent = getIntent();
        Bundle data = intent.getExtras();

        Fragment fragment = new OutputFragment();
        fragment.setArguments(data);
        return fragment;
    }
}
