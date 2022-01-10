package xyz.hjtech.myyoutubeproject.youtube.model.dto;

import lombok.Data;
import xyz.hjtech.myyoutubeproject.youtube.model.YoutubeEntity;

import javax.persistence.Column;
import java.util.Date;

@Data
public class GetYoutubeDto {
    private Long id;

    private String searchTxt;

    private String videoId;
    private String publishedAt;
    private String channelId;
    private String title;
    private String description;
    private String thumbnailUrl;
    private Integer width;
    private Integer height;
    private String channelTitle;
    private String publishTime;
    private Date regTsp;
    private boolean like;

    public GetYoutubeDto(YoutubeEntity entity, boolean like) {
        this.id = entity.getId();
        this.searchTxt = entity.getSearchTxt();
        this.videoId = entity.getVideoId();
        this.publishedAt = entity.getPublishedAt();
        this.channelId = entity.getChannelId();
        this.title = entity.getTitle();
        this.description = entity.getDescription();
        this.thumbnailUrl = entity.getThumbnailUrl();
        this.width = entity.getWidth();
        this.height = entity.getHeight();
        this.channelTitle = entity.getChannelTitle();
        this.publishTime = entity.getPublishTime();
        this.regTsp = entity.getRegTsp();
        this.like = like;
    }
}
