package com.clearsale.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.clearsale.R;
import com.clearsale.model.Property;
import com.clearsale.utils.AppConfigTags;
import com.clearsale.utils.AppConfigURL;
import com.clearsale.utils.BuyerDetailsPref;
import com.clearsale.utils.Constants;
import com.clearsale.utils.FilterDetailsPref;
import com.clearsale.utils.NetworkConnection;
import com.clearsale.utils.Utils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;


public class AllPropertyLocationActivity extends AppCompatActivity implements GoogleMap.OnMarkerClickListener, OnMapReadyCallback {
    SupportMapFragment mapFragment;
    
    CoordinatorLayout clMain;
    ProgressDialog progressDialog;
    BuyerDetailsPref buyerDetailsPref;
    private GoogleMap mMap;
    
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_all_property_location);
        initView ();
        initData ();
        initListener ();
        getAllProperty ();
    }
    
    
    private void initView () {
        clMain = (CoordinatorLayout) findViewById (R.id.clMain);
        mapFragment = (SupportMapFragment) getSupportFragmentManager ().findFragmentById (R.id.map);
        mapFragment.getMapAsync (this);
    }
    
    private void initData () {
        buyerDetailsPref = BuyerDetailsPref.getInstance ();
        progressDialog = new ProgressDialog (this);
    }
    
    private void initListener () {
        
    }
    
    @Override
    public boolean onMarkerClick (Marker marker) {
//        Integer clickCount = (Integer) marker.getTag ();
        // Check if a click count was set, then display the click count.
//        if (clickCount != null) {
//            clickCount = clickCount + 1;
//            marker.setTag (clickCount);
//            Toast.makeText (this,
//                    marker.getTitle () +
//                            " has been clicked " + clickCount + " times.",
//                    Toast.LENGTH_SHORT).show ();
//        }
        return false;
    }
    
    @Override
    public void onMapReady (GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings ().setZoomControlsEnabled (true);
        mMap.setOnInfoWindowClickListener (new GoogleMap.OnInfoWindowClickListener () {
            @Override
            public void onInfoWindowClick (Marker arg0) {
                Property property = (Property) arg0.getTag ();
                Intent intent = new Intent (AllPropertyLocationActivity.this, PropertyDetailActivity.class);
    
                intent.putExtra (AppConfigTags.PROPERTY_ID, property.getId ());
                intent.putExtra (AppConfigTags.PROPERTY_ADDRESS, property.getAddress1 ());
                intent.putExtra (AppConfigTags.PROPERTY_ADDRESS2, property.getAddress2 ());
                intent.putExtra (AppConfigTags.PROPERTY_AREA, property.getArea ());
                intent.putExtra (AppConfigTags.PROPERTY_BATHROOMS, property.getBathroom ());
                intent.putExtra (AppConfigTags.PROPERTY_BEDROOMS, property.getBedroom ());
                intent.putExtra (AppConfigTags.PROPERTY_PRICE, property.getPrice ());
                intent.putExtra (AppConfigTags.PROPERTY_STATUS, property.getStatus ());
                intent.putExtra (AppConfigTags.PROPERTY_BUILT_YEAR, property.getYear_built ());
                intent.putExtra (AppConfigTags.PROPERTY_IMAGES, property.getImageList ());
                intent.putExtra (AppConfigTags.PROPERTY_IS_FAVOURITE, property.is_favourite ());
                

                startActivity (intent);
                overridePendingTransition (R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }
    
    private void getAllProperty () {
        if (NetworkConnection.isNetworkAvailable (this)) {
            Utils.showProgressDialog (progressDialog, getResources ().getString (R.string.progress_dialog_text_please_wait), true);
            Utils.showLog (Log.INFO, "" + AppConfigTags.URL, AppConfigURL.URL_PROPERTY_MAP, true);
            StringRequest strRequest1 = new StringRequest (Request.Method.POST, AppConfigURL.URL_PROPERTY_MAP,
                    new com.android.volley.Response.Listener<String> () {
                        @Override
                        public void onResponse (String response) {
                            
                            Utils.showLog (Log.INFO, AppConfigTags.SERVER_RESPONSE, response, true);
                            if (response != null) {
                                try {
                                    JSONObject jsonObj = new JSONObject (response);
                                    boolean error = jsonObj.getBoolean (AppConfigTags.ERROR);
                                    String message = jsonObj.getString (AppConfigTags.MESSAGE);
                                    if (! error) {
                                        JSONArray jsonArrayAllProperty = jsonObj.getJSONArray (AppConfigTags.PROPERTY_LOCATIONS);
                                        for (int i = 0; i < jsonArrayAllProperty.length (); i++) {
                                            JSONObject jsonObjectProperty = jsonArrayAllProperty.getJSONObject (i);
                                            Property property = new Property (
                                                    jsonObjectProperty.getInt (AppConfigTags.PROPERTY_ID),
                                                    jsonObjectProperty.getInt (AppConfigTags.PROPERTY_STATUS),
                                                    jsonObjectProperty.getString (AppConfigTags.PROPERTY_PRICE),
                                                    jsonObjectProperty.getString (AppConfigTags.PROPERTY_BEDROOMS),
                                                    jsonObjectProperty.getString (AppConfigTags.PROPERTY_BATHROOMS),
                                                    jsonObjectProperty.getString (AppConfigTags.PROPERTY_AREA),
                                                    jsonObjectProperty.getString (AppConfigTags.PROPERTY_BUILT_YEAR),
                                                    jsonObjectProperty.getString (AppConfigTags.PROPERTY_ADDRESS),
                                                    jsonObjectProperty.getString (AppConfigTags.PROPERTY_CITY),
                                                    false,
                                                    jsonObjectProperty.getBoolean (AppConfigTags.PROPERTY_IS_FAVOURITE));
    
                                            JSONArray jsonArrayPropertyImages = jsonObjectProperty.getJSONArray (AppConfigTags.PROPERTY_IMAGES);
                                            ArrayList<String> propertyImages = new ArrayList<> ();
    
                                            for (int j = 0; j < jsonArrayPropertyImages.length (); j++) {
                                                JSONObject jsonObjectImages = jsonArrayPropertyImages.getJSONObject (j);
                                                propertyImages.add (jsonObjectImages.getString (AppConfigTags.PROPERTY_IMAGE));
                                            }
                                            property.setImageList (propertyImages);
    
                                            mMap.addMarker (
                                                    new MarkerOptions ().position (new LatLng (jsonObjectProperty.getDouble (AppConfigTags.PROPERTY_LATITUDE), jsonObjectProperty.getDouble (AppConfigTags.PROPERTY_LONGITUDE)))
                                                            .title (jsonObjectProperty.getString (AppConfigTags.PROPERTY_ADDRESS))
                                                            .draggable (false)
                                                            .icon (BitmapDescriptorFactory.fromResource (R.drawable.ic_map_marker))
                                            ).setTag (property);
                                            mMap.setOnMarkerClickListener (AllPropertyLocationActivity.this);
                                            mMap.animateCamera (CameraUpdateFactory.newLatLngZoom (new LatLng (jsonObjectProperty.getDouble (AppConfigTags.PROPERTY_LATITUDE), jsonObjectProperty.getDouble (AppConfigTags.PROPERTY_LONGITUDE)), 8.0f));
                                        }
                                        progressDialog.dismiss ();
                                    } else {
                                        Utils.showSnackBar (AllPropertyLocationActivity.this, clMain, message, Snackbar.LENGTH_LONG, null, null);
                                        progressDialog.dismiss ();
                                    }
                                } catch (Exception e) {
                                    Utils.showSnackBar (AllPropertyLocationActivity.this, clMain, getResources ().getString (R.string.snackbar_text_exception_occurred), Snackbar.LENGTH_LONG, getResources ().getString (R.string.snackbar_action_dismiss), null);
                                    progressDialog.dismiss ();
                                    e.printStackTrace ();
                                }
                            } else {
                                Utils.showSnackBar (AllPropertyLocationActivity.this, clMain, getResources ().getString (R.string.snackbar_text_error_occurred), Snackbar.LENGTH_LONG, getResources ().getString (R.string.snackbar_action_dismiss), null);
                                Utils.showLog (Log.WARN, AppConfigTags.SERVER_RESPONSE, AppConfigTags.DIDNT_RECEIVE_ANY_DATA_FROM_SERVER, true);
                                progressDialog.dismiss ();
                            }
                        }
                    },
                    new com.android.volley.Response.ErrorListener () {
                        @Override
                        public void onErrorResponse (VolleyError error) {
                            progressDialog.dismiss ();
                            Utils.showLog (Log.ERROR, AppConfigTags.VOLLEY_ERROR, error.toString (), true);
                            Utils.showSnackBar (AllPropertyLocationActivity.this, clMain, getResources ().getString (R.string.snackbar_text_error_occurred), Snackbar.LENGTH_LONG, getResources ().getString (R.string.snackbar_action_dismiss), null);
                        }
                    }) {
                @Override
                protected Map<String, String> getParams () throws AuthFailureError {
                    Map<String, String> params = new Hashtable<String, String> ();
                    FilterDetailsPref filterDetailsPref = FilterDetailsPref.getInstance ();
                    params.put (AppConfigTags.TYPE, AppConfigTags.PROPERTY_LOCATIONS);
                    params.put (AppConfigTags.BUYER_ID, String.valueOf (buyerDetailsPref.getIntPref (AllPropertyLocationActivity.this, BuyerDetailsPref.BUYER_ID)));
                    params.put (AppConfigTags.FILTER, String.valueOf (filterDetailsPref.getBooleanPref (AllPropertyLocationActivity.this, FilterDetailsPref.FILTER_APPLIED)));
                    if (filterDetailsPref.getBooleanPref (AllPropertyLocationActivity.this, FilterDetailsPref.FILTER_APPLIED)) {
                        params.put (AppConfigTags.FILTER_BEDROOMS, filterDetailsPref.getStringPref (AllPropertyLocationActivity.this, FilterDetailsPref.FILTER_BEDROOMS));
                        params.put (AppConfigTags.FILTER_BATHROOMS, filterDetailsPref.getStringPref (AllPropertyLocationActivity.this, FilterDetailsPref.FILTER_BATHROOMS));
                        params.put (AppConfigTags.FILTER_STATUS, filterDetailsPref.getStringPref (AllPropertyLocationActivity.this, FilterDetailsPref.FILTER_STATUS));
                        params.put (AppConfigTags.FILTER_CITIES, filterDetailsPref.getStringPref (AllPropertyLocationActivity.this, FilterDetailsPref.FILTER_CITIES));
                        params.put (AppConfigTags.FILTER_PRICE_MIN, filterDetailsPref.getStringPref (AllPropertyLocationActivity.this, FilterDetailsPref.FILTER_PRICE_MIN));
                        params.put (AppConfigTags.FILTER_PRICE_MAX, filterDetailsPref.getStringPref (AllPropertyLocationActivity.this, FilterDetailsPref.FILTER_PRICE_MAX));
                        params.put (AppConfigTags.FILTER_LOCATION, filterDetailsPref.getStringPref (AllPropertyLocationActivity.this, FilterDetailsPref.FILTER_LOCATION));
                    }
                    Utils.showLog (Log.INFO, AppConfigTags.PARAMETERS_SENT_TO_THE_SERVER, "" + params, true);
                    return params;
                }
                
                @Override
                public Map<String, String> getHeaders () throws AuthFailureError {
                    Map<String, String> params = new HashMap<> ();
                    params.put (AppConfigTags.HEADER_API_KEY, Constants.api_key);
                    Utils.showLog (Log.INFO, AppConfigTags.HEADERS_SENT_TO_THE_SERVER, "" + params, false);
                    return params;
                }
            };
            Utils.sendRequest (strRequest1, 60);
        } else {
            progressDialog.dismiss ();
    
            Utils.showSnackBar (this, clMain, getResources ().getString (R.string.snackbar_text_no_internet_connection_available), Snackbar.LENGTH_LONG, getResources ().getString (R.string.snackbar_action_go_to_settings), new View.OnClickListener () {
                @Override
                public void onClick (View v) {
                    Intent dialogIntent = new Intent (Settings.ACTION_SETTINGS);
                    dialogIntent.addFlags (Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity (dialogIntent);
                }
            });
        }
    }
}