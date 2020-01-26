package com.example.mobilecafe;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_TO_ORDER_ACTIVITY_REQUEST_CODE = 1;

    private ProductViewModel productViewModel;
    private Product productToOrder = null;

    static Order order = new Order();

    private void requestPermission(){
        ActivityCompat.requestPermissions(this, new String[]{CAMERA}, 1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setIcon(R.mipmap.ic_launcher);


        requestPermission();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                if(ActivityCompat.checkSelfPermission(MainActivity.this, CAMERA) != PackageManager.PERMISSION_GRANTED){
                    return;
                }
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivity(intent);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final ProductAdapter adapter = new ProductAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        productViewModel.findAll().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                adapter.setProducts(products);
            }
        });

        //ImageView cartImageView = findViewById(R.id.cart_image_view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
        switch(id){

            case R.id.action_settings:
                Intent intent = new Intent(MainActivity.this, OrderViewActivity.class);
                startActivity(intent);
                return true;

                default:
                    return super.onOptionsItemSelected(item);
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        //Order order = new Order();
        String name = data.getStringExtra(AddToOrderActivity.EXTRA_ADD_TO_ORDER_NAME);
        String size = data.getStringExtra(AddToOrderActivity.EXTRA_ADD_TO_ORDER_SIZE);
        String price = data.getStringExtra(AddToOrderActivity.EXTRA_ADD_TO_ORDER_PRICE);

        Log.d("PRODUCT", name + size + price);

        if(requestCode == ADD_TO_ORDER_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
            //Product product = new Product(data.getStringExtra(AddToOrderActivity.EXTRA_ADD_TO_ORDER_NAME), data.getStringExtra(AddToOrderActivity.EXTRA_ADD_TO_ORDER_SIZE), data.getStringExtra(AddToOrderActivity.EXTRA_ADD_TO_ORDER_PRICE));
            //Log.d("PRODUCT", product.getName() + product.getSize() + product.getPrice());
            //Log.d("XD", product);
            Product product = new Product(name, size, price);
            order.addToOrder(product);
            Snackbar.make(findViewById(R.id.coordinator_layout), getString(R.string.order_updated), Snackbar.LENGTH_LONG).show();
        }

    }

    private class ProductHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView productNameTextView;
        private TextView productSizeTextView;
        private TextView productPriceTextView;
        private Product product;

        public ProductHolder (LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.product_list_item, parent, false));
            itemView.setOnClickListener(this);
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

        @Override
        public void onClick(View v) {
            productToOrder = product;
            Intent intent = new Intent(MainActivity.this, AddToOrderActivity.class);
            intent.putExtra(AddToOrderActivity.EXTRA_ADD_TO_ORDER_NAME, product.getName());
            intent.putExtra(AddToOrderActivity.EXTRA_ADD_TO_ORDER_SIZE, product.getSize());
            intent.putExtra(AddToOrderActivity.EXTRA_ADD_TO_ORDER_PRICE, product.getPrice());
            startActivityForResult(intent, ADD_TO_ORDER_ACTIVITY_REQUEST_CODE);
        }

//        @Override
//        public boolean onLongClick(View v) {
//            Toast.makeText(
//                    getApplicationContext(),
//                    "Book deleted",
//                    Toast.LENGTH_LONG).show();
//            bookViewModel.delete(book);
//            return true;
//        }
    }

    private class ProductAdapter extends RecyclerView.Adapter<ProductHolder>{
        private List<Product> products;

        @NonNull
        @Override
        public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
            return new ProductHolder(getLayoutInflater(), parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ProductHolder holder, int position){
            if(products!=null) {
                Product product = products.get(position);
                holder.bind(product);
            } else {
                Log.d("MainActivity", "No products");
            }
        }

        @Override
        public int getItemCount(){
            if(products!=null){
                return products.size();
            } else{
                return 0;
            }
        }

        void setProducts(List<Product> products){
            this.products = products;
            notifyDataSetChanged();
        }
    }
}
