package com.example.mobilecafe;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_TO_ORDER_ACTIVITY_REQUEST_CODE = 1;

    private ProductViewModel productViewModel;
    private Product productToOrder = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class ProductHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView productNameTextView;
        private TextView productSizeTextView;
        private Product product;

        public ProductHolder (LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.product_list_item, parent, false));
            itemView.setOnClickListener(this);
            //itemView.setOnLongClickListener(this);

            productNameTextView = itemView.findViewById(R.id.product_name);
            productSizeTextView = itemView.findViewById(R.id.product_size);
        }

        public void bind(Product product){
            this.product = product;
            productNameTextView.setText(product.getName());
            productSizeTextView.setText(product.getSize());
        }

        @Override
        public void onClick(View v) {
            productToOrder = product;
            Intent intent = new Intent(MainActivity.this, AddToOrderActivity.class);
            intent.putExtra(AddToOrderActivity.EXTRA_ADD_TO_ORDER_NAME, product.getName());
            intent.putExtra(AddToOrderActivity.EXTRA_ADD_TO_ORDER_SIZE, product.getSize());
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

        void setProducts(List<Product> books){
            this.products = books;
            notifyDataSetChanged();
        }
    }
}
