package ma.unipu.hr.hortikulturni_monitor.util;

import java.io.Serializable;

/**
 * @author Mihovil
 */
public class Flower implements Serializable {
    private String name;
    private String desc;
    private String timeStamp;
    private int temperature;
    private int sunlight;
    private int soil;

    public Flower() {}

    public Flower(String name, String desc, String timeStamp, int temperature, int soil, int sunlight) {
        this.name = name;
        this.desc = desc;
        this.timeStamp = timeStamp;
        this.temperature = temperature;
        this.soil = soil;
        this.sunlight = sunlight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getSunlight() {
        return sunlight;
    }

    public void setSunlight(int sunlight) {
        this.sunlight = sunlight;
    }

    public int getSoil() {
        return soil;
    }

    public void setSoil(int soil) {
        this.soil = soil;
    }
}
