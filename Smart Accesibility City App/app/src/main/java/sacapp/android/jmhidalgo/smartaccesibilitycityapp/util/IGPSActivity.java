package sacapp.android.jmhidalgo.smartaccesibilitycityapp.util;

public interface IGPSActivity {
    public void locationChanged(double longitude, double latitude);
    public void displayGPSSettingsDialog();
}