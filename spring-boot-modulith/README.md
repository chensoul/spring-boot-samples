# spring-boot-modulith


架构发展过程：
- MVC
- DDD
- 六边形架构
- 洋葱架构 https://github.com/hirannor/springboot-hexagonal-ddd
  - 围绕独立的对象模型构建应用 
  - 基础设施 -> 应用服务 -> 领域服务 -> 领域模型。外层依赖内层，内层对外层无感知。
  - 内层定义接口，外层实现接口。基础设施实现接口，通过依赖注入的方式注入到其他层。
  - 所有的应用代码可以独立于基础设施编译和运行
- 微服务
- 整洁架构 https://medium.com/@viniciusromualdobusiness/clean-architecture-with-spring-boot-a-good-idea-d6f97e450130
  - 接口适配层 -> 用例层 -> 实体层
 

分层：
- 展示层：controller、cli、job
- 应用层：application
- 领域层：domain
- 持久层：persistence
- 基础设施：infrastructure/adapter

- 
- 架构设计关键原则

- 思考什么最适合您的软件，而不是盲目听从大众的建议。
- 努力使事情保持简单，而不是通过猜测未来十年的需求来过度设计解决方案。
- 进行研发，选择一项技术并接受它，而不是考虑可替代性来创建抽象。
- 确保您的解决方案作为一个整体发挥作用，而不是仅仅个别单元发挥作用。

实施指南：

- 按功能打包
- 保持“应用程序核心”独立于交付机制（Web、调度程序作业、CLI）
- 将业务逻辑执行与输入源（Web 控制器、消息监听器、计划作业等）分离
- 不要让“外部服务集成”过多地影响“应用程序核心”
- 将领域逻辑保留在领域对象中
- 没有不必要的接口
- 拥抱框架的强大和灵活性。不利用所选框架提供的强大功能和灵活性，而是在所选框架之上创建间接或抽象，希望有一天可以将框架切换到不同的框架，这通常是一个非常糟糕的主意。
- 不仅要测试单元，还要测试整个功能

## References

- https://stibel.icu/md/method/arch-principle/arch-principle-arch-develop.html
- https://github.com/hirannor/springboot-hexagonal-ddd
- https://github.com/C0de-cake/ecommerce-app/tree/main/apps/ecom-backend
- https://github.com/sewah2012/hexagonal-architecture-demo-youtube/