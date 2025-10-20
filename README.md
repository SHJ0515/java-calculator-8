# java-calculator-precourse

# 기능 요구 사항
입력한 문자열에서 숫자를 추출하여 더하는 계산기 구현

쉼표(,) 또는 콜론(:) 을 구분자로 가지는 문자열을 전달하는 경우 구분자를 기준으로 분리 후 각 숫자의 합 반환

    예시 : "" => 0, "1,2" => 3, "1,2,3" => 6, "1,2:3" => 6

"//" 와 "\n" 사이에 위치하는 문자를 커스텀 구분자로 추가 가능

    예시 :  "//;\n1;2;3" => 커스텀구분자 (;) 추가, => 6

잘못된 값을 입력할 경우 IllegalArgumentException 발생 후 애플리케이션 종료. 

# 입출력 요구 사항 
입력 : 구분자와 양수로 구성된 문자열

출력 : 덧셈 결과 => 결과 : 값 형식

    예시
    덧셈할 문자열을 입력해 주세요.
    1,2:3
    결과 : 6

# 프로그램 진행 순서
1. Application 시작 -> CalculatorController.run() 호출
2. 사용자 입력 진행 -> InputView의 Console.readLine()을 통해 입력
3. 계산 서비스 진행
4. 사용자 입력 parse 진행 -> 구분자 파트와 본문 파트로 String을 나눠서 ParsedInput 클래스로 관리
5. 구분자 파트 검증 -> 커스텀 구분자 존재 시 구분자로 추가
6. 본문 파트 검증 
7. 에러 없을 시 계산 수행 후 OutputView.printResult() 출력 후 프로그램 종료
8. 진행 과정 중 IllegalArgumentException 에러 발생 시 catch 후 OutputView.printError() 출력 후 프로그램 종료.

# 기능 구현 리스트

- 입력 기능 구현
  - camp.nextstep.edu.missionutils 에서 제공하는 Console API를 사용해 사용자 입력 처리하는 기능 구현
  - 입력 요구사항에 맞지 않는 입력이 들어올 시 IllegalArgumentException 처리
  - 구분자가 연달아 입력되는 경우 IllegalArgumentException 처리   
  

- 출력 기능 구현
  - 계산 결과를 출력하는 기능 구현
  

- 입력 내용 검증 
  

- 커스텀 구분자 추가 기능 구현
  - "//" 와 "\n" 패턴 안에 문자 1개가 아니라 여러개 입력 시 IllegalArgumentException 처리
  - 에러 없이 올바른 패턴으로 입력이 들어올 시 구분자 목록에 해당 문자 추가
  - 커스텀 구분자로 숫자를 요청할 시 IllegalArgumentException 처리
  - 커스텀 구분자 패턴이 입력 문자열의 맨 앞부분에 존재하지 않을 시 IllegalArgumentException 처리
  - 추가할 커스텀 구분자가 기본 구분자 (,) , (:) 와 겹칠 시 IllegalArgumentException 처리 
  

- 입력값 분류 기능 구현
  - 커스텀 구분자 지정 문자열이 있을 경우, 해당 부분과 실제 덧셈할 문자열 분류
  

- 계산 기능 구현
  - 기본 구분자 및 커스텀 구분자를 통해 올바른 사용자 입력에 대해서 덧셈 계산 기능 구현














