# Spring Cloud

## 分布式、集群、微服务、SOA

- 分布式：不同模块部署在不同服务器上，作用：分布式解决网站高并发带来问题。（一个业务分拆多个子业务，部署在不同的服务器上）
- 集群：多台服务器部署相同应用构成一个集群，作用：通过负载均衡设备共同对外提供服务。（同一个业务，部署在多个服务器上）
- SOA：业务系统分解为多个组件，让每个组件都独立提供离散，自治，可复用的服务能力，通过服务的组合和编排来实现上层的业务流程，作用：简化维护,降低整体风险,伸缩灵活。
- 微服务：架构设计概念,各服务间隔离（分布式也是隔离）,自治（分布式依赖整体组合）其它特性(单一职责,边界,异步通信,独立部署)是分布式概念的跟严格执行 SOA 到微服务架构的演进过程，作用：各服务可独立应用，组合服务也可系统应用。

## CAP 理论

- C：数据一致性(consistency)，所有节点拥有数据的最新版本。
- A：可用性(availability)，数据具备高可用性。
- P：分区容错性(partition-tolerance)，容忍网络出现分区，分区之间网络不可达。

## Spring Cloud

### SpringCloud 的基础功能

- 服务治理： Spring Cloud Eureka
- 客户端负载均衡： Spring Cloud Ribbon
- 服务容错保护： Spring Cloud Hystrix
- 声明式服务调用： Spring Cloud Feign
- API 网关服务：Spring Cloud Zuul
- 分布式配置中心： Spring Cloud Config

### SpringCloud 的高级功能

- 消息总线： Spring Cloud Bus
- 消息驱动的微服务： Spring Cloud Stream
- 分布式服务跟踪： Spring Cloud Sleuth
