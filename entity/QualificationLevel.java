package entity;

public enum QualificationLevel {
    BEGINNER("Početnički nivo"),
    INTERMEDIATE("Srednji nivo"),
    ADVANCED("Napredni nivo"),
    EXPERT("Ekspertski nivo"),
    MASTER("Master nivo"),
    SPECIALIST("Specijalistički nivo"),
    NO_QUALIFICATION("Bez stručne spreme");

    private final String description;

    QualificationLevel(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
