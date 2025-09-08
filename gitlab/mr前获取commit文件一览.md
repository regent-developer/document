```yml

stages:          
  - test

unit-test-job:   
  stage: test  
  image: node:18

  script:
    - git fetch origin $CI_MERGE_REQUEST_TARGET_BRANCH_NAME:$CI_MERGE_REQUEST_TARGET_BRANCH_NAME
    - git fetch origin $CI_MERGE_REQUEST_SOURCE_BRANCH_NAME:$CI_MERGE_REQUEST_SOURCE_BRANCH_NAME

    - changed_dirs=$(git log --name-only --no-abbrev-commit --pretty=format:"" $CI_MERGE_REQUEST_TARGET_BRANCH_NAME..$CI_MERGE_REQUEST_SOURCE_BRANCH_NAME | awk '/\.mjs$/'  | xargs -I {} dirname $(realpath {}) | sort -u)
    - for dir in $changed_dirs; do
    -   if [ -d "$dir" ]; then
    -     echo "$dir"
    -   fi
    - done 
  rules:
    - if: $CI_PIPELINE_SOURCE == 'merge_request_event'
```
