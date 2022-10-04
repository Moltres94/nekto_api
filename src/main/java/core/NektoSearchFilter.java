package core;

public class NektoSearchFilter {

    private final boolean isAdult;
    private final boolean role;
    private final boolean isVoiceDisable;
    private final int myAgeMin;
    private final int myAgeMax;
    private final String mySex;

    public boolean isAdult() {
        return isAdult;
    }

    public boolean isRole() {
        return role;
    }

    public boolean isVoiceDisable() {
        return isVoiceDisable;
    }

    public int getMyAgeMin() {
        return myAgeMin;
    }

    public int getMyAgeMax() {
        return myAgeMax;
    }

    public String getMySex() {
        return mySex;
    }

    public int getWishAgeMin() {
        return wishAgeMin;
    }

    public int getWishAgeMax() {
        return wishAgeMax;
    }

    public String getWishSex() {
        return wishSex;
    }

    public NektoSearchFilter(boolean isAdult, boolean role, boolean isVoiceDisable, int myAgeMin, int myAgeMax, String mySex, int wishAgeMin, int wishAgeMax, String wishSex) {
        this.isAdult = isAdult;
        this.role = role;
        this.isVoiceDisable = isVoiceDisable;
        this.myAgeMin = myAgeMin;
        this.myAgeMax = myAgeMax;
        this.mySex = mySex;
        this.wishAgeMin = wishAgeMin;
        this.wishAgeMax = wishAgeMax;
        this.wishSex = wishSex;
    }

    private final int wishAgeMin;
    private final int wishAgeMax;
    private final String wishSex;


}
