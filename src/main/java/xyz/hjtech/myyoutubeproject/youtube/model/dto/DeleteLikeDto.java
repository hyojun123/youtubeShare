package xyz.hjtech.myyoutubeproject.youtube.model.dto;

import lombok.Data;

@Data
public class DeleteLikeDto {

    private String userUuid;
    private String videoId;
}
