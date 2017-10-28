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

    modifier timedTransitions() {
        if (stage == Stages.Collecting &&
        now >= creationTime)
        nextStage();
        if (stage == Stages.Paying &&
        now >= creationTime)
        nextStage();
        // 按照交易过度阶段
        _;
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
    uint public electRaised;//已确定的卖家卖出店里总和（筹集的电力数）
    uint public elecPrice;//买家出价(单价)
    Seller[] public sellers;//卖家集合

    /* 卖家的数据结构 */
    struct Seller {
    address addr;
    uint elecAmount;
    }

    /*构造函数*/
    function electransaction(address _buyer, uint _electGoal, uint _electRaised, uint _electPrice, uint _electNumSeller){
        buyer = _buyer;
        electGoal = _electGoal;
        electRaised = _electRaised;
        electPrice = _electPrice;
        electNumSeller = _electNumSeller;
    }
    //TODO 需要获得请求买电人的账户总额
    function checkAccount(uint _account)
    atStage(Stages.New)
    {
        if (_account > elecPrice * electGoal)
        nextStage();
        else
        endStage();

    }

    /*没有名称的函数是每当有人向合同发送资金时调用的默认函数*/
    function()
    payable
    timedTransitions
    atStage(Stages.Collecting)
    {
        uint elects = msg.value;
        sellers[sellers.length++] = Seller({addr : msg.sender, amount : elects});
        electRaised += elects;
        TokenTransfer(msg.sender, elects);
        if (electRaised >= electGoal)
        nextStage();
    }

    /* 检查目标是否达到，达到就结束 */
    function checkGoalReached()
    atStage(Stages.Paying)
    {
        if (electRaised >= electGoal) {
            buyer.transfer(electRaised);
            TokenTransfer(buyer, electRaised);
        }
        nextStage();
    }
    /*买家付钱*/
    function pay()
    payable
    timedTransitions
    atStage(Stages.Paying)
    {

        if (msg.sender == buyer) {
            uint amount = msg.value;
            amountPay += amount;

            for (uint i = 0; i < lenders.length; ++i) {
                sellers[i].addr.transfer(sellers[i].elecAmount * elecPrice);
            }

            nextStage();

        }

    }
    function clean()
    atStage(Stages.Finished)
    {

    }
}
