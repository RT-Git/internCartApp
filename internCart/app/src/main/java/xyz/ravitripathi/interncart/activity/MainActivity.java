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
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.ravitripathi.interncart.R;
import xyz.ravitripathi.interncart.adapters.ProductRecyclerAdapter;
import xyz.ravitripathi.interncart.networking.LatestAPI;
import xyz.ravitripathi.interncart.networking.SearchAPI;
import xyz.ravitripathi.interncart.pojo.ProductPOJO;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private RecyclerView recyclerView;
    private boolean isGuest = false;
    private SearchView searchView;
    private ProductRecyclerAdapter productRecyclerAdapter;
    private Context c;
    private SliderLayout carouselView;
    private LottieAnimationView lottieAnimationView;
    private RelativeLayout content;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent i = getIntent();
        c = this;

        String val = i.getStringExtra("uid");
        if ("guest".equals(val)) {
            isGuest = true;
        }
        SharedPreferences sharedPref = getApplicationContext()
                .getSharedPreferences("shared", 0);
        String uidFromStorage = sharedPref.getString("uid", "0");
        Log.e("INTERNAL", uidFromStorage);
        Toast.makeText(this, uidFromStorage, Toast.LENGTH_SHORT).show();

        bindViews();
        getLatest();

    }


    private void bindViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(c, 2));
        recyclerView.setNestedScrollingEnabled(false);
        lottieAnimationView = findViewById(R.id.loading_view);
        carouselView = findViewById(R.id.carousal);
        content = findViewById(R.id.content);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }


    private void getLatest() {
        Toast.makeText(MainActivity.this, "Call Made", Toast.LENGTH_SHORT).show();
        setLoading();
        final LatestAPI latestAPI = LatestAPI.retrofit.create(LatestAPI.class);
        Call<List<ProductPOJO>> call = latestAPI.latest();
        call.enqueue(new Callback<List<ProductPOJO>>() {
            @Override
            public void onResponse(Call<List<ProductPOJO>> call, Response<List<ProductPOJO>> response) {
                setContent();
                Toast.makeText(MainActivity.this, "Response Recieved", Toast.LENGTH_SHORT).show();

                try {
                    Log.d("Body", response.body().get(0).getPimage());


                    for (int i = 0; i < 4; i++) {
                        TextSliderView textSliderView = new TextSliderView(MainActivity.this);
                        // initialize a SliderLayout
                        textSliderView
                                .description(response.body().get(i).getpName())
                                .image(response.body().get(i).getPimage())
                                .setScaleType(BaseSliderView.ScaleType.Fit);
                        //add your extra information

                        carouselView.addSlider(textSliderView);
                    }
                    carouselView.setPresetTransformer(SliderLayout.Transformer.Accordion);
                    carouselView.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                    carouselView.setCustomAnimation(new DescriptionAnimation());
                    carouselView.setDuration(4000);

                    productRecyclerAdapter = new ProductRecyclerAdapter(MainActivity.this, response.body());
                    recyclerView.setAdapter(productRecyclerAdapter);
                } catch (Exception e) {
                    e.printStackTrace();
                    setError();
                }

            }

            @Override
            public void onFailure(Call<List<ProductPOJO>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "failed", Toast.LENGTH_SHORT).show();
                setError();
                t.printStackTrace();
            }
        });
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
                        carouselView.setVisibility(View.GONE);
                    } else {

                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<ProductPOJO>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "FAILED", Toast.LENGTH_SHORT).show();
                setError();
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
                recyclerView.setVisibility(View.GONE);
                carouselView.setVisibility(View.VISIBLE);
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
                carouselView.setVisibility(View.VISIBLE);
                getLatest();
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
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {


        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (isGuest) {
            Toast.makeText(this, "Please Login to continue", Toast.LENGTH_LONG).show();
            Intent i = new Intent(this,LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        } else {
            if (id == R.id.cart) {
                startActivity(new Intent(this, CartActivity.class));
            } else if (id == R.id.orders) {
                startActivity(new Intent(this, OrdersActivity.class));
            } else if (id == R.id.nav_logout) {
                logOut();
            }
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logOut() {
        Intent i = new Intent(this, LoginActivity.class);
        SharedPreferences sharedPref = getApplicationContext()
                .getSharedPreferences("shared", 0);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.commit();


        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(i);
    }

    private void setContent() {
        lottieAnimationView.setVisibility(View.GONE);
        content.setVisibility(View.VISIBLE);

    }

    private void setLoading() {
        lottieAnimationView.setVisibility(View.VISIBLE);
        content.setVisibility(View.GONE);
        lottieAnimationView.setAnimation("loading_animation.json");
        lottieAnimationView.loop(true);
        lottieAnimationView.playAnimation();
    }

    private void setError() {
        lottieAnimationView.setVisibility(View.VISIBLE);
        content.setVisibility(View.GONE);
        lottieAnimationView.setAnimation("warning_sign.json");
        lottieAnimationView.loop(true);
        lottieAnimationView.playAnimation();
        lottieAnimationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLatest();
            }
        });
    }

}
