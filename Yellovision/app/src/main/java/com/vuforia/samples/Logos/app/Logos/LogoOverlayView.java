/*===============================================================================
Copyright (c) 2016 PTC Inc. All Rights Reserved.

Copyright (c) 2012-2014 Qualcomm Connected Experiences, Inc. All Rights Reserved.

Vuforia is a trademark of PTC Inc., registered in the United States and other 
countries.
===============================================================================*/

package com.vuforia.samples.Logos.app.Logos;

import com.vuforia.samples.Logos.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

// Custom View with Logo Overlay Data
public class LogoOverlayView extends RelativeLayout
{
    public LogoOverlayView(Context context)
    {
        this(context, null);
    }
    
    
    public LogoOverlayView(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }
    
    
    public LogoOverlayView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        inflateLayout(context);
        
    }
    
    
    // Inflates the Custom View Layout
    private void inflateLayout(Context context)
    {
        
        final LayoutInflater inflater = LayoutInflater.from(context);
        
        // Generates the layout for the view
        inflater.inflate(R.layout.bitmap_layout, this, true);
    }

    
    // Sets Logo title in View
    public void setBookName(String bookName)
    {
        TextView tv = (TextView) findViewById(R.id.custom_view_title);
        //TextView tv2 = (TextView) findViewById(R.id.company_view_name);
        tv.setText(bookName);
        //tv2.setText(bookName);
    }
    
    
    // Sets Logo Author in View
    public void setBookLocation(String bookLocation)
    {
        TextView tv = (TextView) findViewById(R.id.custom_view_author);
        tv.setText(bookLocation);
    }
    
    
    // Sets Logo Price in View
    public void setBookOpenings(String bookOpenings)
    {
        TextView tv = (TextView) findViewById(R.id.custom_view_price_old);
        tv.setText(bookOpenings);
    }
    
    
    // Sets Logo Number of Ratings in View
    public void setBookRatingCount(String ratingCount)
    {
        TextView tv = (TextView) findViewById(R.id.custom_view_rating_text);
        tv.setText(getContext().getString(R.string.string_openParentheses)
            + ratingCount + getContext().getString(R.string.string_ratings)
            + getContext().getString(R.string.string_closeParentheses));
    }
    
    
    // Sets Logo Special Price in View
    public void setDescription(String description)
    {
        TextView tv = (TextView) findViewById(R.id.badge_price_value);
        tv.setText(description);
    }

    
    // Sets Logo Cover in View from a bitmap
//    public void setCoverViewFromBitmap(Bitmap coverBook)
//    {
//        ImageView iv = (ImageView) findViewById(R.id.custom_view_book_cover);
//        iv.setImageBitmap(coverBook);
//    }
//
//
//    // Sets Logo Rating in View
//    public void setRating(String rating)
//    {
//        RatingBar rb = (RatingBar) findViewById(R.id.custom_view_rating);
//        rb.setRating(Float.parseFloat(rating));
//    }
}
