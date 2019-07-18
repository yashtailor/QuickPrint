package com.android.quickjob;

import android.net.Uri;

public class FileItems {
private String fileName;
private Uri path;
private int fileCost,numberOfPages;
private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public FileItems(String fileName, int fileCost, int numberOfPages, Uri path) {
        this.fileName = fileName;
        this.fileCost = fileCost;
        this.numberOfPages = numberOfPages;
        this.path = path;
    }

    public String getFileName() {
        return fileName;
    }

    public int getFileCost() {
        return fileCost;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }


    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Uri getPath() {
        return path;
    }

    public void setPath(Uri path) {
        this.path = path;
    }

    public void setFileCost(int fileCost) {
        this.fileCost = fileCost;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public FileItems(){
        //it is required
    }
}
