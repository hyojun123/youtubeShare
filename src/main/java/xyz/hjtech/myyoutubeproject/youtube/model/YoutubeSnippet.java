package xyz.hjtech.myyoutubeproject.youtube.model;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
public class YoutubeSnippet {
    private String publishedAt;
    private String channelId;
    private String title;
    private String description;
    @Embedded
    private YoutubeThumbnail thumbnails;
}
