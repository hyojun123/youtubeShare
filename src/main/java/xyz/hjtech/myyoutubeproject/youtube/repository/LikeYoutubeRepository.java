package xyz.hjtech.myyoutubeproject.youtube.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.hjtech.myyoutubeproject.youtube.model.LikeYoutubeEntity;

public interface LikeYoutubeRepository extends JpaRepository<LikeYoutubeEntity, Long> {

    LikeYoutubeEntity findByUserUuid(String userUuid);
}
