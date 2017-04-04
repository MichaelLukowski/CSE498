/*===============================================================================
Copyright (c) 2016 PTC Inc. All Rights Reserved.

Copyright (c) 2012-2014 Qualcomm Connected Experiences, Inc. All Rights Reserved.

Vuforia is a trademark of PTC Inc., registered in the United States and other 
countries.
===============================================================================*/

package com.vuforia.samples.Books.app.Books;

import android.graphics.Bitmap;
import android.widget.ImageView;


// A support class encapsulating the info for one book
public class Book
{
    private String name;
    private String location;
    private String ratingAvg;
    private String ratingTotal;
    private String open_positions;
    private String description;
    private String targetId;
    private String majors;
    
    
    public Book()
    {
        
    }
    
    
    public String getName()
    {
        return name;
    }
    
    
    public void setName(String name){ this.name = name; }
    
    
    public String getLocation()
    {
        return location;
    }
    
    
    public void setLocation(String location)
    {
        this.location = location;
    }
    
    
    public String getRatingAvg()
    {
        return ratingAvg;
    }
    
    
    public void setRatingAvg(String ratingAvg)
    {
        this.ratingAvg = ratingAvg;
    }
    
    
    public String getRatingTotal()
    {
        return ratingTotal;
    }
    
    
    public void setRatingTotal(String ratingTotal)
    {
        this.ratingTotal = ratingTotal;
    }
    
    
    public String getOpenings()
    {
        return open_positions;
    }
    
    
    public void setOpenings(String open_positions)
    {
        this.open_positions = open_positions;
    }
    
    
    public String getDescription()
    {
        return description;
    }
    
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    
    public String getTargetId()
    {
        return targetId;
    }
    
    
    public void setTargetId(String targetId)
    {
        this.targetId = targetId;
    }
    
    
    public String getMajors()
    {
        return majors;
    }
    
    
    public void setMajors(String bookUrl)
    {
        this.majors = bookUrl;
    }

}
