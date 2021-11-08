/*
 *  UCF COP3330 Fall 2021 Application Assignment 1 Solution
 *  Copyright 2021 Nader Fares
 */
package baseline;

public class Error {
    private final String headerText;      //header text for error code
    private final String contentText;     //content text for error code

    public Error(String headerText, String contentText) {
        this.headerText = headerText;
        this.contentText = contentText;
    }

    public String getHeaderText() {
        return headerText;
    }


    public String getContentText() {
        return contentText;
    }
}
