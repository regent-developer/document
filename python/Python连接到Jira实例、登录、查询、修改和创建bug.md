# Python连接到Jira实例、登录、查询、修改和创建bug

## 安装jira模块
```shell
pip install jira
```

## 连接到Jira并登录
```python

from jira import JIRA
from jira.exceptions import JIRAError

# Jira服务器地址，用户名和密码
jira_server = 'https://your-jira-server.com'
jira_user = 'your-username'
jira_password = 'your-password'

# 创建Jira实例并登录
try:
    jira_instance = JIRA(server=jira_server, basic_auth=(jira_user, jira_password))
    print("Logged in successfully.")
except JIRAError as e:
    print(f"Failed to log in to Jira: {e}")

```

## 查询Bug
```python
# 使用JQL查询Bug
def search_bugs(jql_query):
    try:
        issues = jira_instance.search_issues(jql_query)
        for issue in issues:
            print(f"Issue Key: {issue.key}, Summary: {issue.fields.summary}")
    except JIRAError as e:
        print(f"Error searching issues: {e}")

# 示例JQL查询，查询所有状态为"To Do"的bug
jql_query = "issuetype = Bug AND status = 'To Do'"
search_bugs(jql_query)

```

## 修改Bug
```python
# 修改指定的bug
def update_bug(issue_key, **fields):
    try:
        issue = jira_instance.issue(issue_key)
        jira_instance.transition_issue(issue.key, fields)
        print(f"Updated issue {issue_key}")
    except JIRAError as e:
        print(f"Error updating issue {issue_key}: {e}")

# 假设要将bug设置为"In Progress"
issue_key = 'BUG-123'
update_bug(issue_key, fields={'status': {'id': '2'}})  # 假设"In Progress"状态的ID为"2"
```

## 创建Bug
```python
# 创建一个新的bug
def create_bug(project_key, summary, description):
    try:
        new_issue = jira_instance.create_issue(
            project=project_key,
            summary=summary,
            description=description,
            issuetype={'name': 'Bug'}
        )
        print(f"Created new bug: {new_issue.key}")
    except JIRAError as e:
        print(f"Error creating bug: {e}")

# 创建一个新的bug示例
project_key = 'PROJ'  # 项目key
summary = 'New Bug Summary'
description = 'Description of the new bug.'
create_bug(project_key, summary, description)

```