package co.easy4u.easingsdemo;


import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class InOutEasingFragment extends Fragment {
    private DrawView mHistory;
    private ListView mEaseList;
    private View mTarget;

    private static final String[] sEasings = {
            "Ease.Linear.easeNone",

            "Ease.Cubic.easeIn",
            "Ease.Cubic.easeOut",
            "Ease.Cubic.easeInOut",

            "Ease.Quad.easeIn",
            "Ease.Quad.easeOut",
            "Ease.Quad.easeInOut",

            "Ease.Quart.easeIn",
            "Ease.Quart.easeOut",
            "Ease.Quart.easeInOut",

            "Ease.Quint.easeIn",
            "Ease.Quint.easeOut",
            "Ease.Quint.easeInOut",

            "Ease.Sine.easeIn",
            "Ease.Sine.easeOut",
            "Ease.Sine.easeInOut",
    };

    public InOutEasingFragment() {
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

        mEaseList.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, sEasings));
        mEaseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mHistory.clear();

                ObjectAnimator anim1 = ObjectAnimator.ofFloat(mTarget, "translationY", 0, dipToPixels(getActivity(), -(160 - 3)));
                mTarget.setTranslationX(0);
                mTarget.setTranslationY(0);

                anim1.setDuration(1200);

                String inter = sEasings[position];
                if (TextUtils.equals(inter, sEasings[0])) {
                    anim1.setInterpolator(Ease.Linear.easeNone);
                } else if (TextUtils.equals(inter, sEasings[1])) {
                    anim1.setInterpolator(Ease.Cubic.easeIn);
                } else if (TextUtils.equals(inter, sEasings[2])) {
                    anim1.setInterpolator(Ease.Cubic.easeOut);
                } else if (TextUtils.equals(inter, sEasings[3])) {
                    anim1.setInterpolator(Ease.Cubic.easeInOut);
                } else if (TextUtils.equals(inter, sEasings[4])) {
                    anim1.setInterpolator(Ease.Quad.easeIn);
                } else if (TextUtils.equals(inter, sEasings[5])) {
                    anim1.setInterpolator(Ease.Quad.easeOut);
                } else if (TextUtils.equals(inter, sEasings[6])) {
                    anim1.setInterpolator(Ease.Quad.easeInOut);
                } else if (TextUtils.equals(inter, sEasings[7])) {
                    anim1.setInterpolator(Ease.Quart.easeIn);
                } else if (TextUtils.equals(inter, sEasings[8])) {
                    anim1.setInterpolator(Ease.Quart.easeOut);
                } else if (TextUtils.equals(inter, sEasings[9])) {
                    anim1.setInterpolator(Ease.Quart.easeInOut);
                } else if (TextUtils.equals(inter, sEasings[10])) {
                    anim1.setInterpolator(Ease.Quint.easeIn);
                } else if (TextUtils.equals(inter, sEasings[11])) {
                    anim1.setInterpolator(Ease.Quint.easeOut);
                } else if (TextUtils.equals(inter, sEasings[12])) {
                    anim1.setInterpolator(Ease.Quint.easeInOut);
                } else if (TextUtils.equals(inter, sEasings[13])) {
                    anim1.setInterpolator(Ease.Sine.easeIn);
                } else if (TextUtils.equals(inter, sEasings[14])) {
                    anim1.setInterpolator(Ease.Sine.easeOut);
                } else if (TextUtils.equals(inter, sEasings[15])) {
                    anim1.setInterpolator(Ease.Sine.easeInOut);
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
}
