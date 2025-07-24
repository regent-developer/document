# Harbor
Harbor 是一个由 CNCF（Cloud Native Computing Foundation）孵化并托管的企业级云原生镜像仓库，用于存储、管理和分发容器镜像与 Helm Chart。
它在 Docker Registry 的基础上进行了增强，增加了身份认证、访问控制、安全扫描、镜像签名、复制策略等功能。
Harbor 支持多种身份集成方式（如 LDAP、OIDC），并且可以通过镜像复制在多地同步镜像，非常适合部署在私有云、本地数据中心或边缘计算环境中。
Harbor 不仅是一个容器镜像仓库，更是 DevOps 流水线中不可或缺的基础设施组件。

## 以下是 Harbor 在 DevOps 各阶段中的常见使用场景：
持续集成（CI）阶段
Gitea Actions / Jenkins 等工具在完成镜像构建后，自动将镜像推送至 Harbor。

结合镜像签名进行合规验证
使用 Notary 为镜像签名，防止被篡改。

持续部署（CD）阶段
Kubernetes 集群直接从 Harbor 拉取镜像，用作部署或滚动升级。

多地部署与镜像复制
主数据中心部署一个 Harbor 主仓库，边缘节点或 DR 站点通过镜像复制进行同步。

镜像自动清理
支持配置保留策略，设置保留最新 N 个版本或最近使用的镜像，避免仓库存储空间无限膨胀，提高资源利用率。