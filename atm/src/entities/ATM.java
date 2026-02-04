package entities;

import java.util.Map;

public class ATM {

    private Map<Integer, Integer> cash; // denomination -> quantity
    private int paper;
    private int ink;
    private String firmwareVersion;

    public ATM(Map<Integer, Integer> cash, int paper, int ink, String firmwareVersion) {
        this.cash = cash;
        this.paper = paper;
        this.ink = ink;
        this.firmwareVersion = firmwareVersion;
    }

    public Map<Integer, Integer> getCash() {
        return cash;
    }

    public void setCash(Map<Integer, Integer> cash) {
        this.cash = cash;
    }

    public int getPaper() {
        return paper;
    }

    public void setPaper(int paper) {
        this.paper = paper;
    }

    public int getInk() {
        return ink;
    }

    public void setInk(int ink) {
        this.ink = ink;
    }

    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }

    public boolean hasPaper() {
        return paper > 0;
    }

    public void usePaper() {
        if (paper > 0) {
            paper--;
        }
    }
}
