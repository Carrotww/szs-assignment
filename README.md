## szs 지원자 유형석

## Swagger 사용 url
http://localhost:8080/swagger-ui/index.html

## 계산에 필요한 정보 정리

결정세액 -> 산출세액 필드만 필요
= 산출세액 - 근로소득세액공제금액 - 특별세액공제금액 - 표준세액공제금액 - 퇴직연금세액공제금액
---
근로소득세액공제금액
= 산출세액 * 0.55
---
특별세액공제금액
= 보험료공제금액 + 의료비공제금액 + 교육비공제금액 + 기부금공제금액
---
보험료공제금액 -> 소득공제의 보험료 필드
= 보험료납입금액 * 12%
---
의료비공제금액 -> 소득공제의 의료비 필드 (의료비 - 총지급액 * 3%) * 15%
= (의료비납입금액 - 총급여 * 3%) * 15%
단, 의료비공제금액이 음수일 경우 0원 처리
---
교육비공제금액 -> 소득공제의 교육비 필드
= 교육비납입금액 * 15%
---
기부금공제금액 -> 소득공제의 기부금 필드
= 기부금납입금액 * 15%
---
표준세액공제금액 -> 특별세액공제금액에 따름
특별세액공제금액의 합이 130,000원 미만이면 표준세액공제금액 130,000원 처리
특별세액공제금액의 합이 130,000원 이상이면 표준세액공제금액 0원 처리
단, 표준세액공제금액이 130,000원이면 특별세액공제금액 0원 처리
---
퇴직연금세액공제금액 -> 소득공제의 퇴직연금의 총 납임금액 필드
= 퇴직연금 납입금액 * 0.15