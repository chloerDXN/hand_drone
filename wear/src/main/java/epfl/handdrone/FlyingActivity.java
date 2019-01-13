package epfl.handdrone;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.PowerManager;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


public class FlyingActivity extends WearableActivity implements SensorEventListener {


    private SensorManager sensorManager;
    private Sensor acc_sensor;
    private PowerManager PowerManager;

    private ImageView imageView;
    private Switch switch_mode;
    private TextView textViewAcc_x;
    private TextView textViewAcc_y;
    private TextView textViewAcc_z;

    public static int home = 0, forward = 1, backward = 2, left = 3, right = 4,
            up = 5, down = 6, rot_cw = 7, rot_ccw = 8, recovery = 9, undefined = 10;
    public static int xy = 0, vertical = 1;
    private int action = home, prev_action = recovery, mode = xy;
    private String mode_str = "XY";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flying);
        setAmbientEnabled();

        // Get an instance of the SensorManager
        final SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        acc_sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);//TYPE_LINEAR_ACCELERATION);
        sensorManager.registerListener(this, acc_sensor, SensorManager.SENSOR_DELAY_UI);

        // Get an instance of the PowerManager
        PowerManager = (PowerManager) getSystemService(POWER_SERVICE);

        // Get GUI elements
        imageView = findViewById(R.id.imageView);
        switch_mode = (Switch) findViewById(R.id.switch1);
        /*
        textViewAcc_x = findViewById(R.id.acc_value_x);
        textViewAcc_y = findViewById(R.id.acc_value_y);
        textViewAcc_z = findViewById(R.id.acc_value_z);*/

        /*switch_mode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mode = vertical;
                } else {
                    mode = xy;
                }
                Toast.makeText(getApplicationContext(), "Mode changed to: "+ mode_str,Toast.LENGTH_SHORT).show();
            }
        });*/

        switch_mode.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(switch_mode.isChecked())
                {
                    mode = vertical;
                    Toast.makeText(getApplicationContext(), "Mode changed to Vertical", Toast.LENGTH_SHORT).show();
                }
                else {
                    mode = xy;
                    Toast.makeText(getApplicationContext(), "Mode changed to XY", Toast.LENGTH_SHORT).show();
                }

            }
        });




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

        double thresh = 12;

        if(acc_x > thresh || acc_y > thresh || acc_z > thresh){
            action = recovery; // enter recovery mode in case of brusque movements
            Log.i("action", "Jerk acc case");
        }
        else if(acc_x >-1 && acc_x < 1 && acc_y > -6 && acc_y < -4 && acc_z > 8){
            action = home;
        }
        else if(acc_x > 2 && acc_z > 8)
            action = 3;
        else if(acc_x < -3 && acc_z > 8)
            action = 4;
        else if(acc_y > 5 && acc_z > 6)
            action = 2;
        else if(acc_y < -4)
            action = 1;
        else {
            action = undefined;
            Log.i("action", "undefined acc case");
        }

        if(mode == vertical && action != home && action != recovery)
            action = action + 4; // offset for vertical mode

        if(prev_action == recovery && action != home)
            action = recovery; // stay in recovery mode as long as the wrist is not back to rest position

        if(action == undefined && prev_action != recovery)
            action = prev_action;

/*        public static int home = 0, forward = 1, backward = 2, left = 3, right = 4,
                up = 5, down = 6, rot_cw = 7, rot_ccw = 8;*/

        if(action != prev_action) {
            switch (action) {
                case 0:
                    Log.i("action", "Home");
                    imageView.setImageResource(R.drawable.home);
                    break;
                case 1:
                    Log.i("action", "Forward");
                    imageView.setImageResource(R.drawable.forwd);
                    break;
                case 2:
                    Log.i("action", "Backwards");
                    imageView.setImageResource(R.drawable.backwd);
                    break;
                case 3:
                    Log.i("action", "Left");
                    imageView.setImageResource(R.drawable.left);
                    break;
                case 4:
                    Log.i("action", "Right");
                    imageView.setImageResource(R.drawable.right);
                    break;
                case 5:
                    Log.i("action", "Up");
                    imageView.setImageResource(R.drawable.up);
                    break;
                case 6:
                    Log.i("action", "Down");
                    imageView.setImageResource(R.drawable.down);
                    break;
                case 7:
                    Log.i("action", "Counter-clockwise rotation");
                    imageView.setImageResource(R.drawable.rot_ccw);
                    break;
                case 8:
                    Log.i("action", "Clockwise rotation");
                    imageView.setImageResource(R.drawable.rot_cw);
                    break;
                case 9:
                    Log.i("action", "!Recovery mode!");
                    imageView.setImageResource(R.drawable.recover);
                    break;
                case 10:
                    Log.i("action", "Undefined");
                    imageView.setImageResource(R.drawable.recover);
                    break;

            }
            prev_action = action;
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


