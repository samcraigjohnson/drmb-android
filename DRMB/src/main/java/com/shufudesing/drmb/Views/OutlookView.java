package com.shufudesing.drmb.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.util.AttributeSet;

import com.shufudesing.drmb.DrUTILS;
import com.shufudesing.drmb.Drawables.TextDrawable;

/**
 * Created by Sam on 6/23/2014.
 */
public class OutlookView extends BasePopupView{

    private ShapeDrawable statusBox, optionsBox, saveButton;
    private TextDrawable budgetText, moneyText, youAre;
    private TextDrawable optionsHeadingText, optionsText;
    private TextDrawable saveButtonText;

    public OutlookView(Context context) {
        super(context);
    }

    public OutlookView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OutlookView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init(){
        super.init();
        int iconSize = 200;
        int boxSize = 250;
        statusBox = new ShapeDrawable(new RectShape());
        statusBox.getPaint().setColor(DrUTILS.GRAY);
        statusBox.setBounds(leftX+20, topY+iconSize, rightX-20, topY+iconSize+boxSize);

        optionsBox = new ShapeDrawable(new RectShape());
        optionsBox.getPaint().setColor(DrUTILS.GRAY);
        optionsBox.setBounds(leftX+20, topY+iconSize+boxSize+10, rightX-20, topY+iconSize+(boxSize*2)+10);

        saveButton = new ShapeDrawable(new RectShape());
        saveButton.getPaint().setColor(DrUTILS.GRAY);
        saveButton.setBounds(leftX+20,topY+iconSize+(boxSize*2)+60, rightX-20, bottomY-20);

        optionsHeadingText = new TextDrawable("CHOOSE OPTIONS:", (int)(topY+iconSize+boxSize+10+DrUTILS.INFO_TEXT_SIZE+10));
        optionsHeadingText.setTextSize(DrUTILS.INFO_TEXT_SIZE);
        optionsHeadingText.getPaint().setColor(DrUTILS.ORANGE);

        optionsText = new TextDrawable("Spend $20 less a day\nBack on track in\n 4 days",(int)(topY+iconSize+boxSize+10+(DrUTILS.INFO_TEXT_SIZE*2)+20));
        optionsText.setTextSize(DrUTILS.INFO_TEXT_SIZE-5);

        youAre = new TextDrawable("YOU ARE", (int)(topY+iconSize+DrUTILS.INFO_TEXT_SIZE+10));
        youAre.setTextSize(DrUTILS.INFO_TEXT_SIZE);
        moneyText = new TextDrawable("$28.37", (int)(topY+iconSize+(boxSize/1.5))-15);
        moneyText.setTextSize(DrUTILS.ALT_MONEY_TEXT_SIZE);
        moneyText.getPaint().setColor(DrUTILS.RED);
        budgetText = new TextDrawable("OVER BUDGET", (int)(topY+iconSize+(boxSize/2)+DrUTILS.MONEY_TEXT_SIZE+20));
        budgetText.setTextSize(DrUTILS.INFO_TEXT_SIZE);

        saveButtonText = new TextDrawable("S A V E", ((topY+iconSize+(boxSize*2)+80)+bottomY-10)/2);
        saveButtonText.setTextSize(DrUTILS.INFO_TEXT_SIZE);
        saveButtonText.getPaint().setColor(DrUTILS.BLUE);
    }

    @Override
    protected void onDraw(Canvas c){
        super.onDraw(c);
        statusBox.draw(c);
        budgetText.draw(c);
        youAre.draw(c);
        moneyText.draw(c);

        optionsBox.draw(c);
        optionsHeadingText.draw(c);
        optionsText.draw(c);

        saveButton.draw(c);
        saveButtonText.draw(c);
    }
}
