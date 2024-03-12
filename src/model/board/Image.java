package model.board;

public enum Image {
    BOMB_IMG("https://prod.domain.com:1088/");

    private String url;

    Image(String envUrl) {
        this.url = envUrl;
    }

    public String getUrl() {
        return url;
    }
}
