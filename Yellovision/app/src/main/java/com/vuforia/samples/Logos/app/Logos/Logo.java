package com.vuforia.samples.Logos.app.Logos;


// A support class encapsulating the info for one book
public class Logo
{
    private String name;
    private String location;
    private String open_positions;
    private String description;
    private String majors;
    
    
    public Logo()
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
    
    
    public String getMajors()
    {
        return majors;
    }
    
    
    public void setMajors(String bookUrl)
    {
        this.majors = bookUrl;
    }

}
