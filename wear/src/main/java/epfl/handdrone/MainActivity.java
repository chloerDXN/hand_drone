package epfl.handdrone;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends WearableActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.text);

        // Enables Always-on
        setAmbientEnabled();
    }

    public void clickedTakeOffXmlCallback(View view) {
        TextView textView = findViewById(R.id.TakeOffMessage)   ;
        textView.setText("Ready to take off!");
    }
}
