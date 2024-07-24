package edu.sjsu.brandonjohns.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class InputFragment extends MainActivity implements InputInfo.InputInfoListener
{
private boolean splitscreen;

@Override
public Fragment createFragment()
{
    return new InputInfo();
}

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        FrameLayout frameLayout = findViewById(R.id.fragmentLargerContainer);
        if (frameLayout != null) {
            splitscreen = true;
        }
    }
    @Override
    public void onCalculatedResult(double homeValue, double downPayment, double interestRate, int termsView, double propertyTaxRate)
    {
        // new data values bundle
        Bundle data = new Bundle();
        data.putDouble("homeValue", homeValue);
        data.putDouble("downPayment", downPayment);
        data.putDouble("interestRate", interestRate);
        data.putInt("termsView", termsView);
        data.putDouble("propertyTaxRate", propertyTaxRate);


        // split display screene
        if (splitscreen)
        {
            // show the output fragment in larger screen
            OutputFragment fragment = new OutputFragment();
            fragment.setArguments(data);

            FragmentManager fragmentManager = getSupportFragmentManager();

            // Begin the screen adjustment
            FragmentTransaction fragmentScreen = getSupportFragmentManager().beginTransaction();

            // Replace the screen view
            fragmentScreen.replace(R.id.fragmentLargerContainer, fragment);

            // push result
            fragmentScreen.commit();
        }
        // else second screen
        else
            {

            Intent intent = new Intent(this, OutputFragment.class);
            intent.putExtras(data);
            startActivity(intent);
        }

    }


}
