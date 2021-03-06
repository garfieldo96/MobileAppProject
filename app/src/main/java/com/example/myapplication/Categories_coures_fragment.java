package com.example.myapplication;

import android.content.SharedPreferences;
import android.location.Address;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textview.MaterialTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Categories_coures_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Categories_coures_fragment extends Fragment  implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    private MaterialToolbar toolbar;
    private ActionBar actionBar;
    private MaterialTextView toolbartext;
    private MaterialTextView PhoneCall;
    private Button Confirm_Button;

    private GoogleMap GMap;

    //private ArrayList<Recommanded_course_item> RecommandedCourseDataList = new ArrayList<>();
    private RecycleAdaptors_categories_Curse_rank recycleAdaptors;
    private RecyclerView Recommanded_recycler_view;
    private TextView Phone_call;

    private LinearLayoutManager layoutManager;

    private Double start_latitude;
    private Double start_longitude;
    private String target_phone;
    private String data_array;
    private JSONArray Course_total_array;
    private JSONArray Course_array;
    private String Starting_latitude;
    private String Starting_longitude;
    String Email;
    private SharedPreferences login_information_pref;

    private String ip;
    private String input;

    private ArrayList<course_item> course_list = new ArrayList<course_item>();

    private MapView mapView;
    private List<Address> list = null;
    public Categories_coures_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Categories_coures_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Categories_coures_fragment newInstance(String param1, String param2) {
        Categories_coures_fragment fragment = new Categories_coures_fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_categories_coures_fragment, container, false);
        ip = getString(R.string.server_ip);

//        Utils.setStatusBarColor(getActivity(), Utils.StatusBarcolorType.BLACK_STATUS_BAR);
//
//        toolbar = (MaterialToolbar)findViewById(R.id.MainActiviy_toolbar);
//        setSupportActionBar(toolbar);
//        actionBar = getSupportActionBar();
//        actionBar.setDisplayShowCustomEnabled(true);
//        actionBar.setDisplayShowTitleEnabled(false);
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        toolbartext = (MaterialTextView)findViewById(R.id.toolbar_textview);

//        login_information_pref = getSharedPreferences("login_information", Context.MODE_PRIVATE);
//        Email = login_information_pref.getString("email", Email);
//        //String Address = login_information_pref.getString("address", "주소를 선택해주세요.");
//        toolbartext.setText("원하는 장소를 선택해주세요");
        Recommanded_recycler_view = v.findViewById(R.id.recommended_courses_Recycler_view);
        layoutManager =  new LinearLayoutManager(getContext());
        if(layoutManager != null){
            Recommanded_recycler_view.setLayoutManager(layoutManager);
        }
        else{
            Log.e("SensorFragment", "Error");
        }

//        ThreadTask<Object> result = getThreadTask(Email, "/id_duplication_check");
//        result.execute(ip);

