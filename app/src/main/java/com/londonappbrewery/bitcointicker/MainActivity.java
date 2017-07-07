package com.londonappbrewery.bitcointicker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

import org.json.JSONException;
import org.json.JSONObject;

import static android.os.Build.VERSION_CODES.BASE;
import static java.util.logging.Logger.global;


public class MainActivity extends AppCompatActivity {

    // Constants:
    // TODO: Create the base URL
    //Spinner mSpinner = (Spinner) findViewById(R.id.currency_spinner);
    // String currency = mSpinner.getSelectedItem().toString();
    public static String BASE_URL;

    // Member Variables:
    TextView mPriceTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPriceTextView = (TextView) findViewById(R.id.priceLabel);
        Spinner spinner = (Spinner) findViewById(R.id.currency_spinner);
        // Create an ArrayAdapter using the String array and a spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.currency_array, R.layout.spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        // TODO: Set an OnItemSelected listener on the spinner
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Log.d("Bitcoin", "" + parent.getItemAtPosition(position));
                   String currencyPair = (String) parent.getItemAtPosition(position);
               // String currencyPair = "USD";
                Log.d("Bitcoin", currencyPair);
                BASE_URL = "https://apiv2.bitcoinaverage.com/indices/global/ticker/BTC" + currencyPair;
                //  BASE_URL = "https://apiv2.bitcoinaverage.com/indices/global/ticker/BTCAUD";
                Log.d("Bitcoin", BASE_URL);
                RequestParams params = new RequestParams();
                params.put("market", "global");
                //  params.put("crypto", "BTC");
                // params.put("fiat", currencyPair);
                params.put("symbol", "BTC" + currencyPair);
                letsDoSomeNetworking(params);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d("Bitcoin", "Nothing selected");
            }
        });
    }

    // TODO: complete the letsDoSomeNetworking() method
    private void letsDoSomeNetworking(RequestParams params) {

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(BASE_URL, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // called when response HTTP status is "200 OK"
                Log.d("Bitcoin", "JSON: " + response.toString());
                BitcoinDataModel bitcoinDataModel = BitcoinDataModel.fromJson(response);
                updateUI(bitcoinDataModel);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Log.d("Bitcoin", "Request fail! Status code: " + statusCode);
                Log.d("Bitcoin", "Fail response: " + response);
                Log.e("Bitcoin", e.toString());
            }
        });


    }

    private void updateUI(BitcoinDataModel bDataModel) {
        Double price = bDataModel.getPrice();
        String x = price.toString();
        mPriceTextView.setText(x);
    }

}
