package com.londonappbrewery.bitcointicker;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Rahma on 7/4/2017.
 */

public class BitcoinDataModel {
    private Double mPrice;

    public static BitcoinDataModel fromJson(JSONObject jsonObject) {
        try {

            BitcoinDataModel bitCoin = new BitcoinDataModel();
            bitCoin.mPrice = jsonObject.getJSONObject("changes").getJSONObject("price").getDouble("year");
            return bitCoin;
        } catch (JSONException e) {
            e.printStackTrace();
            return  null;
        }


    }

    public Double getPrice() {
        return mPrice;
    }
}
