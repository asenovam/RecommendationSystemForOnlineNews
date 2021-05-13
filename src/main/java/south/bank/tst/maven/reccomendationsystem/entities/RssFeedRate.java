/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package south.bank.tst.maven.reccomendationsystem.entities;

/**
 *
 * @author mar_9
 */
public enum RssFeedRate {

    ONE_STAR("1 star", 1),
    TWO_STARS("2 stars", 2),
    THREE_STARS("3 stars", 3),
    FOUR_STARS("4 stars", 4),
    FIVE_STARS("5 stars", 5);

    RssFeedRate(String label, int rating) {
        this.label = label;
        this.rating = rating;
    }

    private String label;
    private int rating;

    public String getLabel() {
        return label;
    }
    
    public int getRating() {
        return rating;
    }

    public static RssFeedRate getRateByValue(int value) {
        for (RssFeedRate rate : RssFeedRate.values()) {
            if (rate.getRating() == value) {
                return rate;
            }
        }
        
        return null;
    }
}
