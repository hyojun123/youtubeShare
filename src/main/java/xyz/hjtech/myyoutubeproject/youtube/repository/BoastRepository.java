package xyz.hjtech.myyoutubeproject.youtube.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.hjtech.myyoutubeproject.youtube.model.BoastEntity;

import java.util.List;

public interface BoastRepository extends JpaRepository<BoastEntity, Long> {


    List<BoastEntity> findAllByOrderByLikeCntDescModTspDesc();

    List<BoastEntity> findByTitleContains(String searchTxt);

    BoastEntity findByUserUuid(String userUuid);
}
