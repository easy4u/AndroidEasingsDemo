package co.easy4u.easingsdemo;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class EaseActivity extends ActionBarActivity {

    private DrawView mHistory;
    private ListView mEaseList;
    private View mTarget;

    private static final String[] sInterpolators = {
            "AccelerateInterpolator",
            "AccelerateDecelerateInterpolator",
            "AnticipateInterpolator",
            "AnticipateOvershootInterpolator",
            "BounceInterpolator",
            "CycleInterpolator",
            "DecelerateInterpolator",
            "FallbackLUTInterpolator",
            "LinearInterpolator",
            "OvershootInterpolator",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ease);

        mHistory = (DrawView) findViewById(R.id.history);
        mTarget = findViewById(R.id.target);
        mEaseList = (ListView) findViewById(R.id.list);

        mEaseList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, sInterpolators));
        mEaseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mHistory.clear();

                ObjectAnimator anim1 = ObjectAnimator.ofFloat(mTarget, "translationY", 0, dipToPixels(EaseActivity.this, -(160 - 3)));
                mTarget.setTranslationX(0);
                mTarget.setTranslationY(0);

                anim1.setDuration(1200);
                try {
                    String inter = sInterpolators[position];
                    Class<? extends Interpolator> interClass = Class.forName("android.view.animation." + inter).asSubclass(Interpolator.class);
                    anim1.setInterpolator(interClass.newInstance());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

                anim1.start();

                anim1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        long time = animation.getCurrentPlayTime();
                        long duration = animation.getDuration();
                        float value = (Float)animation.getAnimatedValue();

                        mHistory.drawPoint(time, duration, value - dipToPixels(EaseActivity.this, 60));
                    }
                });
            }
        });
    }

    public static float dipToPixels(Context context, float dipValue) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ease, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
