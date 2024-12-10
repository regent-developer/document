# BeautifulSoup标签闭合


```python
from bs4 import BeautifulSoup

html = """
<div>
    <p>a
    <p>b
    <p>c
</div>
    """

soup = BeautifulSoup(html, "lxml")
print(soup.prettify())
```

