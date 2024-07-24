package edu.sjsu.brandonjohns.myapplication;

import android.widget.Button;
import android.widget.TextView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import androidx.fragment.app.Fragment;



public  class OutputFragment  extends Fragment
{
    private TextView monthlyView;
    private TextView totalIntView;
    private TextView totalTaxView;
    private TextView payoffDateView;
    private Button butRest;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.output, parent, false);
        Bundle data = getArguments();

        // combine text to phone
        monthlyView = view.findViewById(R.id.monthlypaymentview);
        totalIntView = view.findViewById(R.id.totalinterestpaidview);
        totalTaxView = view.findViewById(R.id.totaltaxpaidview);
        payoffDateView = view.findViewById(R.id.payoffdateview);
        butRest = view.findViewById(R.id.butReset2);
        butRest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearInput();
                getActivity().onBackPressed();
                System.out.println("Reset Everything");
            }
        });

        if (data != null) {
            double homeValue = data.getDouble("homeValue");
            double downPayment = data.getDouble("downPayment");
            double interestRate = data.getDouble("interestRate");
            int terms = data.getInt("termsView");
            double propertyTaxRate = data.getDouble("propertyTaxRate");

            calcResults(homeValue, downPayment, interestRate, terms, propertyTaxRate);


        }
        return view;
    }

    public void clearInput()
    {
        monthlyView.setText("");
        totalIntView.setText("");
       totalTaxView.setText("");
        payoffDateView.setText("");



    }
    public void calcResults(double homeValue, double downPayment, double interestRate, int terms, double propertyTaxRate)
    {
        /// home val - loan
        double homesubdown = homeValue - downPayment;

        // monthly interest rate
        double monthint = (interestRate)/ 1200;

        /// number of payments
        double numberOfPayments= terms * 12;

        //  monthly payment
        double monthpay = ((homesubdown*monthint * Math.pow(1 + monthint, numberOfPayments))) / (Math.pow(1 + monthint, numberOfPayments) - 1);
        // total interest
        double totalInt = monthpay * numberOfPayments - homesubdown;

            /// proptax value
        double propTax = propertyTaxRate/1200 * homeValue;

        // total property tax paid
        double totalTax = propTax * numberOfPayments;

        // Pay date
        Calendar cal = Calendar.getInstance();
        /// format date
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM");

        /// format month get month
        String month = dateFormat.format(cal.getTime());

        /// format year
        SimpleDateFormat anotherDateFormat = new SimpleDateFormat("yyy");
        cal.add(Calendar.YEAR, terms);


        String year = anotherDateFormat.format(cal.getTime());
        String date = month + " " + year;


        /// returning results
        monthlyView.setText(String.format("%.2f", monthpay));
        totalIntView.setText(String.format("%.2f", totalInt));
        totalTaxView.setText(String.format("%.2f", totalTax));
        payoffDateView.setText(date);
    }


}


