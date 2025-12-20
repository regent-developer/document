# LightRAG


使用“轻量”作为解决GraphRAG的痛点的方式，从而降低了计算成本并加快了处理速度。其架构核心是基于图的文本索引和双层检索范式。

## 特点

- 基于图的文本索引

* 将文档分割成更小、更易于管理的部分，无需分析整个文档即可快速识别和访问相关信息
* 利用 LLM 识别和提取各种实体及其之间的关系，以键值对形式进行输出用于高效检索
* 识别并合并重复的实体和关系，缩减图形大小和图形操作开销
* 增量更新时按照同样流程构建小图，在对节点集进行并集操作，避免重新构建图谱

- 双层检索范式

* 低级检索：侧重与检索特定实体及其相关的的属性或关系，回答细节性主题
* 高级检索：聚合多个相关实体和关系信息，回答更广泛或抽象的主题

- 在检索时会融合图谱与向量，实现高效检索：

* 借助LLM从查询中提取出局部关键字和全局关键字
* 使用向量数据库将局部关键词与候选实体进行匹配，将全局关键词与链接到全局键的关系进行匹配
* 收集局部子图中的单跳相邻节点，最终形成包含实体、关系、原始文本的上下文

## 安装
```
pip install lightrag-hku
```

## 启动

```python
import os
import asyncio

from lightrag import LightRAG
from lightrag.llm.openai import gpt_4o_mini_complete, openai_embed
from lightrag.kg.shared_storage import initialize_pipeline_status

WORKING_DIR = "./dickens"

asyncdef initialize_rag():
    rag = LightRAG(
        # 指定工作目录，存储KV数据、向量数据、图数据等
        working_dir=WORKING_DIR,
        #  指定LLM函数实现，可以使用OpenAI兼容接口
        llm_model_func=gpt_4o_mini_complete,
        # 指定嵌入函数实现，可以使用本地嵌入模型
        embedding_func=openai_embed,
    )

    await rag.initialize_storages()
    await initialize_pipeline_status()

    return rag


asyncdef main():
    rag = None
    try:
        rag = await initialize_rag()

        # 向rag导入新文档
        with open("./book.txt", "r", encoding="utf-8") as f:
            await rag.ainsert(f.read())

        print('请在下方“>”处输入查询问题，输入“exit”则退出系统')

        whileTrue:
            query = input("> ")
            # 向rag检索内容
            response = await rag.aquery(query)
            print(response)

    except Exception as e:
        print(f"An error occurred: {e}")
    finally:
        print('Bey~')
        if rag:
            await rag.finalize_storages()


if __name__ == "__main__":
    asyncio.run(main())
```

## 检索
LightRAG 支持多种检索模式：

* naive：传统的向量相似度检索
* local：利用局部关键字进行检索，关注实体细节信息
* global：利用全局关键字进行检索，关注实体关系和全局知识
* hybrid：结合了 local 和 global 模式，检索实体和关系信息
* mix：结合了 hybrid和 naive模式
* bypass： 不进行任何检索，直接由LLM生成答案