package xyz.hjtech.myyoutubeproject.youtube.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xyz.hjtech.myyoutubeproject.youtube.model.dto.PostBoastDto;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoastEntity {
    // 자랑하기
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userUuid;
    private String title;

    private Integer viewCnt;
    private Integer likeCnt;

    private Date regTsp;
    private Date modTsp;

    @OneToMany(fetch = FetchType.EAGER)
    private List<YoutubeEntity> boastList;

    public BoastEntity(PostBoastDto boastDto, List<YoutubeEntity> boastList) {
        this.userUuid = boastDto.getUserUuid();
        this.title = boastDto.getTitle();
        this.boastList = boastList;
        this.viewCnt = 0;
        this.likeCnt = 0;
        this.regTsp = new Date();
        this.modTsp = new Date();
    }
}
