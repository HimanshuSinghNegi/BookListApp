package com.example.mybooklistapp;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.mybooklistapp.Model.Book;
import com.example.mybooklistapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements BookItemClicked {

    //URL  of book List
    private static final String USGS_REQUEST_URL = "https://www.googleapis.com/books/v1/volumes?q=android&maxResults=23";
    MyCustomAdapter adapter;
    //using the Activity Binding
    ActivityMainBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        // Create a new adapter that takes an empty list of books as input
        adapter = new MyCustomAdapter(this, MainActivity.this);


        //Calling the fetchDta function fetchBookData
        Utils.fetchBookData(USGS_REQUEST_URL, this, adapter, binding.progressBar);

        //LinearLayout Manager to show the list of the books
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.list.setLayoutManager(linearLayoutManager);
        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        binding.list.setAdapter(adapter);

        //my searchView
        binding.mySearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                Log.i("new", newText);

                //calling the getFilter method which is declared inside the adapter class
                adapter.getFilter().filter(newText);
                return false;
            }
        });

    }


    /// Here override the BookItemClicked Interface function
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onItemClicked(Book item) {
        //using intent which will redirect user to chrome or any browser
        //to preview the book
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(item.getPreviewLink()));
        startActivity(i);


    }
}