package cn.sdt.five;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import cn.sdt.connect.FServer;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickButton(View view) {
        int id = view.getId();
        if (id == R.id.create_game) {
            Intent intent = new Intent(this, GameActivity.class);
            intent.putExtra("Is_Server", true);
            startActivity(intent);
        }
    }

    @Override
    protected void onMessageReceived(String msgType, String msgContent) {
    }
}
