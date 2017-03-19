package example.com.foodcalculator;

/**
 * Created by micha on 3/19/2017.
 */

public class Nutrient {
    private String nutrient_id, nutrient, unit, value, gm;

    public Nutrient() {
    }

    public Nutrient(String nutrient_id, String nutrient, String unit, String value, String gm) {
        this.nutrient_id = nutrient_id;
        this.nutrient = nutrient;
        this.unit = unit;
        this.value = value;
        this.gm = gm;
    }

    public String getNutrient_id() {
        return nutrient_id;
    }

    public void setNutrient_id(String nutrient_id) {
        this.nutrient_id = nutrient_id;
    }

    public String getNutrient() {
        return nutrient;
    }

    public void setNutrient(String nutrient) {
        this.nutrient = nutrient;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getGm() {
        return gm;
    }

    public void setGm(String gm) {
        this.gm = gm;
    }
}
