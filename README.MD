## 项目介绍
----------
**这是一个基于区块链的操作网站后台，简单的三层架构，前端通过http发送JSON请求访问后台，后台也通过http发送JSON轻请求访问以太坊go客户端**
- 暂时没有找到一个很好的简单、免费、开源的区块链实践平台，所以准备搞一个
- 服务器在阿里云，空间带宽有限，区块链暂时不支持远端链接

### 暂时实现api
----------
- 网站用户：用户创建、访问，缺少访问控制
- 以太坊账户：以太坊账户创建、访问，按用户查询账户
- 区块：查看区块、查询区块数量
- 交易：按地址查询交易，按私钥查询交易，交易发送（自动挖矿写入），交易发送（无挖矿）
- 挖矿：启动、结束
- 监听器：设置、销毁、区块监听、交易监听
### 智能合约
--------------
- 以太坊，能源交易，solidity语言编写，详见代码
### 以太坊提供多种方式操作区块链
-------
- 最流行的是web3.js 是一个封装了JSON RPC的JavaScript库： [web3.js](https://github.com/ethereum/web3.js/)
- 其次是JSON-RPC的原始接口：[Ethereum JSON-RPC](https://github.com/ethereum/wiki/wiki/JSON-RPC)
- 也有用java封装的库 ：[web3J](https://github.com/web3j/web3j)

### 以太坊的有用相关网址
-------
- 以太坊的github [repositorys](https://github.com/ethereum/)
- 以太坊最火的client包含geth控制台，学习以太坊必备 [go-ethereum](https://github.com/ethereum/go-ethereum)
- 以太坊白皮书，关于以太坊技术、理念等的简单阐述 [白皮书](https://github.com/ethereum/wiki/wiki/White-Paper)
- 以太坊黄皮书，阐述以太坊的论文，算法、概念详细公式都在这，[黄皮书](https://ethereum.github.io/yellowpaper/paper.pdf)

### 作者联系方式
---------
**邮箱：bintan@fudan.edu.cn**
