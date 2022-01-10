package xyz.hjtech.myyoutubeproject.youtube.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import xyz.hjtech.myyoutubeproject.youtube.model.YoutubeEntity;

import java.util.List;

public interface YoutubeRepository extends JpaRepository<YoutubeEntity, Long> {


    List<YoutubeEntity> findByTitleContains(String searchTxt);

    List<YoutubeEntity> findByDescriptionContains(String searchTxt);

    List<YoutubeEntity> findBySearchTxt(String searchTxt);

    @Query(nativeQuery = true, value = "SELECT * FROM YoutubeEntity as y WHERE y.videoId IN (:videoIds)")
    List<YoutubeEntity> findByVideoIds(@Param("videoIds") List<String> ids);

    YoutubeEntity findByVideoId(String videoId);
}
