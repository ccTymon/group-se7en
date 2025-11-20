package use_case.search;

public class SearchOutputData {
    private final String name;
    private final String rate;
    private final String icon;
    private final String plot;
    private final String id;

    public SearchOutputData(String name, String rate, String icon, String plot, String id) {
        this.name = name;
        this.rate = rate;
        this.icon = icon;
        this.plot = plot;
        this.id = id;
    }

    public String getMovieName() {
        return name;
    }
    public String getRate() {
        return rate;
    }
    public String getIcon() { return icon;}
    public String getPlot() { return plot;}
    public String getId() { return id;}
}
