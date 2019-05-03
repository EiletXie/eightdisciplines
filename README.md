# eightdisciplines
新8D系统环境搭建

该系统开发的原因，公司制度要求客服部门一天之内确认客诉，但实际一天是很难确认清楚客诉，所以经常找8D负责人修改客诉单，平均每天影响半小时，而且是分布式的总共半小时，很影响工作效率。

所以为了修改的客诉信息可追溯性，以及减轻自己的维护时间成本，我决定开发一个专门的修改系统

项目源码在我的GitHub上 https://github.com/GrasManXC/eightdisciplines

项目技术背景： JDK1.8 + SpringBoot2.13 + ojbc7 + oracle10.1.0.1版本

前后端框架： Bootstrap + jquery + Mybatis + thymeleaf


以jar包的形式发布在 linux服务器上运行

由于未花时间做兼容性的操作，该修改系统只在google浏览器和火狐浏览器，别的浏览器容易出现js不兼容的问题。



登录页面的用户数据和原有的8D系统是一致的，密码也是用MD5字符加密过后的32位匹配



用户登录后通过car号查询，然后选择修改或者删除客诉





后续由于客服还想修改缺陷描述和责任工序（内联子对象），还需进行完善
在关于表单中包含两个内联对象数组的展示，增删以及统一传入后台，让我对json有了一定的理解。




所有的用户操作都会记录下来，用于日后的数据分析和追溯某CAR号问题的根据，用户可以查询指定日期和car号的相关修改数据







总结：
开发这个系统本意是想着让客服部门和我之间，双方都更加方便点，确实上线后自己系统维护时间大大降低了，双方都比较满意。

没想到的是开发过程中学的了不少东西，由于系统代码不多，更多是思考了 开发系统时 性能方面的考虑，使用关联查询，代码规范性的问题，

对于XML中的SQL书写规范和MVC3层之间的多个表之间调用的问题，这些都是特别注意的。



推荐后来的开发者好好看看  《阿里巴巴JAVA开发手册》，我是在边写改系统的时候边看的这本书，所以发现了自己代码书写的很多问题，后续为了规范将系统进行了部分重构工作。
