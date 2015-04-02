package co.easy4u.easingsdemo;


import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class InterpolatorFragment extends Fragment {

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

    public InterpolatorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_ease, container, false);

        mHistory = (DrawView) v.findViewById(R.id.history);
        mTarget = v.findViewById(R.id.target);
        mEaseList = (ListView) v.findViewById(R.id.list);

        mEaseList.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, sInterpolators));
        mEaseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mHistory.clear();

                ObjectAnimator anim1 = ObjectAnimator.ofFloat(mTarget, "translationY", 0, dipToPixels(getActivity(), -(160 - 3)));
                mTarget.setTranslationX(0);
                mTarget.setTranslationY(0);

                anim1.setDuration(1200);

                String inter = sInterpolators[position];
                if (inter.equals("DecelerateInterpolator")) {
                    anim1.setInterpolator(new DecelerateInterpolator(3f));
                } else {
                    setInterpolatorWithNoParam(inter, anim1);
                }

                anim1.start();

                anim1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        long time = animation.getCurrentPlayTime();
                        long duration = animation.getDuration();
                        float value = (Float)animation.getAnimatedValue();

                        mHistory.drawPoint(time, duration, value - dipToPixels(getActivity(), 60));
                    }
                });
            }
        });

        return v;
    }

    public static float dipToPixels(Context context, float dipValue) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
    }

    private void setInterpolatorWithNoParam(String inter, ObjectAnimator anim1) {
        try {
            Class<? extends Interpolator> interClass = Class.forName("android.view.animation." + inter).asSubclass(Interpolator.class);
            anim1.setInterpolator(interClass.newInstance());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
