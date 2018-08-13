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
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Instructions extends FragmentActivity {
    ConstraintLayout layout;
    RadioGroup rgp;

    String pageData[];

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
        setContentView(R.layout.activity_instructions);

        // Instantiate a ViewPager and a PagerAdapter.
        layout = (ConstraintLayout) findViewById(R.id.content);

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
            return 2;
        }
        //Create the given page (indicated by position)
        @Override
        public Object instantiateItem(ViewGroup container, final int position) {

            View page = inflater.inflate(R.layout.fragment_frag_instruct, null);

            ImageView im = (ImageView) page.findViewById(R.id.instruct);



                if (position == 0) {

                    im.setImageResource(R.drawable.instruct1);
                }
                if (position == 1) {
                    im.setImageResource(R.drawable.instruct2);
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
