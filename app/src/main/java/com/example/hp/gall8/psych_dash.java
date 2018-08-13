package com.example.hp.gall8;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.goodiebag.horizontalpicker.HorizontalPicker;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import at.grabner.circleprogress.CircleProgressView;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;


/**
 * A simple {@link Fragment} subclass.
 */
public class psych_dash extends Fragment {
    float avg = 0;
    private int numberOfLines = 1;
    private int maxNumberOfLines = 4;
    private int numberOfPoints = 12;
    private LineChartData data;
    float[][] randomNumbersTab = new float[maxNumberOfLines][numberOfPoints];
    private boolean hasAxes = true;
    private boolean hasAxesNames = true;
    private boolean hasLines = true;
    private boolean hasPoints = true;
    private ValueShape shape = ValueShape.CIRCLE;
    private boolean isFilled = true;
    private boolean hasLabels = false;
    private boolean isCubic = true;
    private boolean hasLabelForSelected = false;
    private boolean pointsHaveDifferentColor;
    private boolean hasGradientToTransparent = false;
    private BarChart barChart;
    private LineChart lineChart;
    private LineChartView chart, chart1;
    List<PointValue> values = new ArrayList<PointValue>();
    List<Line> lines = new ArrayList<Line>();
    HorizontalPicker hpText;
    DatabaseReference mDatabase;
    String email, pyschEmail;
    SessionManagement session;
    float count = 0;
    CircleProgressView c1, c2, c3, c4;
    public psych_dash() {

        // Required empty public constructor
    }


    // @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dash, container, false);
        //barChart = (BarChart) view.findViewById(R.id.barChart1);

        chart1 = (LineChartView)  view.findViewById(R.id.lc2);

        hpText = (HorizontalPicker) view.findViewById(R.id.hpicker);
        session = new SessionManagement(getContext());
        mDatabase = FirebaseDatabase.getInstance().getReference("data");
        c1 = (CircleProgressView) view.findViewById(R.id.cpv);
        c2 = (CircleProgressView) view.findViewById(R.id.cpv1);
        c3 = (CircleProgressView) view.findViewById(R.id.cpv2);
        c4 = (CircleProgressView) view.findViewById(R.id.cpv3);
        HashMap<String, String> user = session.getUserDetails();

        email = user.get(SessionManagement.KEY_EMAIL);
        List<HorizontalPicker.PickerItem> textItems = new ArrayList<>();

        textItems.add(new HorizontalPicker.TextItem("Daily"));
        textItems.add(new HorizontalPicker.TextItem("Weekly"));
        textItems.add(new HorizontalPicker.TextItem("Monthly"));

        hpText.setItems(textItems,3); //3 here signifies the default selected item. Use : hpText.setItems(textItems) if none of the items are selected by default.
        hpText.setSelectedIndex(0);

