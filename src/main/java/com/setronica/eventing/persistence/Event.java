package com.setronica.eventing.persistence;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

public class Event {
    private int id;

    @NotNull
    @NotBlank(message = "Title is mandatory field")
    private String title;

    @NotNull
    @NotBlank(message = "Description is mandatory field")
    private String description;

    @NotNull
    @NotBlank(message = "Date is mandatory field")
    private LocalDate date;

    private List<String> images;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    /**
     * Sorts a list of events based on a specified parameter and order.
     *
     * @param events The list of events to be sorted.
     * @param sortBy The parameter by which to sort the events ("title" or "date").
     * @param sort   The order of sorting ("asc" for ascending, "desc" for descending).
     *               Note: The function assumes "asc" if the provided value is not "desc".
     * @return A sorted list of events based on the specified parameter and order.
     */
    public static List<Event> sort(List<Event> events, String sortBy, String sort) {
        Comparator<Event> comparator = switch (sortBy.toLowerCase()) {
            case "title" -> Comparator.comparing(Event::getTitle);
            case "date" -> Comparator.comparing(Event::getDate);
            default -> throw new IllegalArgumentException("Invalid sortBy option: " + sortBy);
        };

        if ("desc".equalsIgnoreCase(sort)) {
            comparator = comparator.reversed();
        }

        events.sort(comparator);
        return events;
    }

    /**
     * Retrieves a list of demo images URLs for testing or demonstration purposes.
     *
     * @return A list of demo image URLs.
     * Note: This function provides a set of demo images that can be used for testing or demonstration.
     */
    public static List<String> getDemoImages() {
        return List.of(
                "https://www.rollingstone.com/wp-content/uploads/2022/12/TNMRollingStone_FrontLine-3.jpg?w=1581&h=1054&crop=1",
                "https://theatre.colostate.edu/wp-content/uploads/sites/11/2017/02/apocalyptica_concert_in_warsaw_by_lestreux-d31m8k5.png",
                "https://thestagesofmn.files.wordpress.com/2022/06/2022mayrichryan202.jpg?w=1024"
        );
    }
}
