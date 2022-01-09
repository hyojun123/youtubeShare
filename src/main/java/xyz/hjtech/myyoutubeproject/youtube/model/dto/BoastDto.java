package xyz.hjtech.myyoutubeproject.youtube.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.hjtech.myyoutubeproject.youtube.model.BoastEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoastDto {

    private Long id;
    private Integer viewCnt;
    private Integer likeCnt;
    private String userUuid;
    private String title;
    private Integer cnt;

    public BoastDto(BoastEntity entity) {
        this.id = entity.getId();
        this.viewCnt = entity.getViewCnt();
        this.likeCnt = entity.getLikeCnt();
        this.userUuid = entity.getUserUuid();
        this.title = entity.getTitle();
        this.cnt = entity.getBoastList().size();
    }
}