        readData3(new MyCallback2() {
            @Override
            public void onCallback(String value) {
                pyschEmail = value;




        mDatabase.child(pyschEmail.replaceAll("[-+.^:,@]", "")).child("Stress Logs").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                final Double firstValue = (Double) map.get("stresslevel");
                String secondValue = (String) map.get("time1");
                String thirdValue = (String) map.get("mood");
                if (getActivity()!=null) {
                    avg = avg + firstValue.floatValue();

                }
                readData1(new MyCallback() {
                    @Override
                    public void onCallback(float value) {
                        c1.stopSpinning(); // stops spinning. Spinner gets shorter until it disappears.
                        c1.setValueAnimated((avg/value)); // stops spinning. Spinner spins until on top. Then fills to set value.
                        c2.stopSpinning(); // stops spinning. Spinner gets shorter until it disappears.
                        c2.setValueAnimated(firstValue.floatValue()); // stops spinning. Spinner spins until on top. Then fills to set value.
                    }
                });

                readData2(new MyCallback1() {
                    @Override
                    public void onCallback(float value) {
                        c3.stopSpinning(); // stops spinning. Spinner gets shorter until it disappears.
                        c3.setValueAnimated(firstValue.floatValue()); // stops spinning. Spinner spins until on top. Then fills to set value.
                    }
                });

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        HorizontalPicker.OnSelectionChangeListener listener = new HorizontalPicker.OnSelectionChangeListener() {
            @Override
            public void onItemSelect(HorizontalPicker picker, int index) {
                HorizontalPicker.PickerItem selected = picker.getSelectedItem();
                Toast.makeText(getContext(), selected.hasDrawable() ? "Item at " + (picker.getSelectedIndex() + 1) + " is selected" : selected.getText() + " is selected", Toast.LENGTH_SHORT).show();

                if (selected.getText().equals("Daily")) {

                    mDatabase.child(pyschEmail.replaceAll("[-+.^:,@]", "")).child("Stress Logs").addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                            Double firstValue = (Double) map.get("stresslevel");
                            String secondValue = (String) map.get("time1");
                            String thirdValue = (String) map.get("mood");
                            float val = 0;


                            if (getActivity() != null) {

                                val = firstValue.floatValue();
                                Toast.makeText(getContext(), String.valueOf(val), Toast.LENGTH_SHORT).show();
                                values.add(new PointValue(1, val));

                                Line line = new Line(values);
                                line.setColor(ChartUtils.COLORS[0]);
                                line.setShape(shape);
                                line.setCubic(isCubic);
                                line.setFilled(isFilled);
                                line.setHasLabels(hasLabels);
                                line.setHasLabelsOnlyForSelected(hasLabelForSelected);
                                line.setHasLines(hasLines);
                                line.setHasPoints(hasPoints);

                                if (pointsHaveDifferentColor) {
                                    line.setPointColor(ChartUtils.COLORS[(0 + 1) % ChartUtils.COLORS.length]);
                                }
                                lines.add(line);


                                data = new LineChartData(lines);
                                if (hasAxes) {
                                    Axis axisX = new Axis();
                                    Axis axisY = new Axis().setHasLines(true);
                                    if (hasAxesNames) {
                                        axisX.setName("Dates");
                                        axisY.setName("Stress Levels");
                                        axisX.setTextColor(ChartUtils.COLORS[0]);
                                        axisY.setTextColor(ChartUtils.COLORS[0]);
                                        axisX.setLineColor(ChartUtils.COLORS[0]);
                                        axisY.setLineColor(ChartUtils.COLORS[0]);
                                    }
                                    data.setAxisXBottom(axisX);
                                    data.setAxisYLeft(axisY);
                                } else {
                                    data.setAxisXBottom(null);
                                    data.setAxisYLeft(null);
                                }



                                data.setBaseValue(Float.NEGATIVE_INFINITY);
                                chart1.setLineChartData(data);

                            }







                        }



                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });



                    //MONTHLY

                } else if (selected.getText().equals("Weekly")) {
                    for (int i = 0; i < maxNumberOfLines; ++i) {
                        for (int j = 0; j < numberOfPoints; ++j) {
                            randomNumbersTab[i][j] = (float) Math.random() * 100f;
                        }
                    }



                    List<Line> lines = new ArrayList<Line>();
                    for (int i = 0; i < numberOfLines; ++i) {

                        List<PointValue> values = new ArrayList<PointValue>();
                        for (int j = 0; j < numberOfPoints; ++j) {
                            values.add(new PointValue(j, randomNumbersTab[i][j]));
                        }

                        Line line = new Line(values);
                        line.setColor(ChartUtils.COLORS[i]);
                        line.setShape(shape);
                        line.setCubic(isCubic);
                        line.setFilled(isFilled);
                        line.setHasLabels(hasLabels);
                        line.setHasLabelsOnlyForSelected(hasLabelForSelected);
                        line.setHasLines(hasLines);
                        line.setHasPoints(hasPoints);

                        if (pointsHaveDifferentColor){
                            line.setPointColor(ChartUtils.COLORS[(i + 1) % ChartUtils.COLORS.length]);
                        }
                        lines.add(line);
                    }

                    data = new LineChartData(lines);

                    if (hasAxes) {
                        Axis axisX = new Axis();
                        Axis axisY = new Axis().setHasLines(true);
                        if (hasAxesNames) {
                            axisX.setName("Dates");
                            axisY.setName("Stress Levels");
                            axisX.setTextColor(ChartUtils.COLORS[0]);
                            axisY.setTextColor(ChartUtils.COLORS[0]);
                            axisX.setLineColor(ChartUtils.COLORS[0]);
                            axisY.setLineColor(ChartUtils.COLORS[0]);
                        }
                        data.setAxisXBottom(axisX);
                        data.setAxisYLeft(axisY);
                    } else {
                        data.setAxisXBottom(null);
                        data.setAxisYLeft(null);
                    }

                    data.setBaseValue(Float.NEGATIVE_INFINITY);
                    chart1.setLineChartData(data);

                } else if (selected.getText().equals("Monthly")){
                    for (int i = 0; i < maxNumberOfLines; ++i) {
                        for (int j = 0; j < numberOfPoints; ++j) {
                            randomNumbersTab[i][j] = (float) Math.random() * 100f;
                        }
                    }



                    List<Line> lines = new ArrayList<Line>();
                    for (int i = 0; i < numberOfLines; ++i) {

                        List<PointValue> values = new ArrayList<PointValue>();
                        for (int j = 0; j < numberOfPoints; ++j) {
                            values.add(new PointValue(j, randomNumbersTab[i][j]));
                        }

                        Line line = new Line(values);
                        line.setColor(ChartUtils.COLORS[i]);
                        line.setShape(shape);
                        line.setCubic(isCubic);
                        line.setFilled(isFilled);
                        line.setHasLabels(hasLabels);
                        line.setHasLabelsOnlyForSelected(hasLabelForSelected);
                        line.setHasLines(hasLines);
                        line.setHasPoints(hasPoints);

                        if (pointsHaveDifferentColor){
                            line.setPointColor(ChartUtils.COLORS[(i + 1) % ChartUtils.COLORS.length]);
                        }
                        lines.add(line);
                    }

                    data = new LineChartData(lines);

                    if (hasAxes) {
                        Axis axisX = new Axis();
                        Axis axisY = new Axis().setHasLines(true);
                        if (hasAxesNames) {
                            axisX.setName("Dates");
                            axisY.setName("Stress Levels");
                            axisX.setTextColor(ChartUtils.COLORS[0]);
                            axisY.setTextColor(ChartUtils.COLORS[0]);
                            axisX.setLineColor(ChartUtils.COLORS[0]);
                            axisY.setLineColor(ChartUtils.COLORS[0]);
                        }
                        data.setAxisXBottom(axisX);
                        data.setAxisYLeft(axisY);
                    } else {
                        data.setAxisXBottom(null);
                        data.setAxisYLeft(null);
                    }

                    data.setBaseValue(Float.NEGATIVE_INFINITY);
                    chart1.setLineChartData(data);

                }

            }

        };

