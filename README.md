#  项目名称：Java高并发之模拟产品秒杀以及优化系统 
## 项目技术：
MySQL:1.这里我们采用手写代码创建相关表，掌握这种能力对我们以后的项目二次上线会有很大的帮助；2.SQL技巧；3.事务和行级锁的理解和一些应用。

MyBatis:1.DAO层的设计与开发。2.MyBatis的合理使用，使用Mapper动态代理的方式进行数据库的访问。3.MyBatis和Spring框架的整合:如何高效的去整合MyBatis和Spring框架。

Spring:1.Spring IOC帮我们整合Service以及Service所有的依赖。2.声明式事务。对Spring声明式事务做一些分析以及它的行为分析。

Spring MVC:1.Restful接口设计和使用。Restful现在更多的被应用在一些互联网公司Web层接口的应用上。2.框架运作流程。3.Spring Controller的使用技巧。

前端:1.交互设计。2.bootstrap。3.JQuery。

高并发:1.高并发点和高并发分析。2.优化思路并实现。
## 开发工具： 
IntelliJ IDEA 2017.3.1 x64、5.5.49 MySQL Community Server、apache-tomcat-7.0.52、Windows10、Maven、Git、Navicat、JDK1.8

## 项目效果图
* 秒杀商品列表
![图片名称](https://github.com/MarsLK/seckill/blob/master/images/%E7%A7%92%E6%9D%80%E5%95%86%E5%93%81%E5%88%97%E8%A1%A8.png)

* 进入秒杀界面
![图片名称](https://github.com/MarsLK/seckill/blob/master/images/%E8%BF%9B%E5%85%A5%E7%A7%92%E6%9D%80%E7%95%8C%E9%9D%A2.png)

* 开始秒杀提示界面
![图片名称](https://github.com/MarsLK/seckill/blob/master/images/%E5%BC%80%E5%A7%8B%E7%A7%92%E6%9D%80%E6%8F%90%E7%A4%BA%E7%95%8C%E9%9D%A2.png)

* 秒杀成功提示界面
![图片名称](https://github.com/MarsLK/seckill/blob/master/images/%E7%A7%92%E6%9D%80%E7%A7%92%E6%9D%80%E6%88%90%E5%8A%9F%E6%8F%90%E7%A4%BA%E7%95%8C%E9%9D%A2.png)

* 重复秒杀提示界面
![图片名称](https://github.com/MarsLK/seckill/blob/master/images/%E9%87%8D%E5%A4%8D%E7%A7%92%E6%9D%80%E6%8F%90%E7%A4%BA%E7%95%8C%E9%9D%A2.png)

* 秒杀结束
![图片名称](https://github.com/MarsLK/seckill/blob/master/images/%E7%A7%92%E6%9D%80%E7%BB%93%E6%9D%9F.png)


## 项目描述： 
该项目的侧重点主要就是秒杀这个功能，并对其做了高并发方面的优化，后台个人独立搭建。其中系统后台基于SSM（Spring+SpringMVC+Mybatis）框架，前端基于Bootstrap框架，利用JavaScript模块化，主要完成模拟秒杀的三大功能，即订单详情页以及秒杀接口暴露和执行秒杀功能。对于实现基本功能的基础上再对系统进行优化。

## 项目优化： 
前端静态页面采用CDN缓存，实现动静态数据分离，秒杀按钮做防重复处理，在秒杀未开启的时候需要暴露秒杀地址的接口，使用redis进行缓存，在秒杀操作开启之后，利用行级锁在提交之后释放，减少行级锁持有时间，对于更新库存操作，把客户逻辑利用存储过程放到MySQL服务端，避免网络延迟和GC影响等。
## 项目地址：[Java高并发之模拟产品秒杀以及优化系统 ](https://github.com/MarsLK/seckill)    
