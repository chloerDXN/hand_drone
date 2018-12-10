package epfl.handdrone;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


public class FlyingActivity extends WearableActivity implements SensorEventListener {


    private SensorManager sensorManager;
    private Sensor acc_sensor;
    private PowerManager PowerManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get an instance of the SensorManager
        final SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        acc_sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        sensorManager.registerListener(this, acc_sensor, SensorManager.SENSOR_DELAY_UI);

        // Get an instance of the PowerManager
        PowerManager = (PowerManager) getSystemService(POWER_SERVICE);


        setContentView(R.layout.activity_flying);
        setAmbientEnabled();
    }

/*    @Override
    public void onEnterAmbient(Bundle ambientDetails) {
        super.onEnterAmbient(ambientDetails);
        updateDisplay();
    }

    @Override
    public void onExitAmbient() {
        super.onExitAmbient();
        updateDisplay();
    }*/

    public void clickedStopFlyingXmlCallback(View view) {
        Intent intent = new Intent(this, LauncherActivity.class);
        startActivity(intent);
    }

    //acc_value_x

    // From HR sensor lab where listener has a rate suitable for User Interface, maybe not for control?
    //Great examples, also for gyro: https://developer.android.com/reference/android/hardware/SensorEvent
    @Override
    public void onSensorChanged(SensorEvent event) {

        TextView textViewAcc_x = findViewById(R.id.acc_value_x);
        TextView textViewAcc_y = findViewById(R.id.acc_value_y);
        TextView textViewAcc_z = findViewById(R.id.acc_value_z);

        if (textViewAcc_x != null)
            textViewAcc_x.setText(String.valueOf(event.values[0]));
        if (textViewAcc_y != null)
            textViewAcc_y.setText(String.valueOf(event.values[1]));
        if (textViewAcc_z != null)
            textViewAcc_z.setText(String.valueOf(event.values[2]));


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy){}

/*    @Override
    public void onAccuracyChanged(SensorEvent event){

    }*/

}