        hpText.setChangeListener(listener);



        // LineGraph 1
        for (int i = 0; i < maxNumberOfLines; ++i) {
            for (int j = 0; j < numberOfPoints; ++j) {
                randomNumbersTab[i][j] = (float) Math.random() * 100f;
            }
        }



        List<Line> lines = new ArrayList<Line>();
        for (int i = 0; i < numberOfLines; ++i) {

            List<PointValue> values = new ArrayList<PointValue>();
            for (int j = 0; j < numberOfPoints; ++j) {
                values.add(new PointValue(j, randomNumbersTab[i][j]));
            }

            Line line = new Line(values);
            line.setColor(ChartUtils.COLORS[i]);
            line.setShape(shape);
            line.setCubic(isCubic);
            line.setFilled(isFilled);
            line.setHasLabels(hasLabels);
            line.setHasLabelsOnlyForSelected(hasLabelForSelected);
            line.setHasLines(hasLines);
            line.setHasPoints(hasPoints);

            if (pointsHaveDifferentColor){
                line.setPointColor(ChartUtils.COLORS[(i + 1) % ChartUtils.COLORS.length]);
            }
            lines.add(line);
        }

        data = new LineChartData(lines);

        if (hasAxes) {
            Axis axisX = new Axis();
            Axis axisY = new Axis().setHasLines(true);
            if (hasAxesNames) {
                axisX.setName("Dates");
                axisY.setName("Stress Levels");
                axisX.setTextColor(ChartUtils.COLORS[0]);
                axisY.setTextColor(ChartUtils.COLORS[0]);
                axisX.setLineColor(ChartUtils.COLORS[0]);
                axisY.setLineColor(ChartUtils.COLORS[0]);
            }
            data.setAxisXBottom(axisX);
            data.setAxisYLeft(axisY);
        } else {
            data.setAxisXBottom(null);
            data.setAxisYLeft(null);
        }

        data.setBaseValue(Float.NEGATIVE_INFINITY);
        chart1.setLineChartData(data);


            }
        });





        return view;


    }

    public void readData1(final MyCallback myCallback){

        mDatabase.child(pyschEmail.replaceAll("[-+.^:,@]", "") + "/Stress Logs").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // get total available quest
                float count1 = dataSnapshot.getChildrenCount();

                myCallback.onCallback(count1);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void readData2(final MyCallback1 myCallback1){
        Query lastQuery =  mDatabase.child(pyschEmail.replaceAll("[-+.^:,@]", "") + "/Stress Logs").orderByChild("stresslevel").limitToLast(1);
        lastQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                    String Key = childSnapshot.getKey();

                    mDatabase.child(pyschEmail.replaceAll("[-+.^:,@]", "") + "/Stress Logs").child(Key).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                            Double firstValue = (Double) map.get("stresslevel");

                            myCallback1.onCallback(firstValue.floatValue());
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException(); // don't swallow errors
            }
        });
    }


    public void readData3(final MyCallback2 myCallback2){
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("path");
        dbRef.child(email.replaceAll("[-+.^:,@]", "")).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                String firstValue = (String) map.get("psychUser");

                myCallback2.onCallback(firstValue);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



    public interface MyCallback {
        void onCallback(float value);
    }
    public interface MyCallback1 {
        void onCallback(float value);
    }
    public interface MyCallback2 {
        void onCallback(String value);
    }
}
