package com.project.mything.survey.dto.surveyquestion;

import java.util.HashMap;
import lombok.AllArgsConstructor;
import lombok.ToString;

/**
 * packageName    : com.project.mything.survey.dto.surveyquestion fileName       : SurveyQuestion
 * author         : hagnoykmik date           : 2023-03-30 description    :
 * =========================================================== DATE              AUTHOR
 * NOTE ----------------------------------------------------------- 2023-03-30        hagnoykmik       최초
 * 생성
 */
@ToString
@AllArgsConstructor
public class SurveyQuestion {

  public HashMap<Integer, HashMap<String, String[]>> questions(){

    HashMap<Integer, HashMap<String, String[]>> questions = new HashMap<>();

    // 1번 - topNote
    HashMap<String, String[]> question1 = new HashMap<>();

    question1.put("q", new String[]{"여기는 어디지? 낯선 곳에서 깨어난 당신! 당신은 지금 어디에 있나요?"});
    question1.put("a", new String[] {"아름다운 꽃들이 한가득!","정원의 한가운데"});
    question1.put("b", new String[] {"에퉤퉤!! 파도가 잔잔한","바닷가 모래사장"});
    question1.put("c", new String[] {"울창한 나무가 가득한","숲 속"});
    question1.put("d", new String[] {"실례하겠습니다.. ?","별장 안"});

    questions.put(1, question1);

    // 2번 - middleNote
    HashMap<String, String[]> question2 = new HashMap<>();

    question2.put("q", new String[]{"이런 곳에도 집이 있다니! 인테리어가 마음에 쏙 드는군. 어떤 인테리어 인가요?"});
    question2.put("a", new String[]{"화려함의 극치,", "프렌치 인테리어"});
    question2.put("b", new String[]{"정말 깔끔한데? 신축인가??", "화이트 인테리어"});
    question2.put("c", new String[]{"일곱난장이가 사는 집같아~", "내추럴 인테리어"});
    question2.put("d", new String[]{"보기만 해도 노곤노곤", "코지 인테리어"});

    questions.put(2, question2);

    // 3번 - baseNote
    HashMap<String, String[]> question3 = new HashMap<>();

    question3.put("q", new String[]{"거울에 비친 내모습(베이스): 집안의 거울을 보다 놀란 당신! 당신의 옷차림은? (당신은 여자입니다..)"});
    question3.put("a", new String[]{"귀족 영애 아갓씨", "로판 여주인공 룩"});
    question3.put("b", new String[]{"덥다 더워 !","흰티에 청반바지"});
    question3.put("c", new String[]{"미니멀의 극 치!", "상수룩"});
    question3.put("d", new String[]{"편안한게 최고","스웻셔츠와 조거 팬츠"});

    questions.put(3, question3);

    // 4번 - season
    HashMap<String, String[]> question4 = new HashMap<>();

    question4.put("q", new String[]{"당신이 좋아하는 계절 음식은?"});
    question4.put("a", new String[]{"까치 까치","떡국"});
    question4.put("b", new String[]{"초복, 중복, 말복","삼계탕"});
    question4.put("c", new String[]{"가을이 제철", "대하구이"});
    question4.put("d", new String[]{"슈붕? 팥붕?", "붕어빵"});

    questions.put(4, question4);
    
    return questions;
  }
}
