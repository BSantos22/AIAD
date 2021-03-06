package ontology;

import jade.content.Concept;

import java.util.Comparator;
import java.util.Random;

import static utils.Settings.INVESTOR_MAX_SKILL;

// Class used to pass stock price info from informer to investor
public class StockPrice implements Concept {
    private String symbol;
    private int sector;
    private float currPrice;  // price at the current time
    private float futurePrice;  // price at next hour

    public StockPrice() {}

    public StockPrice(String symbol, int sector, float currPrice, float futurePrice) {
        this.symbol = symbol;
        this.sector = sector;
        this.currPrice = currPrice;
        this.futurePrice = futurePrice;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getSector() {
        return sector;
    }

    public void setSector(int sector) {
        this.sector = sector;
    }

    public float getCurrPrice() {
        return currPrice;
    }

    public void setCurrPrice(float currPrice) {
        this.currPrice = currPrice;
    }

    public float getFuturePrice() {
        return futurePrice;
    }

    public void setFuturePrice(float hourPrice) {
        this.futurePrice = hourPrice;
    }

    public float getGrowth() {
        return (futurePrice-currPrice)/currPrice;
    }

    // Introduces error into hour price according to skill
    // The higher the skill the more accurate the return value is
    public void addError(int skill) {
        Random r = new Random();
        if (skill < r.nextInt(INVESTOR_MAX_SKILL)) {
            // Error is x% of price with x being lower the higher the skill of the investor
            float error = (INVESTOR_MAX_SKILL - skill - 1) * (float) r.nextGaussian() / 100f;
            futurePrice = futurePrice+futurePrice*error;
        }
    }

    @Override
    public String toString() {
        Float growth = (futurePrice-currPrice)/currPrice;
        return growth.toString();
    }

    // Growth from lowest to highest
    public static class StockPriceComparator implements Comparator {
        @Override
        public int compare(Object o1, Object o2) {
            if (!(o1 instanceof StockPrice) || !(o2 instanceof StockPrice)) {
                throw new ClassCastException();
            }

            StockPrice s1 = (StockPrice) o1;
            StockPrice s2 = (StockPrice) o2;

            return Float.compare(s1.getGrowth(), s2.getGrowth());
        }
    }
}
