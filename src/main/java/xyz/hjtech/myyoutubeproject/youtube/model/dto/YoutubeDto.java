package xyz.hjtech.myyoutubeproject.youtube.model.dto;

import lombok.Data;

import java.util.Map;

@Data
public class YoutubeDto {

    private Map<String, Object> id;
    private Map<String, Object> snippet;
}
