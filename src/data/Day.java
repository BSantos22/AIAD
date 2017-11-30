package data;

import utils.MarketSettings;
import utils.Utils;

import java.util.Calendar;
import java.util.Random;

public class Day {
    private Calendar date;
    private float open;
    private float close;
    private float low;
    private float high;
    private int volume;

    public Day(Calendar date, float open, float close, float low, float high, int volume) {
        this.date = date;
        this.open = open;
        this.close = close;
        this.low = low;
        this.high = high;
        this.volume = volume;
    }

    // Get
    public Calendar getDate() {
        return date;
    }

    public float getOpen() {
        return open;
    }

    public float getClose() {
        return close;
    }

    public float getLow() {
        return low;
    }

    public float getHigh() {
        return high;
    }

    public int getVolume() {
        return volume;
    }

    public float getPrice(Calendar date) throws Exception {
        Random r = new Random();
        if (date.get(Calendar.HOUR_OF_DAY) < MarketSettings.OPEN_TIME || date.get(Calendar.HOUR_OF_DAY) > MarketSettings.CLOSE_TIME) {
            throw new Exception("Market closed");
        }
        float linear_price = (date.get(Calendar.HOUR_OF_DAY)-MarketSettings.OPEN_TIME)*(this.close-this.open)/(MarketSettings.CLOSE_TIME-MarketSettings.OPEN_TIME) + this.open;

        float variance = (float) r.nextGaussian()/3;
        if (date.get(Calendar.HOUR_OF_DAY) == MarketSettings.OPEN_TIME || date.get(Calendar.HOUR_OF_DAY) == MarketSettings.CLOSE_TIME) {
            variance = 0;
        }
        else if (this.high-linear_price > linear_price-this.low) {
            variance *= linear_price-this.low;
        }
        else {
            variance *= this.high-linear_price;
        }

        return linear_price+variance;
    }

    // Set
    public void setDate(Calendar date) {
        this.date = date;
    }

    public void setOpen(float open) {
        this.open = open;
    }

    public void setClose(float close) {
        this.close = close;
    }

    public void setLow(float low) {
        this.low = low;
    }

    public void setHigh(float high) {
        this.high = high;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }
}
