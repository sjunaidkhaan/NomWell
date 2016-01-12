package com.zai.nomwell.dialog;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.zai.nomwell.R;

/**
 * Created by chitta on 12/6/15.
 */
public class NomwellFilterDialog {

    private View view;

    public static final int SPOT_ALL = 1;
    public static final int SPOT_WANT_TO_GO = 2;
    public static final int SPOT_GONE = 3;

    public static final int STARS_1 = 1;
    public static final int STARS_2 = 2;
    public static final int STARS_3 = 3;
    public static final int STARS_4 = 4;
    public static final int STARS_5 = 5;

    public static final int PRICE_1 = 1;
    public static final int PRICE_2 = 2;
    public static final int PRICE_3 = 3;
    public static final int PRICE_4 = 4;

    private int starsSelectedPosition = -1;
    private int priceSelectedPosition = -1;

    private Context context;

    private LinearLayout llSpots;
    private AppCompatTextView txtAll;
    private AppCompatImageView imvwWantToGo;
    private AppCompatImageView imvwGone;

    private LinearLayout llStars;
    private AppCompatTextView lblStars[] = new AppCompatTextView[5];

    private LinearLayout llPrice;
    private AppCompatTextView lblPrice[] = new AppCompatTextView[4];

    private AppCompatTextView txtCuisine;
    private AppCompatTextView txtTag;

    private View.OnClickListener onClickListener;

    public NomwellFilterDialog(Context context) {
        this.context = context;
        this.view = LayoutInflater.from(context).inflate(R.layout.dialog_filter, null);
        init();
    }

    public View getView() {
        return view;
    }

    private void init() {
        llSpots = (LinearLayout) view.findViewById(R.id.llSpots);
        txtAll = (AppCompatTextView) llSpots.findViewById(R.id.txtAll);
        txtAll.setOnClickListener(clickListener);

        imvwWantToGo = (AppCompatImageView) llSpots.findViewById(R.id.imvwWantToGo);
        imvwWantToGo.setOnClickListener(clickListener);
        imvwWantToGo.setColorFilter(ContextCompat.getColor(context, R.color.filter_dialog_color));

        imvwGone = (AppCompatImageView) llSpots.findViewById(R.id.imvwGone);
        imvwGone.setOnClickListener(clickListener);
        imvwGone.setColorFilter(ContextCompat.getColor(context, R.color.filter_dialog_color));

        llStars = (LinearLayout) view.findViewById(R.id.llStars);

        lblStars[0] = (AppCompatTextView) llStars.findViewById(R.id.lbl1);
        lblStars[0].setTag(R.id.lbl1);
        lblStars[1] = (AppCompatTextView) llStars.findViewById(R.id.lbl2);
        lblStars[1].setTag(R.id.lbl2);
        lblStars[2] = (AppCompatTextView) llStars.findViewById(R.id.lbl3);
        lblStars[3] = (AppCompatTextView) llStars.findViewById(R.id.lbl4);
        lblStars[4] = (AppCompatTextView) llStars.findViewById(R.id.lbl5);
        for (int i = 0; i < lblStars.length; i++) {
            lblStars[i].setOnClickListener(clickListener);
        }

        llPrice = (LinearLayout) view.findViewById(R.id.llPrice);
        lblPrice[0] = (AppCompatTextView) llPrice.findViewById(R.id.lblPrice1);
        lblPrice[1] = (AppCompatTextView) llPrice.findViewById(R.id.lblPrice2);
        lblPrice[2] = (AppCompatTextView) llPrice.findViewById(R.id.lblPrice3);
        lblPrice[3] = (AppCompatTextView) llPrice.findViewById(R.id.lblPrice4);
        for (int i = 0; i < lblPrice.length; i++) {
            lblPrice[i].setOnClickListener(clickListener);
        }

        txtCuisine = (AppCompatTextView) view.findViewById(R.id.txtCuisine);
        txtCuisine.setOnClickListener(clickListener);
        txtTag = (AppCompatTextView) view.findViewById(R.id.txtTag);
        txtTag.setOnClickListener(clickListener);
    }

    public void addClickListener(View.OnClickListener listener) {
        this.onClickListener = listener;
    }

    public int getStarsSelectedPosition() {
        return starsSelectedPosition;
    }

