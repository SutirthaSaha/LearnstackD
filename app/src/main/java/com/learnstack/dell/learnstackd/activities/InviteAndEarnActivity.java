package com.learnstack.dell.learnstackd.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.learnstack.dell.learnstackd.R;

public class InviteAndEarnActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView referralCodeText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_and_earn);
        setToolbar();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        referralCodeText=findViewById(R.id.referralCodeText);

    }

    private void setToolbar() {
        toolbar=findViewById(R.id.inviteAndEarnToolbar);
        toolbar.setTitle("Invite and Earn");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.arrow_left);
    }

    public void onShareCodeClick(View view) {
        Intent intent=new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,"LearnStack Referral Code-\n"+referralCodeText.getText().toString().trim());
        startActivity(Intent.createChooser(intent,"Share Using"));

    }
}
