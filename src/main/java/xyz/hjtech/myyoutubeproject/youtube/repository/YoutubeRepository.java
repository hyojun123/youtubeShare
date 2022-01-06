package xyz.hjtech.myyoutubeproject.youtube.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.hjtech.myyoutubeproject.youtube.model.YoutubeEntity;

import java.util.List;

public interface YoutubeRepository extends JpaRepository<YoutubeEntity, Long> {


    List<YoutubeEntity> findByTitleContains(String searchTxt);

    List<YoutubeEntity> findByDescriptionContains(String searchTxt);

    List<YoutubeEntity> findBySearchTxt(String searchTxt);
}
