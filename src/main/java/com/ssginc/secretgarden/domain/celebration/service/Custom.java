package com.ssginc.secretgarden.domain.celebration.service;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Custom {
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
}
