package xyz.hjtech.myyoutubeproject.youtube.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(indexes = @Index(columnList = "userUuid"))
public class LikeYoutubeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userUuid;

    @OneToMany(fetch = FetchType.EAGER)
    private List<YoutubeEntity> youtubeList;

    private Date regTsp = new Date();

}
