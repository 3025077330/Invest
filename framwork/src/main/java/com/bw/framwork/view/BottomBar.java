package com.bw.framwork.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;

import com.bw.framwork.R;

public class BottomBar extends LinearLayout {

    //Bommtom各个下标
    public static final int HOME_INDEX = 0;
    public static final int INVEST_INDEX = 1;
    public static final int MINE_INDEX = 2;
    public static final int MORE_INDEX = 3;
    //BottomBar文字颜色
    private int selectColor;
    private int unselectColor;

    private IBottomBarSelectListener iBottomBarSelectListener;

    //定义一个接口，这个接口，Activity或者Fragment实现这个接口，通过这个接口达到自定义view和Activity或者Fragment之间的通信
    public interface IBottomBarSelectListener {
        void onBottomBarSelected(int selectIndex);

        void onBottomForPagerSelected(int selectindex);
    }

    public void setBottomBarSelectListener(IBottomBarSelectListener listener) {
        this.iBottomBarSelectListener = listener;
    }


    public BottomBar(Context context) {
        super(context);
        init(context, null, 0);
    }

    public BottomBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, null, 0);
    }

    public BottomBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        initBottomBarAttrs(context,attrs);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.bottombar_layout, this);
        RadioGroup radioGroup = view.findViewById(R.id.bottomGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.bu_home) {
                    selectHome();
                } else if (checkedId == R.id.bu_invest) {
                    selectInvest();
                } else if (checkedId == R.id.bu_mine) {
                    selectMine();
                } else if (checkedId == R.id.bu_more) {
                    selectMore();
                }
            }
        });
        selectHome();//默认显示HOME页面
    }

    //数组的长度为4
    public void setTabTitle(String[] tabTitles) {
        RadioButton homeButton = findViewById(R.id.bu_home);
        homeButton.setText(tabTitles[0]);
        RadioButton investButton = findViewById(R.id.bu_invest);
        investButton.setText(tabTitles[1]);
        RadioButton mineButton = findViewById(R.id.bu_mine);
        mineButton.setText(tabTitles[2]);
        RadioButton moreButton = findViewById(R.id.bu_more);
        moreButton.setText(tabTitles[3]);
    }


    public void FindIndex(int postion) {
        switch (postion) {
            case HOME_INDEX:
                selectHome();
                break;
            case INVEST_INDEX:
                selectInvest();
                break;
            case MORE_INDEX:
                selectMore();
                break;
            case MINE_INDEX:
                selectMine();
                break;
            default:
                selectHome();
        }


    }

    private void selectMore() {
        RadioButton homeButton = findViewById(R.id.bu_home);
        homeButton.setTextColor(unselectColor);
        RadioButton investButton = findViewById(R.id.bu_invest);
        investButton.setTextColor(unselectColor);
        RadioButton moreButton = findViewById(R.id.bu_more);
        moreButton.setTextColor(selectColor);
        moreButton.setChecked(true);//设置为选中状态
        RadioButton mineButton = findViewById(R.id.bu_mine);
        mineButton.setTextColor(unselectColor);
        if (iBottomBarSelectListener != null) {
            iBottomBarSelectListener.onBottomBarSelected(MORE_INDEX);
        }
    }

    private void selectMine() {
        RadioButton homeButton = findViewById(R.id.bu_home);
        homeButton.setTextColor(unselectColor);

        RadioButton investButton = findViewById(R.id.bu_invest);
        investButton.setTextColor(unselectColor);

        RadioButton moreButton = findViewById(R.id.bu_more);
        moreButton.setTextColor(unselectColor);
        RadioButton mineButton = findViewById(R.id.bu_mine);
        mineButton.setTextColor(selectColor);
        mineButton.setChecked(true);//设置为选中状态
        if (iBottomBarSelectListener != null) {
            iBottomBarSelectListener.onBottomBarSelected(MINE_INDEX);
        }
    }

    private void selectInvest() {
        RadioButton homeButton = findViewById(R.id.bu_home);
        homeButton.setTextColor(unselectColor);
        RadioButton investButton = findViewById(R.id.bu_invest);
        investButton.setTextColor(selectColor);
        investButton.setChecked(true);//设置为选中状态
        RadioButton moreButton = findViewById(R.id.bu_more);
        moreButton.setTextColor(unselectColor);
        RadioButton mineButton = findViewById(R.id.bu_mine);
        mineButton.setTextColor(unselectColor);
        if (iBottomBarSelectListener != null) {
            iBottomBarSelectListener.onBottomBarSelected(INVEST_INDEX);
        }
    }

    private void selectHome() {
        RadioButton homeButton = findViewById(R.id.bu_home);
        homeButton.setChecked(true);//设置为选中状态
        homeButton.setTextColor(selectColor);
        RadioButton investButton = findViewById(R.id.bu_invest);
        investButton.setTextColor(unselectColor);
        RadioButton mineButton = findViewById(R.id.bu_mine);
        mineButton.setTextColor(unselectColor);
        RadioButton moreButton = findViewById(R.id.bu_more);
        moreButton.setTextColor(unselectColor);
        if (iBottomBarSelectListener != null) {
            iBottomBarSelectListener.onBottomBarSelected(HOME_INDEX);
        }
    }

    private void initBottomBarAttrs(Context context, AttributeSet attributeSet) {
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.BottomBar);
        selectColor = typedArray.getColor(R.styleable.BottomBar_select_textcolor, Color.RED);
        unselectColor = typedArray.getColor(R.styleable.BottomBar_unselect_textcolor, Color.BLACK);
    }
}
