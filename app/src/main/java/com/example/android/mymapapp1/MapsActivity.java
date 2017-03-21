package com.example.android.mymapapp1;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
        import android.os.Bundle;
        import android.util.JsonReader;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
        import com.google.android.gms.maps.GoogleMap;
        import com.google.android.gms.maps.OnMapReadyCallback;
        import com.google.android.gms.maps.SupportMapFragment;
        import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
        import com.josie.busrouted.models.BusRoute;
        import com.josie.busrouted.models.Stop;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.util.ArrayList;
        import java.util.List;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = "MapsActivity";

    private BusRoute mBusRoute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // First, load the JSON
        try {
            InputStream in = getAssets().open("line2.json");
            mBusRoute = readJsonStream(in);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Now we have the data, load the map
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private BusRoute readJsonStream(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            return readRoute(reader);
        } finally {
            reader.close();
        }
    }

    private BusRoute readRoute(JsonReader reader) throws IOException {
        String lineName = null;
        List<Stop> stops = new ArrayList<>();
        reader.beginObject();
        while(reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("lineName")) {
                lineName = reader.nextString();
            } else if (name.equals("stopPointSequences")) {
                stops = readStopSequence(reader);
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new BusRoute(lineName, stops);
    }

    public List<Stop> readStopSequence(JsonReader reader) throws IOException {
        List<Stop> stops = new ArrayList<>();

        reader.beginArray();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("stopPoint")) {
                stops = readStopPoints(reader);
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        reader.endArray();
        return stops;
    }

    public List<Stop> readStopPoints(JsonReader reader) throws IOException {
        List<Stop> stations = new ArrayList<>();

        reader.beginArray();
        while (reader.hasNext()) {
            stations.add(readStop(reader));
        }
        reader.endArray();
        return stations;
    }

    public Stop readStop(JsonReader reader) throws IOException {
        String stationName = null;
        double lat = -1;
        double lon = -1;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("name")) {
                stationName = reader.nextString();
            } else if (name.equals("lat")) {
                lat = reader.nextDouble();
            } else if (name.equals("lon")) {
                lon = reader.nextDouble();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new Stop(stationName, new LatLng(lat, lon));
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {


            Context context = getApplicationContext();
            CharSequence text = "Have fun exploring!";
            int duration = Toast.LENGTH_LONG;

        // Draw route line
        googleMap.addPolyline(new PolylineOptions().addAll(mBusRoute.getStopLatLongs()));
        googleMap.addMarker(new MarkerOptions().position((mBusRoute.getFirstStop().getLatLong())).title("Line2").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

        //this is basically what's happening to the first line as well, just done by hand
        LatLng Line1End = new LatLng(51.498199, -0.049857);
        googleMap.addMarker(new MarkerOptions().position(Line1End).title("Line1").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

        final Polyline line1 = googleMap.addPolyline(new PolylineOptions()
                .add(new LatLng(51.516780, -0.127804), new LatLng(51.516821, -0.127557), new LatLng(51.517078, -0.125601), new LatLng(51.51902, -0.120966), new LatLng(51.517298, -0.120245), new LatLng(51.513369, -0.117481), new LatLng(51.513266, -0.117226), new LatLng(51.513303, -0.116215), new LatLng(51.513242, -0.115756), new LatLng(51.512949, -0.114846), new LatLng(51.512823, -0.114851), new LatLng(51.512037, -0.116887), new LatLng(51.511758, -0.117951), new LatLng(51.511307, -0.119036), new LatLng(51.510307, -0.118313), new LatLng(51.505188, -0.113711), new LatLng(51.505036, -0.113228), new LatLng(51.504475, -0.113035), new LatLng(51.504259, -0.112424), new LatLng(51.502056, -0.109648), new LatLng(51.501874, -0.109540), new LatLng(51.498885, -0.105975), new LatLng(51.498736, -0.105621), new LatLng(51.498710, -0.105176), new LatLng(51.498928, -0.104763), new LatLng(51.498825, -0.104465), new LatLng(51.498328, -0.104327), new LatLng(51.495610, -0.100853), new LatLng(51.495607, -0.100161), new LatLng(51.495515, -0.100007), new LatLng(51.495262, -0.099902), new LatLng(51.495094, -0.099534), new LatLng(51.494146, -0.094301), new LatLng(51.494327, -0.086644), new LatLng(51.494448, -0.086336), new LatLng(51.494716, -0.086239), new LatLng(51.494874, -0.085987), new LatLng(51.494771, -0.084680), new LatLng(51.495349, -0.083734), new LatLng(51.496371, -0.082438), new LatLng(51.496712, -0.081818), new LatLng(51.495817, -0.078787), new LatLng(51.494996, -0.076445), new LatLng(51.494143, -0.075429), new LatLng(51.493518, -0.074043), new LatLng(51.492967, -0.073389), new LatLng(51.492733, -0.072794), new LatLng(51.492660, -0.072207), new LatLng(51.492746, -0.071382), new LatLng(51.492751, -0.069524), new LatLng(51.492389, -0.067219), new LatLng(51.492165, -0.063988), new LatLng(51.492261, -0.062730), new LatLng(51.492151, -0.060949), new LatLng(51.490369, -0.059814), new LatLng(51.489783, -0.059176), new LatLng(51.488751, -0.057636), new LatLng(51.490282, -0.055108), new LatLng(51.490721, -0.053922), new LatLng(51.491220, -0.051970), new LatLng(51.491353, -0.051864), new LatLng(51.491356, -0.051504), new LatLng(51.491192, -0.051381), new LatLng(51.491052, -0.051099), new LatLng(51.491067, -0.049831), new LatLng(51.491475, -0.047321), new LatLng(51.491642, -0.047098), new LatLng(51.492320, -0.047314), new LatLng(51.493068, -0.047959), new LatLng(51.493394, -0.048103), new LatLng(51.493887, -0.048529), new LatLng(51.494596, -0.050083), new LatLng(51.496263, -0.052404), new LatLng(51.497195, -0.050606), new LatLng(51.497530, -0.049670), new LatLng(51.497707, -0.049504), new LatLng(51.498119, -0.049371), new LatLng(51.498161, -0.049758), new LatLng(51.498199, -0.049857))
                .width(8)
                .clickable(true)
                .color(Color.BLUE));

        LatLng Line39End = new LatLng(51.464513, -0.168110);
        googleMap.addMarker(new MarkerOptions().position(Line39End).title("Line39").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        final Polyline line39 = googleMap.addPolyline(new PolylineOptions()
                .add(new LatLng(51.468347, -0.209078), new LatLng(51.467746, -0.209144), new LatLng(51.467891, -0.210420), new LatLng(51.468356, -0.210287), new LatLng(51.468398, -0.211221), new LatLng(51.465692, -0.214264), new LatLng(51.464908, -0.214712), new LatLng(51.464206, -0.215272), new LatLng(51.461692, -0.216723), new LatLng(51.460137, -0.217374), new LatLng(51.459357, -0.217548), new LatLng(51.456886, -0.218868), new LatLng(51.454288, -0.220078), new LatLng(51.452704, -0.221204), new LatLng(51.451738, -0.221558), new LatLng(51.449116, -0.222941), new LatLng(51.448844, -0.222822), new LatLng(51.448719, -0.222309), new LatLng(51.448474, -0.222146), new LatLng(51.447939, -0.222454), new LatLng(51.447642, -0.222408), new LatLng(51.447378, -0.222203), new LatLng(51.447481, -0.221911), new LatLng(51.448653, -0.221520), new LatLng(51.448975, -0.220817), new LatLng(51.448953, -0.220544), new LatLng(51.448398, -0.218925), new LatLng(51.446826, -0.216684), new LatLng(51.445929, -0.215639), new LatLng(51.444265, -0.214423), new LatLng(51.443463, -0.217304), new LatLng(51.441927, -0.216788), new LatLng(51.440723, -0.216864), new LatLng(51.440284, -0.216391), new LatLng(51.439577, -0.214822), new LatLng(51.438831, -0.213714), new LatLng(51.437922, -0.213030), new LatLng(51.439640, -0.211323), new LatLng(51.441880, -0.209595), new LatLng(51.445298, -0.206065), new LatLng(51.449963, -0.202428), new LatLng(51.449824, -0.199829), new LatLng(51.451274, -0.198217), new LatLng(51.452978, -0.197301), new LatLng(51.453722, -0.197156), new LatLng(51.454436, -0.197373), new LatLng(51.454945, -0.197137), new LatLng(51.455387, -0.196659), new LatLng(51.455799, -0.195966), new LatLng(51.456040, -0.195280), new LatLng(51.457060, -0.194348), new LatLng(51.45716, -0.194415), new LatLng(51.457256, -0.194815), new LatLng(51.457834, -0.194994), new LatLng(51.458625, -0.194948), new LatLng(51.458891, -0.193541), new LatLng(51.458960, -0.192732), new LatLng(51.458855, -0.191772), new LatLng(51.459171, -0.190680), new LatLng(51.459106, -0.190524), new LatLng(51.458501, -0.190375), new LatLng(51.456952, -0.189659), new LatLng(51.456894, -0.189388), new LatLng(51.457655, -0.185140), new LatLng(51.458439, -0.183468), new LatLng(51.459149, -0.181684), new LatLng(51.459491, -0.180533), new LatLng(51.460792, -0.175140), new LatLng(51.462147, -0.172653), new LatLng(51.462659, -0.171496), new LatLng(51.463486, -0.168598), new LatLng(51.463789, -0.167808), new LatLng(51.463844, -0.167863), new LatLng(51.464069, -0.167898), new LatLng(51.464151, -0.167952), new LatLng(51.464278, -0.168004), new LatLng(51.464513, -0.168110))
                .width(8)
                .clickable(true)
                .color(Color.RED));
        line39.setTag("Line 39");

        // Move camera to correct position
        LatLng London = new LatLng(51.5152117, -0.130);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(London));
        googleMap.setMinZoomPreference(11.0f);


        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
    public void sendFeedback(View view) {

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "josefine.raab@stud.leuphana.de" });
        intent.putExtra(Intent.EXTRA_SUBJECT, "My opinion on the Bus Route App");
        intent.putExtra(Intent.EXTRA_TEXT, "Hey Josie, this is what I liked about your app:  \n \n \n \n This is what you can improve:\n ");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);

        }
    }
}