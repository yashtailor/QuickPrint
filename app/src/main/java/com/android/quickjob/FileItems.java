package com.android.quickjob;

public class FileItems {
private String fileName,path;
private int fileCost,numberOfPages;


    public FileItems(String fileName, int fileCost, int numberOfPages,String path) {
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
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
