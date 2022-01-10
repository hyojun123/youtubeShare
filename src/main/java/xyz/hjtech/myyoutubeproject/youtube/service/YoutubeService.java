package xyz.hjtech.myyoutubeproject.youtube.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import xyz.hjtech.myyoutubeproject.util.DeDuplicationUtils;
import xyz.hjtech.myyoutubeproject.youtube.model.BoastEntity;
import xyz.hjtech.myyoutubeproject.youtube.model.LikeYoutubeEntity;
import xyz.hjtech.myyoutubeproject.youtube.model.YoutubeEntity;
import xyz.hjtech.myyoutubeproject.youtube.model.dto.*;
import xyz.hjtech.myyoutubeproject.youtube.repository.BoastRepository;
import xyz.hjtech.myyoutubeproject.youtube.repository.LikeYoutubeRepository;
import xyz.hjtech.myyoutubeproject.youtube.repository.YoutubeRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class YoutubeService {
    private final YoutubeRepository youtubeRepository;
    private final BoastRepository boastRepository;
    private final LikeYoutubeRepository likeYoutubeRepository;
    private final static int MIN_SIZE = 50;
    private final static int MAX_SIZE = 50;

    public List<GetYoutubeDto> findBySearchTxt(String searchTxt, String userUuid) {
        List<YoutubeEntity> findByTitleList = youtubeRepository.findByTitleContains(searchTxt);
        List<YoutubeEntity> findByDescriptionList = youtubeRepository.findByDescriptionContains(searchTxt);
        List<YoutubeEntity> findBySearchTxtList = youtubeRepository.findBySearchTxt(searchTxt);

        List<YoutubeEntity> mergedList = new ArrayList<>(findByTitleList);
        mergedList.addAll(findByDescriptionList);
        mergedList.addAll(findBySearchTxtList);

        List<YoutubeEntity> list = DeDuplicationUtils.DeDuplication(mergedList, YoutubeEntity::getVideoId);
        List<GetYoutubeDto> result = null;
        if(list.size() >= MIN_SIZE) {
            result = list.stream().map(t -> {
                return new GetYoutubeDto(t, false);
            }).collect(Collectors.toList());
            LikeYoutubeEntity likeYoutubeEntity = likeYoutubeRepository.findByUserUuid(userUuid);

            if(likeYoutubeEntity != null) {
                List<GetYoutubeDto> finalResult = result;
                likeYoutubeEntity.getYoutubeList().forEach(t -> {
                    finalResult.forEach(t2 -> {
                        if(t2.getVideoId().equals(t.getVideoId())) {
                           t2.setLike(true);
                        }
                    });
                });
            }
            return result;
        }

        List<YoutubeEntity> searchList = findBySearchTxtByGoogleApi(searchTxt);
        youtubeRepository.saveAll(searchList);
        result = searchList.stream().map(t -> {
            return new GetYoutubeDto(t, false);
        }).collect(Collectors.toList());
        return result;
    }

    // 구글api에서 가져오기
    private List<YoutubeEntity> findBySearchTxtByGoogleApi(String searchTxt) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://www.googleapis.com/youtube/v3/search?part=snippet&q=" + searchTxt + "&key=AIzaSyAL_WX8Q4b_4K1N8w_MAimL4GNLhakUDXI&maxResults=" + MAX_SIZE;
        Map<String, Object> obj = restTemplate.getForObject(url, Map.class);
        List<Map<String, Object>> items = (List<Map<String, Object>>) obj.get("items");
        List<YoutubeEntity> result = new ArrayList<>();
        items.forEach(t -> {
            Map<String, Object> id = (Map<String, Object>) t.get("id");
            Map<String, Object> snippet = (Map<String, Object>) t.get("snippet");
            Map<String, Object> thumbnails = (Map<String, Object>) snippet.get("thumbnails");
            Map<String, Object> high = (Map<String, Object>) thumbnails.get("high");
            String videoId = id.get("videoId") != null ? id.get("videoId").toString() : "";
            String publishedAt = snippet.get("publishedAt") != null ? snippet.get("publishedAt").toString().replace("T", " ").replace("Z", " ") : "";
            String channelId = snippet.get("channelId") != null ? snippet.get("channelId").toString() : "";
            String title = snippet.get("title") != null ? snippet.get("title").toString() : "";
            String description = snippet.get("description") != null ? snippet.get("description").toString() : "";
            String thumbnailsUrl = high.get("url") != null ? high.get("url").toString() : "";
            Integer width = high.get("width") != null ? Integer.parseInt(high.get("width").toString()) : 0;
            Integer height = high.get("height") != null ? Integer.parseInt(high.get("height").toString()) : 0;
            String channelTitle = snippet.get("channelTitle") != null ? snippet.get("channelTitle").toString() : "";
            String publishTime = snippet.get("publishTime") != null ? snippet.get("publishTime").toString().replace("T", " ").replace("Z", " ") : "";
            if(videoId == null || videoId.equals("")) {
                return;
            }
            if(youtubeRepository.findByVideoId(videoId) == null) {
                result.add(new YoutubeEntity(videoId, searchTxt, publishedAt, channelId, title, description, thumbnailsUrl, width, height, channelTitle, publishTime));
            }
        });
        return result;
    }


    @Transactional
    public BoastEntity postBoast(PostBoastDto boastDto) {
        BoastEntity boastEntity = null;
        List<YoutubeEntity> boastList = new ArrayList<>(youtubeRepository.findByVideoIds(Arrays.asList(boastDto.getItems().split(","))));
        List<YoutubeEntity> list = DeDuplicationUtils.DeDuplication(boastList, YoutubeEntity::getVideoId);
        if(boastRepository.findByUserUuid(boastDto.getUserUuid()) != null) {
            boastEntity = boastRepository.findByUserUuid(boastDto.getUserUuid());
            boastEntity.setTitle(boastDto.getTitle());
            boastEntity.setBoastList(list);
            boastEntity.setModTsp(new Date());
        } else {
            boastEntity = new BoastEntity(boastDto, boastList);

            boastRepository.save(boastEntity);
        }
        return boastEntity;
    }

    public List<BoastDto> findAllBoastList() {
        List<BoastEntity> entities = boastRepository.findAllByOrderByLikeCntDescModTspDesc();
        List<BoastDto> result = new ArrayList<>();
        entities.forEach(t -> {
            result.add(new BoastDto(t));
        });
        return result;
    }


    public List<BoastDto> findBoastBySearchTxt(String searchTxt) {
        List<BoastEntity> entities = boastRepository.findByTitleContains(searchTxt);
        List<BoastDto> result = new ArrayList<>();
        entities.forEach(t -> {
            result.add(new BoastDto(t));
        });
        return result;
    }

    @Transactional
    public BoastEntity findBoastById(Long id) {
        BoastEntity entity = boastRepository.findById(id).get();
        entity.setViewCnt(entity.getViewCnt() + 1);
        return entity;
    }

    public GetLikeEntity findByUserUuid(String userUuid) {
        LikeYoutubeEntity likeYoutubeEntity = likeYoutubeRepository.findByUserUuid(userUuid);

        if(likeYoutubeEntity != null) {
            return new GetLikeEntity(likeYoutubeEntity);
        } else {
            return new GetLikeEntity(userUuid);
        }

    }

    @Transactional
    public BoastEntity postLikeBoast(Long id) {
        BoastEntity entity = boastRepository.findById(id).get();
        entity.setLikeCnt(entity.getLikeCnt() + 1);

        return entity;
    }

    @Transactional
    public void deleteBoast(DeleteBoastDto boastDto) {
        boastRepository.deleteByUserUuid(boastDto.getUserUuid());
    }

    @Transactional
    public LikeYoutubeEntity postLikeVideo(PostLikeDto likeDto) {
        YoutubeEntity youtubeEntity = youtubeRepository.findByVideoId(likeDto.getVideoId());

        LikeYoutubeEntity likeYoutube = likeYoutubeRepository.findByUserUuid(likeDto.getUserUuid());
        LikeYoutubeEntity likeYoutubeEntity = null;

        if(likeYoutube == null) {
            List<YoutubeEntity> youtubeEntities = new ArrayList<>();
            youtubeEntities.add(youtubeEntity);
            likeYoutubeEntity = new LikeYoutubeEntity();
            likeYoutubeEntity.setUserUuid(likeDto.getUserUuid());
            likeYoutubeEntity.setYoutubeList(youtubeEntities);

            likeYoutubeRepository.save(likeYoutubeEntity);
        } else {
            likeYoutube.getYoutubeList().add(youtubeEntity);
        }

        return likeYoutubeEntity;
    }

    @Transactional
    public void deleteLikeVideo(DeleteLikeDto deleteLikeDto) {
        YoutubeEntity youtubeEntity = youtubeRepository.findByVideoId(deleteLikeDto.getVideoId());
        LikeYoutubeEntity likeYoutube = likeYoutubeRepository.findByUserUuid(deleteLikeDto.getUserUuid());

        likeYoutube.getYoutubeList().remove(youtubeEntity);
    }

    public BoastEntity findMyBoast(String userUuid) {
        return boastRepository.findByUserUuid(userUuid);
    }
}
