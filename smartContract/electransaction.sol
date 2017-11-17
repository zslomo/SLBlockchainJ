pragma solidity ^0.4.0;

/*
*  以太坊智能合约，能源交易，购买人发起购买请求，有剩余电量的卖家可以选择卖出多少电量
*  请求满足后买家付款，卖家发送电力
*  @Auther：bintan
*  @CreateDate：2017.10.28
*/


contract electransaction {

    enum Stages {
    New,
    Collecting,
    Paying,
    Finished
    }

    uint public creationTime = now;

    Stages public stage = Stages.New;

    modifier atStage(Stages _stage) {
        require(stage == _stage);
        _;
    }

    function nextStage() internal {
        stage = Stages(uint(stage) + 1);
    }

    function endStage() internal {
        stage = Stages.Finished;
    }


    // 该修饰符在功能完成后进入下一个阶段。
    modifier transitionNext()
    {
        _;
        nextStage();
    }

    event TokenTransfer(address backer, uint amount);
    
    address public buyer; //买家
    uint public electGoal;//购买电力数
    uint public  electRaised;//已确定的卖家卖出店里总和（筹集的电力数）
    uint public electPrice;//买家出价(单价)
    uint public electNumSeller;
    bytes public electType;
    

    

    /* 卖家的数据结构 */
    struct Seller {
    address addr;
    uint elecAmount;
    }

    Seller[] public sellers;//卖家集合
   
   
    function  ReleaseElectransaction(uint _electGoal, uint _electPrice, bytes _type )
    atStage(Stages.New)
    {
        
            buyer = msg.sender;
            electGoal = _electGoal;
            electRaised = 0;
            electPrice = _electPrice;
            electNumSeller = 0;
            electType = _type;
        
        
        nextStage();
    }
    //TODO 需要获得请求买电人的账户总额
    // function checkAccount(uint _account)
    // atStage(Stages.New)
    // {
    //     if (_account > releaseBuyerMap[msg.sender].electPrice * releaseBuyerMap[msg.sender].electGoal)
    //     nextStage();
    //     else
    //     endStage();

    // }

    
    function sell(uint _elects)
    public
    payable
    atStage(Stages.Collecting)
    {
        sellers.push(Seller({
            addr : msg.sender, 
            elecAmount : _elects
        })) ;
        electRaised += _elects;
        if (electRaised >= electGoal)
        nextStage();
    }

    /* 检查目标是否达到，达到就结束 */
    function checkGoalReached()
    payable
    atStage(Stages.Paying)
    {
        if (electRaised >= electGoal) {
            //buyer.transfer(electRaised);
            //TokenTransfer(buyer, electRaised);
            nextStage();
        }
        
    }
    /*买家付钱*/
    function pay()
    payable
    atStage(Stages.Paying)
    {

        if (msg.sender == buyer) {

            for (uint i = 0; i < sellers.length; ++i) {
                //sellers[i].addr.transfer(sellers[i].elecAmount * electPrice);
            }

        }
        checkGoalReached();

    }

    function clean()
    atStage(Stages.Finished) {

    }

    struct Product {
    address seller;    //卖家
    uint electAccount; //电量
    bytes electTpye;   //类型
    bytes detail;     //详情
    uint unitPrice;  //电价
    }
    
    uint80 constant None = uint80(0); 

    mapping(address => uint) sellerPublish;

    Product[] products;
    // notice One person publish multiple transactions is not support yet
    function electPublish(uint _electAccount, bytes _elecTpye, bytes _detail, uint _uintPrice) {
        products.push(Product({
            seller : msg.sender,
            electAccount : _electAccount,
            electTpye : _elecTpye,
            detail : _detail,
            unitPrice : _uintPrice
        }));
    
        sellerPublish[msg.sender] = products.length - 1;
    }

    function buy(address _seller,uint _buyElectAccount) public payable returns (uint){
        if (products[sellerPublish[_seller]].electAccount < _buyElectAccount) throw;
        products[sellerPublish[_seller]].electAccount -= _buyElectAccount;
        
        uint sellerPaid = products[sellerPublish[_seller]].unitPrice * _buyElectAccount;
        products[sellerPublish[_seller]].seller.transfer(sellerPaid);
        
        return products[sellerPublish[_seller]].electAccount;
    }
    function getProductsLength() public returns (uint){
        return products.length;
    }
    function getProducts(uint index) public returns (address,uint,bytes,bytes,uint) {
        return (products[index].seller,products[index].electAccount,products[index].electTpye,products[index].detail,products[index].unitPrice);
    }
    function deleteProduct() returns(bool) {
        if(sellerPublish[msg.sender] == None) throw;
        delete products[sellerPublish[msg.sender]];
        return true;
    }
    
}
