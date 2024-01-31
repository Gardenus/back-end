package com.ssginc.secretgarden.domain.celebration.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class Custom {

    private static String staticApiKey;

    @Value("${openai.api.key}")
    private String apiKey;

    @PostConstruct
    public void init(){
        staticApiKey = apiKey;
    }

    // 익명 닉네임 랜덤 생성 메서드
    public static String createRandomNickname() {
        List<String> nick = Arrays.asList(
                "기분좋은", "행복한", "즐거운", "멋진", "아름다운",
                "빛나는", "활기찬", "따뜻한", "매력적인", "상쾌한",
                "우아한", "평화로운", "기쁜", "환상적인", "감동적인",
                "밝은", "희망찬", "달콤한", "사랑스러운", "신나는",
                "안정된", "만족스러운", "편안한", "활발한", "용기있는",
                "창의적인", "능력있는", "성실한", "정직한", "친절한"
        );
        List<String> name = Arrays.asList(
                "코스모스", "장미", "튤립", "라일락", "해바라기",
                "수국", "데이지", "카네이션", "벚꽃", "라벤더",
                "금잔화", "아네모네", "팬지", "프리지아", "난초",
                "백합", "아마릴리스", "철쭉", "연꽃", "진달래",
                "동백", "매화", "국화", "히야신스", "목련",
                "포인세티아", "피튜니아", "거베라", "아이리스", "자스민"
        );
        Collections.shuffle(nick); Collections.shuffle(name);
        return nick.get(0) + " " + name.get(0);
    }

    public static String createImageByGPT(String category, String content) throws IOException {

        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(30, TimeUnit.SECONDS) // 연결 타임아웃 30초
                .readTimeout(30, TimeUnit.SECONDS)    // 읽기 타임아웃 30초
                .writeTimeout(30, TimeUnit.SECONDS)   // 쓰기 타임아웃 30초
                .build();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = null;
        if (category.equals("anniversary")){
            body = RequestBody.Companion.create("{\"model\": \"dall-e-3\", \"prompt\": \" Create an image of cute animal wishing me a happy birthday while setting a garden with colorful flowers in the background.\", \"n\": 1, \"size\": \"1024x1024\"}", mediaType);
        } else if (category.equals("daily")){
            // String prompt = "Create an image of cute animal celebrating that " + content + ".";
            String prompt = content + "알록다록 꽃들이 펼쳐진 정원에서 앞의 내용을 축하해주는 귀여운 동물 이미지를 생성해줘.";
            body = RequestBody.Companion.create("{\"model\": \"dall-e-3\", \"prompt\": \"" + prompt + "\", \"n\": 1, \"size\": \"1024x1024\"}", mediaType);
        }
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/images/generations")
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + staticApiKey)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.body() != null) {
                String responseBody = response.body().string();
                System.out.println(responseBody);

                // JSON 파싱을 위한 ObjectMapper 객체 생성
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(responseBody);

                // "url" 필드 값을 추출하여 return
                return jsonNode.at("/data/0/url").asText();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; // 예외가 발생하거나 응답 본문이 null일 경우 null 반환
    }
}
