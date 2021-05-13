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
public enum RssFeedCategory {

    WORLD_NEWS("World News"),
    LOCAL_NEWS("Local News"),
    FINANCE("Finance"),
    GOVERNMENT("Government"),
    HEALTH("Health"),
    WEATHER("Weather"),
    ENVIRONMENTAL("Environmental"),
    SPORTS("Sports"),
    TECHNOLOGY("Technology"),
    BUSINESS("Business");

    RssFeedCategory(String label) {
        this.label = label;
    }

    private String label;

    public String getLabel() {
        return label;
    }

    public static RssFeedCategory getCategoryByString(String categoryAsString) {
        for (RssFeedCategory category : RssFeedCategory.values()) {
            if (category.toString().equals(categoryAsString)) {
                return category;
            }
        }

        return null;
    }
}
