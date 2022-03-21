package controllers;

import play.data.validation.Constraints;

/**
 * @author Stallone, Swapnil, Esha, Saumya
 * @version 1: Stallone, Swapnil, Esha, Saumya implements search box.
 *
 * Holds an input text for the search box
 */
public class SearchForm {
    @Constraints.Required
    private String input;

    /**
     * @author Stallone, Swapnil, Esha, Saumya*
     * The getter of the input
     * @return input
     */
    public String getInput() {
        return input;
    }

    /**
     * @author Stallone, Swapnil, Esha, Saumya*
     * The setter of the input
     * @param input - Setting the search input
     */
    public void setInput(String input) {
        this.input = input;
    }
}