package com.coffeetracker.addcaffeine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.coffeetracker.R;
import com.coffeetracker.adapter.SelectionAdapter;
import com.coffeetracker.util.CaffeineConstants;
import com.coffeetracker.util.DataManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

public class AddNewCaffeineActivity extends AppCompatActivity implements AddCaffeineContract.View {
    public static final String CUSTOM = "Custom";
    private static final String MEAL_XID = "MEAL_XID";

    //Views
    private View amountView;
    private Button submitButton;
    private EditText otherAmountEditText;
    private Spinner typeSelectionSpinner;
    private Spinner amountSelectionSpinner;
    private Button dateTimeButton;

    //Data
    private ArrayAdapter namesAdapter;
    private String[] caffeineNamesArray;
    private String[] amountsArray;
    private String[] caffeineNameCodes;
    private String[] amountCodes;
    private String[] canSizeCodes;
    private int currentDateTime;
    private long selectedtime;

    private AddCaffeinePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_caffeine);

        presenter = new AddCaffeinePresenter(this, new DataManager(getResources()));

        typeSelectionSpinner = (Spinner)findViewById(R.id.type_selection);
        amountView = findViewById(R.id.amount_view);
        amountSelectionSpinner = (Spinner)findViewById(R.id.amount_selection);
        otherAmountEditText = (EditText)findViewById(R.id.other_amount_edit);

        populateSelectionSpinner();

        dateTimeButton = (Button)findViewById(R.id.date_time_button);
        dateTimeButton.setText(getDateTimeString(Calendar.getInstance().getTimeInMillis()));
        //selectedtime = Calendar.getInstance().getTimeInMillis();
        dateTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimePicker();
            }
        });

        submitButton = (Button)findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitCaffeine();
            }
        });

        if (getIntent() != null && getIntent().hasExtra(MEAL_XID)) {
            //TODO: Edit meal
        }
    }

    public String getDateTimeString(long time) {
        DateFormat targetFormat = new SimpleDateFormat("EEEE, MMM dd HH:mm");
        String dateString = targetFormat.format(time);

        return dateString;
    }

    private void showDateTimePicker() {
        final View dialogView = View.inflate(this, R.layout.date_time_picker, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        dialogView.findViewById(R.id.date_time_set).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.date_picker);
                TimePicker timePicker = (TimePicker) dialogView.findViewById(R.id.time_picker);

                Calendar calendar = new GregorianCalendar(datePicker.getYear(),
                        datePicker.getMonth(),
                        datePicker.getDayOfMonth(),
                        timePicker.getCurrentHour(),
                        timePicker.getCurrentMinute());

                selectedtime = calendar.getTimeInMillis();
                dateTimeButton.setText(getDateTimeString(selectedtime));
                alertDialog.dismiss();
            }
        });

        alertDialog.setView(dialogView);
        alertDialog.show();
    }

    private void submitCaffeine() {
        if (validate()) {
            Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_LONG).show();
            closeActivity();
//            ApiManager.getRestApiInterface().createMealEvent(
//                    UpPlatformSdkConstants.API_VERSION_STRING,
//                    getNewMealParameters(),
//                    genericCallbackListener
//            );
        }
    }

    private HashMap<String, Object> getNewMealParameters() {
        HashMap<String, Object> queryHashMap = new HashMap<String, Object>();

        String drinkName = (String)namesAdapter.getItem(typeSelectionSpinner.getSelectedItemPosition());
        String drinkSize = "";
        if (CUSTOM.equalsIgnoreCase(drinkName)) {
            drinkSize = otherAmountEditText.getText().toString() + " mg";
        } else {
            drinkSize = amountsArray[amountSelectionSpinner.getSelectedItemPosition()];
        }


        String drinkNameString = String.format("%s %s", drinkName, drinkSize);

        int caffeine = calculateCaffeine();

        JSONObject obj = new JSONObject();
        try {
            obj.put("name", drinkNameString);
            obj.put("description", drinkNameString);
            obj.put("caffeine", caffeine);
            //obj.put("num_drinks", 1);
            obj.put("sub_type", 1);
            //obj.put("amount", 1);
            //obj.put("only_waters", true);
            //obj.put("accuracy", 100.00);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        queryHashMap.put("note", drinkNameString);
        queryHashMap.put("sub_type", 1);
        if (selectedtime > 0) {
            int selectedTimeToInteger = (int)(selectedtime / 1000L);
            queryHashMap.put("time_created", selectedTimeToInteger);
        }

        queryHashMap.put("items", String.format("[%s]", obj.toString()));

        return queryHashMap;
    }

    private int calculateCaffeine() {
        String selectedCaffeineCode = caffeineNameCodes[typeSelectionSpinner.getSelectedItemPosition()];
        String selectedAmountCode;
        if (CaffeineConstants.isSoda(selectedCaffeineCode)) {
            selectedAmountCode = canSizeCodes[amountSelectionSpinner.getSelectedItemPosition()];
        } else {
            selectedAmountCode = amountCodes[amountSelectionSpinner.getSelectedItemPosition()];
        }

        int totalCaffeine = 0;
        if (CUSTOM.equalsIgnoreCase(selectedCaffeineCode)) {
            totalCaffeine = Integer.parseInt(otherAmountEditText.getText().toString());
        } else {
            totalCaffeine = CaffeineConstants.getCaffeineAmount(selectedCaffeineCode, selectedAmountCode);
        }

        return totalCaffeine;
    }

    /*
    private Callback genericCallbackListener = new Callback<Object>() {
        @Override
        public void success(Object mealsResponse, Response response) {
            Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_LONG).show();
            closeActivity();

        }

        @Override
        public void failure(RetrofitError retrofitError) {
            Log.e("TAG",  "api call failed, error message: " + retrofitError.getMessage());
            Toast.makeText(getApplicationContext(), "There was an error", Toast.LENGTH_LONG).show();
        }
    };
    */

    private boolean validate() {
        String selectedTypeName = (String)namesAdapter.getItem(typeSelectionSpinner.getSelectedItemPosition());
        if (CUSTOM.equalsIgnoreCase(selectedTypeName)) {
            String otherAmount = otherAmountEditText.getText().toString();
            int amount = Integer.parseInt(otherAmount);
            if (otherAmount == null || otherAmount.length() < 1 || amount == 0) {
                Toast.makeText(getApplicationContext(), "You must enter an amount!", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        return true;
    }

    private void populateSelectionSpinner() {
        //buildDataArrays();

        //List<String> caffeineNames = new ArrayList<>(Arrays.asList(caffeineNamesArray));
        List<String> caffeineNames = presenter.getCaffeineNames();
        SelectionAdapter namesAdapter = new SelectionAdapter(this, R.layout.selection_item, caffeineNames);

        typeSelectionSpinner.setAdapter(namesAdapter);

        typeSelectionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                displayAmountEntry(position);
                populateAmountsSpinner();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void buildDataArrays() {
        caffeineNamesArray = getResources().getStringArray(R.array.caffeine_names);
        namesAdapter = ArrayAdapter.createFromResource(this, R.array.caffeine_names, android.R.layout.simple_spinner_item);
        caffeineNameCodes = getResources().getStringArray(R.array.caffeine_name_codes);
        amountsArray = getResources().getStringArray(R.array.amount_names);
        canSizeCodes = getResources().getStringArray(R.array.can_size_codes);
        //espressoSizeCodes = getResources().getStringArray(R.array.esspresso_size_codes);
    }

    private void displayAmountEntry(int position) {

        String caffeineNameCode = caffeineNameCodes[position];
        if (CUSTOM.equalsIgnoreCase(caffeineNameCode)) {
            otherAmountEditText.setVisibility(View.VISIBLE);
            amountView.setVisibility(View.GONE);
        } else {
            otherAmountEditText.setVisibility(View.GONE);
            amountView.setVisibility(View.VISIBLE);
        }
    }

    private void populateAmountsSpinner() {

        String selectedCaffeineCode = caffeineNameCodes[typeSelectionSpinner.getSelectedItemPosition()];

        String[] amountNamesArray = null;
        List<String> amountNames = new ArrayList<>();
        if (CaffeineConstants.isSoda(selectedCaffeineCode)) {
            amountNamesArray = getResources().getStringArray(R.array.can_size_names);
            amountCodes = getResources().getStringArray(R.array.can_size_codes);
        } else if (CaffeineConstants.isEspresso(selectedCaffeineCode)){
            amountNamesArray = getResources().getStringArray(R.array.esspresso_size_names);
            amountCodes = getResources().getStringArray(R.array.esspresso_size_codes);
        } else {
            amountNamesArray = getResources().getStringArray(R.array.amount_names);
            amountCodes = getResources().getStringArray(R.array.amount_codes);
        }

        amountNames = new ArrayList<>(Arrays.asList(amountNamesArray));

        SelectionAdapter amountNamesAdapter = new SelectionAdapter(this, R.layout.selection_item, amountNames);

        amountSelectionSpinner.setAdapter(amountNamesAdapter);

        amountSelectionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void closeActivity() {
        Intent returnIntent = new Intent();
        setResult(RESULT_OK, returnIntent);
        finish();
    }


}
