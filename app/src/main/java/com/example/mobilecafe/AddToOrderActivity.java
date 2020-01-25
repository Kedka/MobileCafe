package com.example.mobilecafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AddToOrderActivity extends AppCompatActivity {

    public static final String EXTRA_ADD_TO_ORDER_NAME = "ADD_TO_ORDER_NAME";
    public static final String EXTRA_ADD_TO_ORDER_SIZE = "ADD_TO_ORDER_SIZE";
    public static final String EXTRA_ADD_TO_ORDER_PRICE = "ADD_TO_ORDER_PRICE";


    private TextView productNameTextView;
    private TextView productSizeTextView;
    private TextView productPriceTextView;
    private Button buttonYes;
    private Button buttonNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_order);

        productNameTextView = findViewById(R.id.product_name_order);
        productSizeTextView = findViewById(R.id.product_size_order);
        productPriceTextView = findViewById(R.id.product_price_order);
        buttonNo = findViewById(R.id.button_no);
        buttonYes = findViewById(R.id.button_yes);

        if(getIntent().hasExtra(EXTRA_ADD_TO_ORDER_NAME) && getIntent().hasExtra(EXTRA_ADD_TO_ORDER_SIZE) && getIntent().hasExtra(EXTRA_ADD_TO_ORDER_PRICE)){
            productNameTextView.setText(getIntent().getSerializableExtra(EXTRA_ADD_TO_ORDER_NAME).toString());
            productSizeTextView.setText(getIntent().getSerializableExtra(EXTRA_ADD_TO_ORDER_SIZE).toString());
            productPriceTextView.setText(getIntent().getSerializableExtra(EXTRA_ADD_TO_ORDER_PRICE).toString());
            //SpannableString spannableString = new SpannableString();
            //spannableString.append(getIntent().getSerializableExtra(EXTRA_ADD_TO_ORDER_PRICE.toString()));
            //productPriceTextView.setText(getIntent().getSerializableExtra(EXTRA_ADD_TO_ORDER_PRICE.toString()));
        }

        buttonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent replyIntent = new Intent();
                setResult(RESULT_CANCELED, replyIntent);
                finish();
            }
        });

        buttonYes.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent replyIntent = new Intent();
                String name = productNameTextView.getText().toString();
                String size = productSizeTextView.getText().toString();
                String price = productPriceTextView.getText().toString();
                replyIntent.putExtra(EXTRA_ADD_TO_ORDER_NAME, name);
                replyIntent.putExtra(EXTRA_ADD_TO_ORDER_SIZE, size);
                replyIntent.putExtra(EXTRA_ADD_TO_ORDER_PRICE, price);
                setResult(RESULT_OK, replyIntent);
                finish();
            }
        });
    }
}
