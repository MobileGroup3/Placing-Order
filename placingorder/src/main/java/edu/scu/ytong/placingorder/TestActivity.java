package edu.scu.ytong.placingorder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Button button = (Button) findViewById(R.id.button_test);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestActivity.this,PlacingOrder.class);
                String objectId = "4382FE98-8473-3598-FF63-E21AC0287E00";
                intent.putExtra("object_id_extra_key",objectId);
                startActivity(intent);
            }
        });
    }
}
