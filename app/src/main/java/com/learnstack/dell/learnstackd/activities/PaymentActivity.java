package com.learnstack.dell.learnstackd.activities;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.learnstack.dell.learnstackd.R;

public class PaymentActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private int courseFee=1000;
    private int discountAmt=0;
    private String couponCode;
    private EditText couponCodeText;
    private TextView discountAmtValue,totalPayableValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        toolbar=findViewById(R.id.paymentToolbar);
        couponCodeText=findViewById(R.id.couponCodeText);
        discountAmtValue=findViewById(R.id.discountAmtValue);
        totalPayableValue=findViewById(R.id.totalPayableValue);

        setToolbar();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
    private void setToolbar() {
        toolbar=findViewById(R.id.paymentToolbar);
        toolbar.setTitle("Subscription");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.arrow_left);
    }

    public void onCouponCodeClick(View view) {
        couponCode=couponCodeText.getText().toString().trim();
        if(!TextUtils.isEmpty(couponCode)){
            if(couponCode.equals("SUPER")){
                discountAmt=990;
                discountAmtValue.setText("Rs. "+Integer.toString(discountAmt));
                totalPayableValue.setText("Rs. "+Integer.toString(courseFee-discountAmt));
            }
        }
        else{
            discountAmt=0;
        }
    }

    public void onPayBtnClick(View view) {

    }
}
