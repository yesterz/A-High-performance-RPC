# Introduction
学习Netty后做的一个基于Netty、Zookeeper、Spring的轻量级RPC框架。

1. 采用Netty来实现简单服务器
2. Netty Client 和服务器进行通信
3. 用Netty构建RPC服务器（加上自己的简单通信协议）
4. Netty Client 长连接与RPC服务器通信
5. 加上动态代理 Spring Cglib
6. 然后需要把业务模块与通信底层模块分离
7. 分布式RPC实现（注册服务器到Zookeeper中去）
8. 客户端需要从Zookeeper中获取唯一服务器列表
9. 构建和管理通信链路

# Features
* 支持长连接
* 支持异步调用
* 支持心跳检测
* 支持JSON序列化
* 接近零配置，基于注解实现调用
* 基于Zookeeper实现服务器注册中心
* 支持客户端连接动态管理
* 支持客户端服务监听、发现功能
* 支持服务端服务注册功能
* 基于Netty4.x版本实现

# Quick Start