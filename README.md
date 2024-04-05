## Commit
> 커밋 메시지 작성 시 마지막에 `#이슈번호` 붙이기
- Feat: 실습, 과제 코드 파일이나 부수적인 코드 추가
- Fix: 코드 및 내부 파일 수정
- Style: 문법 오류 해결, 타입 변경, 이름 변경 등의 코드 자체의 변경이 없는 작은 수정
- Del: 쓸모 없는 코드 혹은 파일 삭제
- Move: 프로젝트 파일 및 코드 이동
- Rename: 파일 이름 변경
- Merge: 다른 브랜치와의 충돌 해결 후 Merge
- Refactor: 전면 수정
- Docs: README.md 등 필기한 마크다운 파일 수정 및 등록

## Branch
- `main` : 배포 시 사용
- `dev` : 개발 시 기본 브랜치
- `기능_유형/이슈번호` : 기능 개발
  - ex) login_feat/#1

## Issues
- `[Type] 기능 설명`
  - ex) [FEAT] 로그인 기능 구현
- Assignees : assign yourself
- Labels : enhancement
- 기능 개발이 완료된 이슈 닫기

## PR
- `[Type] 기능 설명`
  - ex) [FEAT] 로그인 기능 구현
- Assignees : assign yourself
- Development : Link an issue
- 기능 개발이 완료된 브랜치 삭제
