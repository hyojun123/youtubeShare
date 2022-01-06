package xyz.hjtech.myyoutubeproject.youtube.model;

import javax.persistence.Embeddable;

@Embeddable
public class YoutubeThumbnail {
    private String url;
    private Integer width;
    private Integer height;
}