    public int getPriceSelectedPosition() {
        return priceSelectedPosition;
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (onClickListener != null) {
                onClickListener.onClick(view);
            }

            switch (view.getId()) {
                case R.id.txtAll:
                case R.id.imvwWantToGo:
                case R.id.imvwGone:
                    updateSpots(view);
                    break;
                case R.id.lbl1:
                case R.id.lbl2:
                case R.id.lbl3:
                case R.id.lbl4:
                case R.id.lbl5:
                    updateStars(view);
                    break;
                case R.id.lblPrice1:
                case R.id.lblPrice2:
                case R.id.lblPrice3:
                case R.id.lblPrice4:
                    updatePrice(view);
                    break;
            }
        }
    };

    public void setCuisineText(String text) {
        SpannableString content = new SpannableString(text);
        content.setSpan(new UnderlineSpan(), 0, text.length(), 0);
        txtCuisine.setText(content);
    }

    public String getCuisineText() {
        return txtCuisine.getText().toString();
    }

    public void setTagText(String text) {
        SpannableString content = new SpannableString(text);
        content.setSpan(new UnderlineSpan(), 0, text.length(), 0);
        txtTag.setText(content);
    }

    public String getTagText() {
        return txtTag.getText().toString();
    }

    private void updateSpots(View view) {
        switch (view.getId()) {
            case R.id.txtAll:
                txtAll.setBackgroundResource(
                        R.drawable.bg_filter_view_left_selected);
                imvwWantToGo.setBackgroundResource(
                        R.drawable.bg_filter_view_center);
                imvwGone.setBackgroundResource(
                        R.drawable.bg_filter_view_right);
                txtAll.setTextColor(ContextCompat.getColor(context, R.color.white));
                imvwWantToGo.setColorFilter(ContextCompat.getColor(context, R.color.filter_dialog_color));
                imvwGone.setColorFilter(ContextCompat.getColor(context, R.color.filter_dialog_color));
                break;
            case R.id.imvwWantToGo:
                txtAll.setBackgroundResource(
                        R.drawable.bg_filter_view_left);
                imvwWantToGo.setBackgroundResource(
                        R.drawable.bg_filter_view_center_selected);
                imvwGone.setBackgroundResource(
                        R.drawable.bg_filter_view_right);
                txtAll.setTextColor(ContextCompat.getColor(context, R.color.filter_dialog_color));
                imvwWantToGo.setColorFilter(ContextCompat.getColor(context, R.color.white));
                imvwGone.setColorFilter(ContextCompat.getColor(context, R.color.filter_dialog_color));
                break;
            case R.id.imvwGone:
                txtAll.setBackgroundResource(
                        R.drawable.bg_filter_view_left);
                imvwWantToGo.setBackgroundResource(
                        R.drawable.bg_filter_view_center);
                imvwGone.setBackgroundResource(
                        R.drawable.bg_filter_view_right_selected);
                txtAll.setTextColor(ContextCompat.getColor(context, R.color.filter_dialog_color));
                imvwWantToGo.setColorFilter(ContextCompat.getColor(context, R.color.filter_dialog_color));
                imvwGone.setColorFilter(ContextCompat.getColor(context, R.color.white));
                break;
        }
    }

    private void updateStars(View view) {
        for (int i = 0; i < lblStars.length; i++) {
            if (i == 0) {
                lblStars[i].setBackgroundResource(view.getId() == lblStars[i].getId() ?
                        R.drawable.bg_filter_view_left_selected : R.drawable.bg_filter_view_left);
            } else if (i == (lblStars.length - 1)) {
                lblStars[i].setBackgroundResource(view.getId() == lblStars[i].getId() ?
                        R.drawable.bg_filter_view_right_selected : R.drawable.bg_filter_view_right);
            } else {
                lblStars[i].setBackgroundResource(view.getId() == lblStars[i].getId() ?
                        R.drawable.bg_filter_view_center_selected : R.drawable.bg_filter_view_center);
            }

            int color = view.getId() == lblStars[i].getId() ?
                    ContextCompat.getColor(context, R.color.white) : ContextCompat.getColor(context, R.color.filter_dialog_color);
            lblStars[i].setTextColor(color);

            if (view.getId() == lblStars[i].getId()) {
                starsSelectedPosition = i;
            }
        }
    }

    private void updatePrice(View view) {
        for (int i = 0; i < lblPrice.length; i++) {

            if (view.getId() == lblPrice[i].getId()) {
                priceSelectedPosition = i;

                boolean selected = false;
                if (lblPrice[i].getTag() != null) {
                    selected = (boolean) lblPrice[i].getTag();
                }
                lblPrice[i].setTag(!selected);
                selected = !selected;
                if (i == 0) {
                    lblPrice[i].setBackgroundResource(selected ?
                            R.drawable.bg_filter_view_left_selected : R.drawable.bg_filter_view_left);
                } else if (i == (lblPrice.length - 1)) {
                    lblPrice[i].setBackgroundResource(selected ?
                            R.drawable.bg_filter_view_right_selected : R.drawable.bg_filter_view_right);
                } else {
                    lblPrice[i].setBackgroundResource(selected ?
                            R.drawable.bg_filter_view_center_selected : R.drawable.bg_filter_view_center);
                }

                int color = selected ?
                        ContextCompat.getColor(context, R.color.white) : ContextCompat.getColor(context, R.color.filter_dialog_color);
                lblPrice[i].setTextColor(color);
            }
        }
    }

}
