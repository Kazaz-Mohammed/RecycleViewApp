package com.example.recyleview;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
//import android.widget.SearchView;
import androidx.appcompat.widget.SearchView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyleview.adapter.StarAdapter;
import com.example.recyleview.beans.Star;
import com.example.recyleview.service.StarService;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    private List<Star> stars;
    private RecyclerView recyclerView;
    private StarAdapter starAdapter = null;
    private StarService service;
    private static final String TAG = "ListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        stars = new ArrayList<>();
        service = StarService.getInstance();
        if (service.findAll().isEmpty()){
            init();
        }

        recyclerView = findViewById(R.id.recycle_view);
        starAdapter = new StarAdapter(this, service.findAll());
        recyclerView.setAdapter(starAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void init(){
        service.create(new Star("kate bosworth", "https://www.hawtcelebs.com/wp-content/uploads/2025/03/lucy-hale-out-and-about-in-beverly-hills-03-20-2025-6.jpg", 3.5f));
        service.create(new Star("george clooney", "https://fr.web.img5.acsta.net/r_1280_720/pictures/16/05/12/17/04/136865.jpg", 3));
        service.create(new Star("michelle rodriguez",
                "https://fr.web.img2.acsta.net/c_310_420/pictures/19/05/22/10/29/0914375.jpg", 5));
        service.create(new Star("vin diesel", "https://fr.web.img4.acsta.net/c_310_420/pictures/15/10/14/11/30/335169.jpg", 1));
        service.create(new Star("cristiano ronaldo", "https://fr.web.img2.acsta.net/c_310_420/pictures/16/05/12/10/44/262752.jpg", 5));
        service.create(new Star("brad pitt", "https://fr.web.img6.acsta.net/c_310_420/pictures/20/02/10/10/37/1374948.jpg", 1));
        service.create(new Star("george clooney", "https://fr.web.img5.acsta.net/r_1280_720/pictures/16/05/12/17/04/136865.jpg", 3));
        service.create(new Star("michelle rodriguez",
                "https://fr.web.img2.acsta.net/c_310_420/pictures/19/05/22/10/29/0914375.jpg", 5));
        service.create(new Star("vin diesel", "https://fr.web.img4.acsta.net/c_310_420/pictures/15/10/14/11/30/335169.jpg", 1));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu started");

        getMenuInflater().inflate(R.menu.menu, menu);
        Log.d(TAG, "Menu inflated successfully");

        MenuItem menuItem = menu.findItem(R.id.app_bar_search);
        if (menuItem == null) {
            Log.e(TAG, "menuItem is null! Check menu.xml");
            return true;
        }
        Log.d(TAG, "MenuItem found");

        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) menuItem.getActionView();
        if (searchView == null) {
            Log.e(TAG, "SearchView is null! Cannot set listener.");
            return true;
        }
        Log.d(TAG, "SearchView initialized");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, "Search submitted: " + query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d(TAG, "Search text changed: " + newText);
                if (starAdapter != null) {
                    starAdapter.getFilter().filter(newText);
                } else {
                    Log.e(TAG, "starAdapter is null, filtering skipped");
                }
                return true;
            }
        });

        Log.d(TAG, "SearchView listener set successfully");
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.share) {
            String txt = "Decouvrez notre application Stars !";
            String mimeType = "text/plain";

            ShareCompat.IntentBuilder
                    .from(this)
                    .setType(mimeType)
                    .setChooserTitle("Partager avec")
                    .setText(txt)
                    .startChooser();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
