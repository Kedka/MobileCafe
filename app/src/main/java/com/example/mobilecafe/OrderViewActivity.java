package com.example.mobilecafe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import static com.example.mobilecafe.MainActivity.order;

public class OrderViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_view);

        RecyclerView recyclerView = findViewById(R.id.order_view_recyclerview);
        final ProductAdapter adapter = new ProductAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        TextView totalPrice = findViewById(R.id.total_price);
        totalPrice.setText(String.valueOf(order.totalPrice()));

        Button buttonOrder = findViewById(R.id.button_order);
        buttonOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderViewActivity.this, AcceptOrderActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private class ProductHolder extends RecyclerView.ViewHolder{ //implements View.OnClickListener{

        private TextView productNameTextView;
        private TextView productSizeTextView;
        private TextView productPriceTextView;
        private Product product;

        public ProductHolder (LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.product_list_item, parent, false));
           // itemView.setOnClickListener(this);
            //itemView.setOnLongClickListener(this);

            productNameTextView = itemView.findViewById(R.id.product_name);
            productSizeTextView = itemView.findViewById(R.id.product_size);
            productPriceTextView = itemView.findViewById(R.id.product_price);
        }

        public void bind(Product product){
            this.product = product;
            productNameTextView.setText(product.getName());
            productSizeTextView.setText(product.getSize());
            productPriceTextView.setText(product.getPrice());
        }

//        @Override
//        public void onClick(View v) {
//            productToOrder = product;
//            Intent intent = new Intent(MainActivity.this, AddToOrderActivity.class);
//            intent.putExtra(AddToOrderActivity.EXTRA_ADD_TO_ORDER_NAME, product.getName());
//            intent.putExtra(AddToOrderActivity.EXTRA_ADD_TO_ORDER_SIZE, product.getSize());
//            intent.putExtra(AddToOrderActivity.EXTRA_ADD_TO_ORDER_PRICE, product.getPrice());
//            startActivityForResult(intent, ADD_TO_ORDER_ACTIVITY_REQUEST_CODE);
//        }

//        @Override
//        public boolean onLongClick(View v) {
//            bookViewModel.delete(book);
//            return true;
//        }
    }

    private class ProductAdapter extends RecyclerView.Adapter<ProductHolder>{
        //private Order products;

        @NonNull
        @Override
        public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
            return new ProductHolder(getLayoutInflater(), parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ProductHolder holder, int position){
            if(order!=null) {
                Product product = order.get(position);
                holder.bind(product);
            } else {
                Log.d("OrderViewActivity", "No products");
            }
        }

        @Override
        public int getItemCount(){
            if(order!=null){
                return order.size();
            } else{
                return 0;
            }
        }

//        void setProducts(List<Product> products){
//            this.order = products;
//            notifyDataSetChanged();
//        }
    }
}
