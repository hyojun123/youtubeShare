package xyz.hjtech.myyoutubeproject.youtube.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class YoutubeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String searchTxt;

    private String videoId;
    private String publishedAt;
    private String channelId;
    @Column(length = 4000)
    private String title;
    @Column(length = 4000)
    private String description;


    @Column(length = 4000)
    private String thumbnailUrl;
    private Integer width;
    private Integer height;
    private String channelTitle;
    private String publishTime;
    private Date regTsp;

    public YoutubeEntity(String videoId, String searchTxt, String publishedAt, String channelId, String title, String description, String thumbnailUrl, Integer width, Integer height, String channelTitle, String publishTime) {
        this.videoId = videoId;
        this.searchTxt = searchTxt;
        this.publishedAt = publishedAt;
        this.channelId = channelId;
        this.title = title;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
        this.width = width;
        this.height = height;
        this.channelTitle = channelTitle;
        this.publishTime = publishTime;
        this.regTsp = new Date();
    }





}