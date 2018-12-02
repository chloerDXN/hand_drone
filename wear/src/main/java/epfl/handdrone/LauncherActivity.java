package epfl.handdrone;

import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.TextView;

public class LauncherActivity extends WearableActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        mTextView = (TextView) findViewById(R.id.text);

        // Enables Always-on
        setAmbientEnabled();
    }

    public void clickedTakeOffXmlCallback(View view) {
        TextView textView = findViewById(R.id.TakeOffMessage);
        textView.setText("Ready to take off!");

        Intent intent = new Intent(this, FlyingActivity.class);
        startActivity(intent);
    }
}
