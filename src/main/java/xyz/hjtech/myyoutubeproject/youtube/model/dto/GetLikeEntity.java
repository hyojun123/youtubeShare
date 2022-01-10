package xyz.hjtech.myyoutubeproject.youtube.model.dto;

import lombok.Data;
import xyz.hjtech.myyoutubeproject.youtube.model.LikeYoutubeEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class GetLikeEntity {

    private Long id;
    private String userUuid;
    private List<GetYoutubeDto> youtubeList;

    public GetLikeEntity(LikeYoutubeEntity entity) {
        this.id = entity.getId();
        this.userUuid = entity.getUserUuid();
        this.youtubeList = entity.getYoutubeList().stream().map(t -> {
            return new GetYoutubeDto(t, true);
        }).collect(Collectors.toList());
    }

    public GetLikeEntity(String userUuid) {
        this.userUuid = userUuid;
        this.youtubeList = new ArrayList<>();
    }
}
