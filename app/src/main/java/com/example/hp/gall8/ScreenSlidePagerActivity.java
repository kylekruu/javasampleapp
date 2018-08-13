package com.example.hp.gall8;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class ScreenSlidePagerActivity extends FragmentActivity {
    ConstraintLayout layout;
    RadioGroup rgp;

    String pageData[];
    String ans[][] = new String[][]{
            new String[] {"Diabetes","High Blood"},
            new String[] {"Always","Often","Sometimes","Seldom","Never"},
            new String[] {"Always","Often","Sometimes","Seldom","Never"},
            new String[] {"1-3 cups","4-6 cups", "7 or more" ,"I don't drink coffee"},
            new String[] { "I don't have any vices" ,"I only drink" ,"I only smoke" ,"I drink and smoke"},
            new String[] {"I do not smoke","1-2 packs","3-5 packs","6 or more"},
            new String[] {"I do not drink","1-6 units a week","7-10 units a week","11 or more units"},
            new String[] {"1-3 hours","4-6 hours","7-9 hours","10 or more"},
            new String[] {"Always","Often","Sometimes","Seldom","Never"},
            new String[] {"Sleep","Drinking","Smoking","Spending time with friends","Pamper yourself"}};//Stores the text to swipe.
    String answers[] = new String[ans.length];
    LayoutInflater inflater;    //Used to create individual pages
    ViewPager vp;               //Reference to class to swipe views
    private static final int NUM_PAGES = 5;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen);

        // Instantiate a ViewPager and a PagerAdapter.
        layout = (ConstraintLayout) findViewById(R.id.content);
        rgp = (RadioGroup) findViewById(R.id.radiogroup);
        ConstraintLayout.LayoutParams p = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.FILL_PARENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );

        pageData=getResources().getStringArray(R.array.questions);


        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        vp = (ViewPager) findViewById(R.id.pager);
       // mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        vp.setAdapter(new MyPagesAdapter());
        vp.setPageTransformer(true, new ZoomOutPageTransformer());





    }




    public class ZoomOutPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.85f;
        private static final float MIN_ALPHA = 0.5f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();
            int pageHeight = view.getHeight();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 1) { // [-1,1]
                // Modify the default slide transition to shrink the page as well
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                if (position < 0) {
                    view.setTranslationX(horzMargin - vertMargin / 2);
                } else {
                    view.setTranslationX(-horzMargin + vertMargin / 2);
                }

                // Scale the page down (between MIN_SCALE and 1)
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

                // Fade the page relative to its size.
                view.setAlpha(MIN_ALPHA +
                        (scaleFactor - MIN_SCALE) /
                                (1 - MIN_SCALE) * (1 - MIN_ALPHA));

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }
    class MyPagesAdapter extends PagerAdapter {
        RadioButton rb1;

        @Override
        public int getCount() {
            //Return total pages, here one for each data item
            return pageData.length;
        }
        //Create the given page (indicated by position)
        @Override
        public Object instantiateItem(ViewGroup container, final int position) {

            View page = inflater.inflate(R.layout.fragment_profile, null);
            RadioGroup rgp1 = (RadioGroup) page.findViewById(R.id.radiogroup);
            ((TextView)page.findViewById(R.id.textView4)).setText(pageData[position]);
            for (int i = 0; i < ans[position].length; i++) {

                rb1 = new RadioButton(getApplicationContext());

                rb1.setText(ans[position][i]);


                rgp1.addView(rb1);
            }

            rgp1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                public void onCheckedChanged(RadioGroup rg, int checkedId) {
                    for(int i=0; i<rg.getChildCount(); i++) {
                        RadioButton btn = (RadioButton) rg.getChildAt(i);
                        if(btn.getId() == checkedId) {
                            String text = btn.getText().toString();
                            answers[position] = text;
                        }
                    }
                }
            });

            Button buttonOne = (Button) page.findViewById(R.id.button);
            buttonOne.setOnClickListener(new Button.OnClickListener() {
                public void onClick(View v) {

                   vp.setCurrentItem(vp.getCurrentItem()+1);

                }
            });


            if (position == 9){
                buttonOne.setText("Submit");
                buttonOne.setOnClickListener(new Button.OnClickListener() {
                    public void onClick(View v) {

                        Intent intent = new Intent(getApplication(), MainActivity.class);
                        startActivity(intent);

                    }
                });
            }

            //Add the page to the front of the queue
            ((ViewPager) container).addView(page, 0);
            return page;
        }
        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            //See if object from instantiateItem is related to the given view
            //required by API
            return arg0==(View)arg1;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((View) object);
            object=null;
        }
    }

}
