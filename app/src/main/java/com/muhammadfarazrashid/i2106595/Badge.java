package com.muhammadfarazrashid.i2106595;

public class Badge {

    private String name;
    private boolean isSelected;

    // Constructor, getters, setters
    public Badge( String name, boolean isSelected) {

        this.name = name;
        this.isSelected = isSelected;
    }



    public String getName() {
        return name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

}
