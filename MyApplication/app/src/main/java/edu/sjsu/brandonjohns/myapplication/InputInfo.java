package edu.sjsu.brandonjohns.myapplication;


import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


public class InputInfo  extends Fragment
{
    /// view screen results
    private EditText HomeValueView;
    private EditText DownPaymentView;
    private EditText APRView;
    private EditText PropertyTaxView;
    private RadioGroup TermsView;
    private Button butCalculate;
    private Button butReset;
    private InputInfoListener inputListner;

    /// buttons for terms
    private RadioButton year15;
    private RadioButton year20;
    private RadioButton year25;
    private RadioButton year30;
    private RadioButton year40;

    private int year = 0;

    /// user interface for fragment
    public interface InputInfoListener
    {

        void onCalculatedResult(double homeValue, double downPayment, double interestRate, int termsView, double propertyTaxRate);
    }

    /// gets the activity and sets it to the phone
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            inputListner = (InputInfoListener) getActivity();
        } catch (Exception e) {
            Log.e("InputInfo", "implement InputInfo Activit");
        }
    }

    /// creates a new view
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        View view = inflater.inflate(R.layout.input, parent, false);
            /// connects the user values to the code
        HomeValueView = view.findViewById(R.id.homevalue);
        DownPaymentView = view.findViewById(R.id.downpayment);
        APRView = view.findViewById(R.id.apr);
        TermsView = view.findViewById(R.id.terms);
        PropertyTaxView = view.findViewById(R.id.proptaxrate);
        butCalculate = view.findViewById(R.id.butCalc);
        butReset = view.findViewById(R.id.butReset);

            /// sets the terms
        year15 = view.findViewById(R.id.year15);
        year20 = view.findViewById(R.id.year20);
        year25 = view.findViewById(R.id.year25);
        year30 = view.findViewById(R.id.year30);
        year40 = view.findViewById(R.id.year40);

        /// starts the calculate button
        butCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                boolean valuesFilled = inputUserValsEntered();
                boolean isValidInput = false;
                if (valuesFilled) {
                    isValidInput = UserInfoCorrect();
                }
                if (valuesFilled && isValidInput) {
                    calculateResults();


                } else
                {
                    Toast invalidentry = Toast.makeText(getContext(), "Invalid Entry", Toast.LENGTH_LONG);
                    invalidentry.show();
                }

            }
        });

            /// creates the reset button
        butReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearInput();
                System.out.println("Reset Everything");
            }
        });
        return view;
    }

    //// resets input
    public void clearInput()
    {
        HomeValueView.setText("");
        DownPaymentView.setText("");
        APRView.setText("");
        PropertyTaxView.setText("");

    }



    private boolean inputUserValsEntered() {
        String homeVal = HomeValueView.getText().toString();
        String downPayment = DownPaymentView.getText().toString();
        String interestRate = APRView.getText().toString();
        String propertyTaxRate = PropertyTaxView.getText().toString();

        if (homeVal.length() == 0 || downPayment.length() == 0 || interestRate.length() == 0 || propertyTaxRate.length() == 0) {
            return false;
        }
        return true;
    }


    /// checks the user info
    private boolean UserInfoCorrect()
    {
        double home = Double.valueOf(HomeValueView.getText().toString());
        double downpayment = Double.valueOf(DownPaymentView.getText().toString());
        double interestrate = Double.valueOf(APRView.getText().toString());
        double tax = Double.valueOf(PropertyTaxView.getText().toString());

        boolean term = false;

        if (downpayment > home)
        {
            Toast downVal = Toast.makeText(getContext(), "Down Payment cannot be larger than Home Value ", Toast.LENGTH_LONG);
            downVal.show();
            return false;
        }
        if (interestrate > 100)
        {
            Toast intval = Toast.makeText(getContext(), "Interest Rate must be less than 100", Toast.LENGTH_LONG);
            intval.show();
            return  false;
        }
        if (tax > 3)
        {
            Toast proptaxval = Toast.makeText(getContext(), "Property Tax must be less than 4%", Toast.LENGTH_LONG);
            proptaxval.show();
            return false;
        }
        else
        {
            return true;
        }
    }

    /// CALCULATES RESULTS
        public void calculateResults()
    {
            double homeVal = Double.valueOf(HomeValueView.getText().toString());
            double down = Double.valueOf(DownPaymentView.getText().toString());
            double interestRate = Double.valueOf(APRView.getText().toString());
            int years = getterms();
            double taxrate = Double.valueOf(PropertyTaxView.getText().toString());
            inputListner.onCalculatedResult(homeVal, down, interestRate,years, taxrate);
        }


    /// GETS RADIO BUTTON
    public int getterms()
    {
        if (TermsView.getCheckedRadioButtonId() != -1)
        {
            int id = TermsView.getCheckedRadioButtonId();
            View radioButton = TermsView.findViewById(id);
            int radioId = TermsView.indexOfChild(radioButton);
            RadioButton btn = (RadioButton) TermsView.getChildAt(radioId);
            String selection = (String) btn.getText();
            year = Integer.valueOf(selection);
        }

        return year;
    }
}



