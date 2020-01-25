package com.example.mobilecafe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class AddToOrderActivity extends AppCompatActivity {

    public static final String EXTRA_ADD_TO_ORDER_NAME = "ADD_TO_ORDER_NAME";
    public static final String EXTRA_ADD_TO_ORDER_SIZE = "ADD_TO_ORDER_SIZE";

    private TextView productNameTextView;
    private TextView productSizeTextView;
    private Button buttonYes;
    private Button buttonNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_order);

        productNameTextView = findViewById(R.id.product_name_order);
        productSizeTextView = findViewById(R.id.product_size_order);
        buttonNo = findViewById(R.id.button_no);
        buttonYes = findViewById(R.id.button_yes);

        if(getIntent().hasExtra(EXTRA_ADD_TO_ORDER_NAME) && getIntent().hasExtra(EXTRA_ADD_TO_ORDER_SIZE)){
            productNameTextView.setText(getIntent().getSerializableExtra(EXTRA_ADD_TO_ORDER_NAME).toString());
            productSizeTextView.setText(getIntent().getSerializableExtra(EXTRA_ADD_TO_ORDER_SIZE).toString());
        }
    }
}
