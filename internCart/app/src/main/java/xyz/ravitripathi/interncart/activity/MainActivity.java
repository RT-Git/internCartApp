package xyz.ravitripathi.interncart.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.ravitripathi.interncart.R;
import xyz.ravitripathi.interncart.adapters.ProductRecyclerAdapter;
import xyz.ravitripathi.interncart.networking.SearchAPI;
import xyz.ravitripathi.interncart.pojo.ProductPOJO;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private RecyclerView recyclerView;
    private SearchView searchView;
    private ProductRecyclerAdapter productRecyclerAdapter;
    private Context c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent i = getIntent();

        c = this;
        String uid = i.getStringExtra("uid");
//        if(uid==null || uid.isEmpty()){
//            Toast.makeText(this,"Please Login First to continue",Toast.LENGTH_SHORT);
//            startActivity(new Intent(this,LoginActivity.class));
//        }
        bindViews();

    }

    private void bindViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(c, 2));

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//        searchView = findViewById(R.id.search);


//        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
//            @Override
//            public boolean onClose() {
//                recyclerView.setAdapter(null);
//
//                return false;
//            }
//        });
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                String s = searchView.getQuery().toString();
//
//                searchForItem(s);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//
//                String s = searchView.getQuery().toString();
//
//                searchForItem(s);
//
//                return false;
//            }
//
//        });

    }


    private void searchForItem(String s) {
        final SearchAPI search = SearchAPI.retrofit.create(SearchAPI.class);
        Call<List<ProductPOJO>> call = search.search(s);
        call.enqueue(new Callback<List<ProductPOJO>>() {
            @Override
            public void onResponse(Call<List<ProductPOJO>> call, Response<List<ProductPOJO>> response) {
                try {
                    if (!response.body().isEmpty()) {
                        productRecyclerAdapter = new ProductRecyclerAdapter(MainActivity.this, response.body());
                        recyclerView.setAdapter(productRecyclerAdapter);
                    } else {

                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<ProductPOJO>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "FAILED", Toast.LENGTH_SHORT).show();
                Log.d("RESULT", "FAILED");
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                recyclerView.setAdapter(null);
                return false;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String s = searchView.getQuery().toString();

                searchForItem(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                String s = searchView.getQuery().toString();

                searchForItem(s);

                return false;
            }

        });
        MenuItem menuItem = menu.findItem(R.id.search);

        menuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                recyclerView.setAdapter(null);
                return true; // Return true to collapse action view
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.cart) {
            startActivity(new Intent(this, CartActivity.class));
        } else if (id == R.id.orders) {
            startActivity(new Intent(this, OrdersActivity.class));
        } else if (id == R.id.nav_logout) {
            logOut();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logOut() {
        Intent i = new Intent(this, LoginActivity.class);
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("uid", null);
        editor.commit();
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(i);
    }
}
