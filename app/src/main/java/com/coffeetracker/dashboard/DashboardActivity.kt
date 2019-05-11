package com.coffeetracker.dashboard

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.TextView
import com.coffeetracker.CoffeeApplication
import com.coffeetracker.R
import com.coffeetracker.addcaffeine.AddNewCaffeineActivity
import com.coffeetracker.adapter.ItemAdapter
import com.coffeetracker.models.MealItem
import com.coffeetracker.util.DeleteMealListener
import java.util.*


class DashboardActivity : AppCompatActivity(), DashboardContract.View, DeleteMealListener {

    private val mAccessToken: String? = null
    private val mRefreshToken: String? = null
    private val totalTextView: TextView? = null
    private val progressBar: ProgressBar? = null
    private var app: CoffeeApplication? = null
    //private List<Long> timestamps;
    private val itemsResponseList: List<MealItem>? = null
    //private var itemDisplayList: MutableList<MealItem>? = null
    private val caffeineListview: ListView? = null
    private var totalCaffeine: Double = 0.toDouble()

    private lateinit var presenter: DashboardPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_dashboard)

        app = application as CoffeeApplication

        presenter = DashboardPresenter(this)

        presenter.getMeals()

        //getUserData();
        //getMeals();
        //Intent intent = new Intent(DashboardActivity.this, JawboneLoginActivity.class);
        //startActivity(intent);
        //finish();

        /*
        AppRate.with(this)
                .setInstallDays(0) // default 10, 0 means install day.
                .setLaunchTimes(3) // default 10
                .setRemindInterval(2) // default 1
                .setShowLaterButton(true) // default true
                .setDebug(false) // default false
                .setOnClickButtonListener(new OnClickButtonListener() { // callback listener.
                    @Override
                    public void onClickButton(int which) {
                        //Log.d(DashboardActivity.class.getName(), Integer.toString(which));

                    }
                })
                .setEventsTimes(2)
                .monitor();

        // Show a dialog if meets conditions
        AppRate.showRateDialogIfMeetsConditions(this);
        */


    }

    /*
    private void buildHistoryView(MealsResponse mealsResponse) {
        itemsResponseList = mealsResponse.getData().getItems();

        CalculateTimingsTask task = new CalculateTimingsTask();
        task.execute();


    }
    */

    override fun buildCaffeineList(itemDisplayList: ArrayList<MealItem>) {
        val mealItemArrayAdapter = ItemAdapter(this, R.layout.meal_item, itemDisplayList, this)
        caffeineListview?.adapter = mealItemArrayAdapter

        caffeineListview?.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val builder = AlertDialog.Builder(this@DashboardActivity)
            builder.setMessage(itemDisplayList!![position].formattedOutputString)
            builder.setPositiveButton("Delete") { dialog, which ->
                //Toast.makeText(getApplicationContext(), "Delete", Toast.LENGTH_LONG).show();
                deleteMealCallback(itemDisplayList!![position].xid)
            }
            //TODO: Allow editing
            /*
                builder.setNegativeButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(getApplicationContext(), "Edit", Toast.LENGTH_LONG).show();

                    }
                });
                */
            builder.setNeutralButton("Cancel", null)
            val dialog = builder.create()
            dialog.show()
        }
    }

    override fun deleteMealCallback(id: String) {
        deleteMealAction(id)
    }

    /*
    inner class CalculateTimingsTask : AsyncTask<Void, Void, String>() {

        override fun doInBackground(vararg params: Void): String? {


            itemDisplayList = ArrayList()
            totalCaffeine = 0.0

            for (item in itemsResponseList!!) {

                var timeStamp = item.created
                timeStamp = timeStamp * 1000
                val cal1 = Calendar.getInstance()
                cal1.timeInMillis = timeStamp
                val created = cal1.timeInMillis

                val cal2 = Calendar.getInstance()
                val now = cal2.timeInMillis

                val difference = now - created
                var amountLeft = 0.0
                if (difference < HOURS_IN_MILLIS) {
                    if (item.details.containsKey("caffeine")) {
                        val caffeine = item.details["caffeine"] as Double
                        val seconds = difference / 1000
                        val minutes = seconds / 60
                        val hours = minutes / 60

                        var metabolized = true
                        if (hours <= 0 && minutes <= 15) {
                            metabolized = false
                        }

                        amountLeft = caffeine * Math.pow(0.5, hours / 5.7)
                        if (metabolized) {
                            totalCaffeine += amountLeft
                        }


                        val outputString = StringBuilder()
                        val targetFormat = SimpleDateFormat("EEEE, MMM dd HH:mm")

                        val cal = Calendar.getInstance()
                        cal.timeZone = TimeZone.getTimeZone("UTC")
                        cal.timeInMillis = timeStamp


                        val dateString = targetFormat.format(cal.time)

                        outputString.append(String.format("%s - ", dateString))
                        outputString.append(String.format(" "))
                        outputString.append(String.format("%s", item.title))
                        //outputString.append(System.getProperty("line.separator"));
                        //outputString.append(System.getProperty("line.separator"));
                        outputString.append(System.getProperty("line.separator"))

                        val mealItem = MealItem()
                        mealItem.metabolized = metabolized
                        mealItem.caffeineLeft = amountLeft
                        mealItem.formattedOutputString = outputString.toString()
                        mealItem.xid = item.xid
                        itemDisplayList!!.add(mealItem)
                    }


                }


            }
            return null
        }

        override fun onPostExecute(outputString: String) {

            buildCaffeineList()
            totalTextView!!.text = totalCaffeine.toInt().toString()
        }
    }
    */

    private fun getUserData() {
        //        ApiManager.getRestApiInterface().getUser(
        //                UpPlatformSdkConstants.API_VERSION_STRING,
        //                userCallbackListener);

    }

    private fun getMeals() {
        progressBar!!.visibility = View.VISIBLE
        //        ApiManager.getRestApiInterface().getMealEventsList(
        //                UpPlatformSdkConstants.API_VERSION_STRING,
        //                getMealEventsListRequestParams(),
        //                mealsCallbackListener);
    }

    private fun deleteMealAction(xid: String) {
        progressBar!!.visibility = View.VISIBLE
        //        ApiManager.getRestApiInterface().deleteMealEvent(
        //                UpPlatformSdkConstants.API_VERSION_STRING,
        //                xid,
        //                mealDeletedCallbackListener
        //                );
    }

    /*
    private Callback userCallbackListener = new Callback<UserResponse>() {
        @Override
        public void success(UserResponse userResponse, Response response) {
            String username = userResponse.getData().getFirst() + " " + userResponse.getData().getLast();
            app.setUsername(username);

            progressBar.setVisibility(View.GONE);

            getMeals();

        }

        @Override
        public void failure(RetrofitError retrofitError) {
            Response r = retrofitError.getResponse();
            if (r != null && r.getStatus() == 401) {
                Toast.makeText(getApplicationContext(), "There was a problem getting your data. Trying logging in again.", Toast.LENGTH_LONG).show();
                Log.e(TAG, "api call failed, error message: " + retrofitError.getMessage());

                Intent intent = new Intent(DashboardActivity.this, JawboneLoginActivity.class);
                startActivity(intent);
                finish();
            }

        }
    };

    private Callback mealsCallbackListener = new Callback<MealsResponse>() {
        @Override
        public void success(MealsResponse mealsResponse, Response response) {
            progressBar.setVisibility(View.GONE);
            buildHistoryView(mealsResponse);

        }

        @Override
        public void failure(RetrofitError retrofitError) {
            Log.e(TAG, "api call failed, error message: " + retrofitError.getMessage());
            Toast.makeText(getApplicationContext(), "There was a problem finding your latest caffeine entries", Toast.LENGTH_LONG).show();
        }
    };

    private Callback mealDeletedCallbackListener = new Callback() {
        @Override
        public void success(Object o, Response response) {
            progressBar.setVisibility(View.GONE);
            getMeals();
        }

        @Override
        public void failure(RetrofitError retrofitError) {
            Log.e(TAG, "api call failed, error message: " + retrofitError.getMessage());
            Toast.makeText(getApplicationContext(), "There was a problem deleting that item", Toast.LENGTH_LONG).show();
        }
    };
    */

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        val intent: Intent

        when (id) {
            R.id.action_add -> {
                intent = Intent(this@DashboardActivity, AddNewCaffeineActivity::class.java)
                startActivityForResult(intent, CAFFEINE_ADDED)
                return true
            }
        }


        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CAFFEINE_ADDED) {
            if (resultCode == Activity.RESULT_OK) {
                //AppRate.passSignificantEvent(this);
                getMeals()
            }

        }
    }

    companion object {

        private val TAG = DashboardActivity::class.java.simpleName
        val CAFFEINE_ADDED = 100

        val HOURS_72_MILLIS = 259200000
        val HOURS_48_MILLIS = 172800000
        val HOURS_IN_MILLIS = HOURS_72_MILLIS

        private fun getMealToDelete(xid: String): HashMap<String, String> {
            val queryHashMap = HashMap<String, String>()
            queryHashMap["meal_event_xid"] = xid

            return queryHashMap
        }

        private//uncomment to add as needed parameters
        //        queryHashMap.put("date", "<insert-date>");
        //        queryHashMap.put("page_token", "<insert-page-token>");
        //        queryHashMap.put("start_time", Integer.parseInt(String.valueOf(lastWeek.getTimeInMillis())));
        //        queryHashMap.put("end_time", Integer.parseInt(String.valueOf(currentDay.getTimeInMillis())));
        //        queryHashMap.put("updated_after", "<insert-time>");
        val mealEventsListRequestParams: HashMap<String, Int>
            get() = HashMap()
    }
}