//        String input ="{"+
//                "data_array:" + "[{\"Course_Name\" : \"추천코스1\", \"Course_id\" : \"0\", \"Course\" : [{\"Place_name\" : \"place1\", \"latitude\" : \"35.88565632201131\", \"longitude\" : \"128.6119264468393\"},{\"Place_name\" : \"place2\", \"latitude\" : \"35.88492137332584\", \"longitude\" : \"128.60971990159663\"},{\"Place_name\" : \"place3\", \"latitude\" : \"35.88253371936135\", \"longitude\" : \"128.60998957938452\"}]},{\"Course_Name\" : \"추천코스2\", \"Course_id\" : \"1\", \"Course\" : [{\"Place_name\" : \"place4\", \"latitude\" : \"35.884365190327685\", \"longitude\" : \"128.60770459025358\"},{\"Place_name\" : \"place5\", \"latitude\" : \"35.88213627620797\", \"longitude\" : \"128.60587875777216\"},{\"Place_name\" : \"place6\", \"latitude\" : \"35.884528069549695\", \"longitude\" : \"128.6060717434991\"} ]}]"
//                +"}";

        String input ="{"+
                "Course:"  +"[{\"Place_name\" : \"place1\",\"Place_detail\" : \"place detail1\", \"latitude\" : \"35.88565632201131\", \"longitude\" : \"128.6119264468393\"},{\"Place_name\" : \"place2\",\"Place_detail\" : \"place detail2\", \"latitude\" : \"35.88492137332584\", \"longitude\" : \"128.60971990159663\"},{\"Place_name\" : \"place3\",\"Place_detail\" : \"place detail3\", \"latitude\" : \"35.88253371936135\", \"longitude\" : \"128.60998957938452\"}]"
                +"}";

        JSONObject input_object = null;
        try {
            input_object = new JSONObject(input);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Intent intent = getIntent();
        try {
//            start_latitude = Double.parseDouble(input_object.getString("latitude"));
//            start_longitude = Double.parseDouble(input_object.getString("longitude"));
//            Log.e("NearHospital", Double.toString(start_latitude));
//            Log.e("NearHospital", Double.toString(start_longitude));
            data_array = input_object.getString("Course");
            Course_total_array = null;
            Course_total_array = new JSONArray(data_array);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            for(int j = 0 ; j < Course_total_array.length() ; j ++){
                JSONObject temp_object = Course_total_array.getJSONObject(j);
                String Place_name = temp_object.getString("Place_name");
                String Place_detail = temp_object.getString("Place_detail");
                String Latitude = temp_object.getString("latitude");
                String Longitude = temp_object.getString("longitude");

//                final Geocoder geocoder = new Geocoder(getContext());
//                try {
//                    list = geocoder.getFromLocation(Double.parseDouble(Latitude),Double.parseDouble(Longitude), 10);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                Address addr = list.get(0);
//                String Place_detail = addr.getAddressLine(0);
                if(j == 0){
                    Starting_latitude = Latitude;
                    Starting_longitude = Longitude;
                }

//                GpsToAddress gps = new GpsToAddress(Double.parseDouble(Latitude), Double.parseDouble(Longitude));
//                String Place_detail = gps.getAddress();
//                //String Place_detail = " ";
                course_list.add(new course_item(Place_name, Place_detail, Latitude, Longitude));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        mapView = (MapView)v.findViewById(R.id.map);
        // 맵이 실행되면 onMapReady 실행
        mapView.getMapAsync(this);


        Confirm_Button = v.findViewById(R.id.recommended_courses_confirm);
        Confirm_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = recycleAdaptors.getSelectedPosition();
                if(position != -1){
                    course_item items = recycleAdaptors.getItem(position);
                    Bundle args = new Bundle();
                    args.putString("destination", items.getPlace_name());
                    args.putString("address", items.getAddress());
                    args.putString("latitude", items.getLatitude());
                    args.putString("longitude", items.getLongitude());
                    args.putString("index", mParam1);


                    ((MainActivity)getActivity()).onMoveCustomcourse(args);
                }
                else{
                    Toast.makeText(getContext(), "목적지를 선택해주세요.", Toast.LENGTH_SHORT).show();
                }
                /**
                 * 목적지 이름
                 * 주소
                 * 위도
                 * 경도 전달
                 * */
            }
        });
        return v;


    }


    public com.google.android.gms.maps.GoogleMap getGoogleMap() {
        return GMap;
    }

    public void setGoogleMap(com.google.android.gms.maps.GoogleMap googleMap) {
        GMap = googleMap;
    }
    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onLowMemory();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //액티비티가 처음 생성될 때 실행되는 함수
        if(mapView != null)
        {
            mapView.onCreate(savedInstanceState);
        }
    }
    @Override
    public void onMapReady(final GoogleMap googleMap) {

        setGoogleMap(googleMap);
        GMap.setOnMarkerClickListener(this);
        // 구글에서 등록한 api와 엮어주기
        // 시작위치를 서울 시청으로 변경
        /**
         * 센서 위치 시작 지점 받아오기
         * */
        LatLng Starting_Point = new LatLng(Double.parseDouble(Starting_latitude), Double.parseDouble(Starting_longitude)); // 스타팅 지점
        getGoogleMap().moveCamera(CameraUpdateFactory.newLatLngZoom(Starting_Point, 14));

        //GMap.animateCamera(CameraUpdateFactory.zoomTo(14));// 키우면 더 확대

        // 시작시 마커 생성하기 누르면 title과 snippet이 뜬다.
        MarkerOptions markerOptions = new MarkerOptions();
        double Latitude;
        double Longtitude;

        Marker marker = null;
//        for(int i = 0 ; i < HospitalDataList.size() ; i++){
////            Latitude = Double.parseDouble(HospitalDataList.get(i).getLatitude());
////            Longtitude = Double.parseDouble(HospitalDataList.get(i).getLongtitude());
////
//////            markerOptions.position(new LatLng(Latitude,Longtitude));
//////            markerOptions.title(HospitalDataList.get(i).getHospital_name());
////            //markerOptions.snippet(Hos);
////            // 생성된 마커 옵션을 지도에 표시
////
////            //marker = GMap.addMarker(markerOptions);
////            //marker.showInfoWindow();
////            marker = GMap.addMarker(new MarkerOptions()
////                    .position(new LatLng(Latitude,Longtitude))
////                    .title(HospitalDataList.get(i).getHospital_name() ));
////        }
////        marker.showInfoWindow();
        // 서울광장마커

        // 회사 DB에 데이터를 가지고 있어야 된다.
//        LatLng plaza = new LatLng(37.565785, 126.978056);
//        markerOptions.position(plaza);
//        markerOptions.title("광장");
//        markerOptions.snippet("서울 광장");
//        GMap.addMarker(markerOptions);

        //맵 로드 된 이후
        GMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                //Toast.makeText(NearHospital.this, "Map로딩성공", Toast.LENGTH_SHORT).show();
            }
        });

        //카메라 이동 시작
        GMap.setOnCameraMoveStartedListener(new GoogleMap.OnCameraMoveStartedListener() {
            @Override
            public void onCameraMoveStarted(int i) {
                //Log.d("set>>","start");
            }
        });

        // 카메라 이동 중
        GMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {
                //Log.d("set>>","move");
            }
        });

        // 지도를 클릭하면 호출되는 이벤트
        GMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                // 기존 마커 정리
                //googleMap.clear();
                // 클릭한 위치로 지도 이동하기
                GMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                // 신규 마커 추가
//                MarkerOptions newMarker=new MarkerOptions();
//                newMarker.position(latLng);
//                googleMap.addMarker(newMarker);
            }
        });

        recycleAdaptors = new RecycleAdaptors_categories_Curse_rank(ip, getGoogleMap());
        recycleAdaptors.setItems(course_list);
        Recommanded_recycler_view.setAdapter(recycleAdaptors);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                getActivity().finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.showInfoWindow();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //System.out.println(layoutManager.canScrollVertically());
//                int position = 0;
//                for( Hospital hospital : HospitalDataList ){
//                    if(hospital.getHospital_name().equals(marker.getTitle())){
//                        System.out.println(hospital.getHospital_name());
//                        Hospital_recycler_view.smoothScrollToPosition(position);
//                    }
//                    position++;
//                }
            }
        }, 200);
        return false;
    }
}