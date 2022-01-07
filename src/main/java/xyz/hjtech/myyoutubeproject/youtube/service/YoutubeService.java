package xyz.hjtech.myyoutubeproject.youtube.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import xyz.hjtech.myyoutubeproject.util.DeDuplicationUtils;
import xyz.hjtech.myyoutubeproject.youtube.model.YoutubeEntity;
import xyz.hjtech.myyoutubeproject.youtube.repository.YoutubeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class YoutubeService {
    private final YoutubeRepository youtubeRepository;
    private final static int MIN_SIZE = 50;
    private final static int MAX_SIZE = 50;

    public List<YoutubeEntity> findBySearchTxt(String searchTxt) {
        List<YoutubeEntity> findByTitleList = youtubeRepository.findByTitleContains(searchTxt);
        List<YoutubeEntity> findByDescriptionList = youtubeRepository.findByDescriptionContains(searchTxt);
        List<YoutubeEntity> findBySearchTxtList = youtubeRepository.findBySearchTxt(searchTxt);

        List<YoutubeEntity> mergedList = new ArrayList<>(findByTitleList);
        mergedList.addAll(findByDescriptionList);
        mergedList.addAll(findBySearchTxtList);

        List<YoutubeEntity> list = DeDuplicationUtils.DeDuplication(mergedList, YoutubeEntity::getVideoId);

        if(list.size() >= MIN_SIZE) {
            return list;
        }

        List<YoutubeEntity> searchList = findBySearchTxtByGoogleApi(searchTxt);
        youtubeRepository.saveAll(searchList);
        return searchList;
    }

    public List<YoutubeEntity> findByVideoIds(List<String> ids) {
        if(ids.size() != 0) {

            return DeDuplicationUtils.DeDuplication(youtubeRepository.findByVideoIds(ids), YoutubeEntity::getVideoId);
        }

        return new ArrayList<>();
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

            result.add(new YoutubeEntity(videoId, searchTxt, publishedAt, channelId, title, description, thumbnailsUrl, width, height, channelTitle, publishTime));
        });
        return result;
    }


}
