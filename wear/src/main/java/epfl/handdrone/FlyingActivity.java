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
import android.widget.ImageView;
import android.widget.TextView;


public class FlyingActivity extends WearableActivity implements SensorEventListener {


    private SensorManager sensorManager;
    private Sensor acc_sensor;
    private PowerManager PowerManager;

    private ImageView imageView;
    private TextView textViewAcc_x;
    private TextView textViewAcc_y;
    private TextView textViewAcc_z;

    public static int home = 0, forward = 1, backward = 2, left = 3, right = 4,
            up = 5, down = 6, rot_cw = 7, rot_ccw = 8;

    private int mode = home, prev_mode = home;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get an instance of the SensorManager
        final SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        acc_sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);//TYPE_LINEAR_ACCELERATION);
        sensorManager.registerListener(this, acc_sensor, SensorManager.SENSOR_DELAY_UI);

        // Get an instance of the PowerManager
        PowerManager = (PowerManager) getSystemService(POWER_SERVICE);

        // Get GUI elements
        imageView = findViewById(R.id.imageView);/*
        textViewAcc_x = findViewById(R.id.acc_value_x);
        textViewAcc_y = findViewById(R.id.acc_value_y);
        textViewAcc_z = findViewById(R.id.acc_value_z);*/


        setContentView(R.layout.activity_flying);
        setAmbientEnabled();
    }



    public void clickedStopFlyingXmlCallback(View view) {
        Intent intent = new Intent(this, LauncherActivity.class);
        startActivity(intent);
    }


    // From HR sensor lab where listener has a rate suitable for User Interface, maybe not for control?
    //Great examples, also for gyro: https://developer.android.com/reference/android/hardware/SensorEvent
    @Override
    public void onSensorChanged(SensorEvent event) {

        float acc_x, acc_y, acc_z;
        acc_x = event.values[0];
        acc_y = event.values[1];
        acc_z = event.values[2];


        imageView = findViewById(R.id.imageView);
/*
        textViewAcc_x = findViewById(R.id.acc_value_x);
        textViewAcc_y = findViewById(R.id.acc_value_y);
        textViewAcc_z = findViewById(R.id.acc_value_z);*/


        /*if (textViewAcc_x != null)
            textViewAcc_x.setText(String.valueOf(acc_x));
        if (textViewAcc_y != null)
            textViewAcc_y.setText(String.valueOf(acc_y));
        if (textViewAcc_z != null)
            textViewAcc_z.setText(String.valueOf(acc_z));*/

        //Example from lab, intent to be implemented to send gesture to tablet
/*        Intent intent = new Intent(RecordingActivity.this, WearService.class);
        intent.setAction(WearService.ACTION_SEND.HEART_RATE.name());
        intent.putExtra(WearService.HEART_RATE, heartRate);
        startService(intent);*/


        if(acc_x > 2 && acc_z > 8)
            mode = left;
        else if(acc_x < -3 && acc_z > 8)
            mode = right;
        else if(acc_y > 5 && acc_z > 6)
            mode = backward;
        else if(acc_y < -4)
            mode = forward;
        else {
            mode = home;
        }

        if(mode != prev_mode) {
            switch (mode) {
                case 0:
                    Log.i("mode", "Home");
                    imageView.setImageResource(R.drawable.home);
                    break;
                case 1:
                    Log.i("mode", "Forward");
                    imageView.setImageResource(R.drawable.forwd);
                    break;
                case 2:
                    Log.i("mode", "Backwards");
                    imageView.setImageResource(R.drawable.backwd);
                    break;
                case 3:
                    Log.i("mode", "Left");
                    imageView.setImageResource(R.drawable.left);
                    break;
                case 4:
                    Log.i("mode", "Right");
                    imageView.setImageResource(R.drawable.right);
                    break;
            }
            prev_mode = mode;
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy){}

/*    @Override
    public void onAccuracyChanged(SensorEvent event){

    }*/

    @Override
    public void onPause() {
        //sensorManager.unregisterListener(this, acc_sensor);
        super.onPause();
    }

}


