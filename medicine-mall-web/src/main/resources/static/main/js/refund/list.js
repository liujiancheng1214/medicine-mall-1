window.$vue = new Vue({
    el: '#refund_order_list',
    data() {
        return {
            page:{
                current: 1,
                size: 10,
                total: 0
            },
            loading: true,
            value1:true,
            tabIndex: '3',
            searchVar: '',
            refundTime: [],
            orderTime: [],
            settleStatus:'',
            settleStatusList:[],
            refundReasonList:[],
            deliveryTypeList:[],
            refundReason:'',
            deliveryType:'',
            selectList: [],
            refuseRefundRemark:'',
            agreeRefundRemark:'',
            tableHeight: 0,
            orderList: [],
            tab2: 0,
            tab3: 0,
            tab4: 0,
            tab5: 0,
            tab6: 0,
            tab7: 0,
            label1: (h) => {
                return h('div', [
                    h('span', '待客户确认 '),
                    h('Badge', {
                        props: {
                            count: this.tab2,
                            showZero: true
                        }
                    })
                ])
            },
            label2: (h) => {
                return h('div', [
                    h('span', '待审核 '),
                    h('Badge', {
                        props: {
                            count: this.tab3,
                            showZero: true
                        }
                    })
                ])
            },
            label3: (h) => {
                return h('div', [
                    h('span', '退款中 '),
                    h('Badge', {
                        props: {
                            count: this.tab4,
                            showZero: true
                        }
                    })
                ])
            },
            label4: (h) => {
                return h('div', [
                    h('span', '已退款 '),
                    h('Badge', {
                        props: {
                            count: this.tab5,
                            showZero: true
                        }
                    })
                ])
            },
            label5: (h) => {
                return h('div', [
                    h('span', '退款失败 '),
                    h('Badge', {
                        props: {
                            count: this.tab6,
                            showZero: true
                        }
                    })
                ])
            },
            label6: (h) => {
                return h('div', [
                    h('span', '客户拒绝 '),
                    h('Badge', {
                        props: {
                            count: this.tab7,
                            showZero: true
                        }
                    })
                ])
            },
            tableHead: [
                {
                    type: 'selection',
                    minWidth: 20,
                    align: 'center'
                },
                {
                    title: '订单ID',
                    align: 'center',
                    key: 'orderId',
                    minWidth: 100,
                    render:(h,params) => {
                        return [h('div', [h('p', {}, "" + params.row.id)]),
                            h('div', [h('p', {}, "" + params.row.orderId)])];
                    }
                },
                {
                    title: '下单时间',
                    align: 'center',
                    minWidth: 180,
                    render: (h, params) => {
                        let date = this.timeFormat(params.row.createTime);
                            return [h('div', [h('p', {}, "" + date)])];
                    }
                }
                ,{
                    title: '药店名称',
                    align: 'center',
                    minWidth: 150,
                    render: (h, params) => {
                        return [h('div', [h('p', {}, "" + params.row.companyName)])];
                    }
                }
                ,{
                    title: '退款商品',
                    align: 'left',
                    minWidth: 120,
                    render: (h, params) => {
                        let data = params.row.infoList;
                        let arr = [];
                        for (let i = 0; i < data.length; i++) {
                            arr.push(h('div', [h('p', {}, "产品编号：" + data[i].itemNo)]));//商品编号
                            arr.push(h('div', [h('p', {}, "名称：" + data[i].itemName)]));//名称
                            arr.push(h('div', [h('p', {}, "规格：" + data[i].spec)]));//规格
                            arr.push(h('div', [h('p', {}, "有效期：" + data[i].expiryDate+"个月")]));//有效期（个月）
                            arr.push(h('div', [h('p', {}, "批准文号：" + data[i].approvalNo)]));//批准文号
                            arr.push(h('div', [h('p', {style:{borderBottom: "1px solid #ddd"}}, "库存：" + data[i].qty)]));//库存
                        }
                        return arr;
                    }
                }
                ,{
                    title: '结算状态',
                    align: 'center',
                    minWidth: 60,
                    render: (h, params) => {
                        let text = '未结算';
                        if (params.row.settleStatus === 1){
                            text = '未结算';
                        }
                        if (params.row.settleStatus === 2){
                            text = '结算中';
                        }
                        if (params.row.settleStatus === 3){
                            text = '已结算';
                        }
                        return [h('div', [h('p', {}, text)])];
                    }
                }
                ,{
                    title: '退款数量',
                    align: 'center',
                    minWidth: 60,
                    render: (h, params) => {
                        return [h('div', [h('p', {}, params.row.totalNum)])];
                    }
                }
                ,{
                    title: '退款总额',
                    align: 'center',
                    minWidth: 80,
                    render: (h, params) => {
                        return [h('div', [h('p', {}, params.row.totalRefundAmount)])];
                    }
                }
                ,{
                    title: '退款类型',
                    align: 'center',
                    minWidth: 80,
                    render: (h, params) => {
                        let text = "整单退款";
                        if (params.row.refundType === 2){
                            text = "部分退款";
                        }
                        return [h('div', [h('p', {}, text)])];
                    }
                }
                ,{
                    title: '退款原因',
                    align: 'center',
                    minWidth: 80,
                    render: (h, params) => {
                        return [h('div', [h('p', {}, params.row.reason)])];
                    }
                }
                ,{
                    title: '退款备注',
                    align: 'center',
                    minWidth: 80,
                    render: (h, params) => {
                        return [h('div', [h('p', {}, params.row.remarkAdmin)])];
                    }
                }
                ,{
                    title: '申请退款时间',
                    align: 'center',
                    minWidth: 180,
                    render: (h, params) => {
                        return [h('div', [h('p', {}, params.row.applyTime)])];
                    }
                }
            ]
        }
    },
    mounted() {
        this.getOrderCount();
        this.getOrderList();
        this.getSettleStatusList();
        this.getRefundReasonList();
        this.getDeliveryTypeList();
        // 表格高度跟着浏览器窗口高度变化
        // window.onresize = () => {
        //     return (() => {
        //         this.changeTableHeight();
        //     })();
        // };
    },
    components: {
    },
    methods: {
        changeTableHeight() {
            this.tableHeight = window.innerHeight - this.$refs.table.$el.offsetTop - 140;
        },
        getOrderList(){
            this.loading = true;
            api.get("/cms/refund/page", {params:{
                    "current": this.page.current,
                    "size": this.page.size,
                    "status": this.tabIndex,
                    "search": this.searchVar,
                    "settleStatus": this.settleStatus,
                    "refundReason": this.refundReason,
                    "orderTimeBegin": this.orderTime[0],
                    "orderTimeEnd": this.orderTime[1],
                    "refundTimeBegin": this.refundTime[0],
                    "refundTimeEnd": this.refundTime[1]
                }}).then((res) => {
                if (res.data.code === 0) {
                    let result = res.data.data;
                    this.orderList = result.records||[];
                    this.page.total = result.total;
                    this.page.current = result.current;
                }
            });
            this.loading = false;
        },
        getOrderCount() {
            api.get("/cms/refund/statistical", {params:{
                    "search": this.searchVar,
                    "settleStatus": this.settleStatus,
                    "refundReason": this.refundReason,
                    "orderTimeBegin": this.orderTime[0],
                    "orderTimeEnd": this.orderTime[1],
                    "refundTimeBegin": this.refundTime[0],
                    "refundTimeEnd": this.refundTime[1]
                }}).then((res) => {
                if (res.data.code === 0) {
                    let data = res.data.data;
                    if (data != null) {
                        this.tab2 = data.tab2;
                        this.tab3 = data.tab3;
                        this.tab4 = data.tab4;
                        this.tab5 = data.tab5;
                        this.tab6 = data.tab6;
                        this.tab7 = data.tab7;
                    }
                }
            });
        },
        changeTab() {
            this.orderList = [];
            this.search();
        },
        getSelection(list) {
            this.selectList = list;
        },
        pageChange(current){
            this.page.current = current;
            this.page.size = this.size;
            this.getOrderList();
        },
        pageSizeChange(size){
            this.page.current = 1;
            this.page.size = size;
            this.getOrderList();
        },
        search(){
            this.page.current = 1;
            this.getOrderList();
            this.getOrderCount();
        },
        clearDate() {
            this.dateArray = [];
            this.search();
        },
        operate(op){
            switch (op) {
                case "1"://批量审核通过退款
                    if (this.selectList.length<1){
                        this.tips_warning("请至少选中一行");
                        return;
                    }
                    this.$Modal.confirm({
                        render: (h) => {
                            return h('Input', {
                                props: {
                                    value: this.agreeRefundRemark,
                                    autofocus: true,
                                    placeholder: '请填写备注',
                                    type:'textarea'
                                },
                                on: {
                                    input: (val) => {
                                        this.agreeRefundRemark = val;
                                    }
                                }
                            })
                        },
                        onOk:()=>{
                            let ids = [];
                            for (let i = 0; i < this.selectList.length; i++) {
                                ids.push(this.selectList[i].id)
                            }
                            api.post("/cms/refund/agreeRefundBatch", {ids:ids,remarkOrReason:this.agreeRefundRemark}).then((res) => {
                                if (res.data.code === 0) {
                                    this.tips_success("操作成功！");
                                    this.getOrderList();
                                    this.getOrderCount();
                                    this.selectList=[];
                                }
                            });
                        },
                        onCancel:()=>{
                            return;
                        }
                    });
                    break;
                case "2"://拒绝退款
                    if (this.selectList.length<1){
                        this.tips_warning("请选中一行");
                        return;
                    }
                    if (this.selectList.length>1){
                        this.tips_warning("只能选中一行");
                        return;
                    }
                    this.$Modal.confirm({
                        render: (h) => {
                            return h('Input', {
                                props: {
                                    value: this.agreeRefundRemark,
                                    autofocus: true,
                                    placeholder: '请填写原因',
                                    type:'textarea'
                                },
                                on: {
                                    input: (val) => {
                                        this.agreeRefundRemark = val;
                                    }
                                }
                            })
                        },
                        onOk:()=>{
                            let ids = [];
                            for (let i = 0; i < this.selectList.length; i++) {
                                ids.push(this.selectList[i].id)
                            }
                            api.post("/cms/refund/refuseRefund", {ids:ids,remarkOrReason:this.agreeRefundRemark}).then((res) => {
                                if (res.data.code === 0) {
                                    this.tips_success("操作成功！");
                                    this.getOrderList();
                                    this.getOrderCount();
                                    this.selectList=[];
                                }
                            });
                        },
                        onCancel:()=>{
                            return;
                        }
                    });
                    break;
                default:
            }
        },
        getSettleStatusList() {
            api.get("/sys/dict", {params:{type:"SETTLE_STATUS"}}).then((res) => {
                if (res.data.code === 0) {
                    this.settleStatusList = res.data.data;
                }
            });
        },
        getRefundReasonList() {
            api.get("/sys/dict", {params:{type:"REFUND_REASON"}}).then((res) => {
                if (res.data.code === 0) {
                    this.refundReasonList = res.data.data;
                }
            });
        },
        getDeliveryTypeList() {
            api.get("/sys/dict", {params:{type:"DELIVERY_TYPE"}}).then((res) => {
                if (res.data.code === 0) {
                    this.deliveryTypeList = res.data.data;
                }
            });
        },
        settleStatusSelectChange(value){
            this.tips_info("选中的值为："+value);
        },
        tips_warning (msg_content) {
            this.$Message.warning(msg_content);
        },
        tips_info (msg_content) {
            this.$Message.info(msg_content);
        },
        tips_success (msg_content) {
            this.$Message.success(msg_content);
        },
        tips_error (msg_content) {
            this.$Message.error(msg_content);
        },
        // 日期格式转换
        timeFormat(time) {
            if (time) {
                let date = new Date(time);

                // 年
                let year = date.getFullYear();
                // 月
                let month = date.getMonth() + 1;
                // 日
                let day = date.getDate();
                // 时
                let hh = date.getHours();
                // 分
                let mm = date.getMinutes();
                // 秒
                let ss = date.getSeconds();
                // 结果
                let datetime = year + "-";

                if (month < 10) {
                    datetime += "0";
                }
                datetime += month + "-";

                if (day < 10) {
                    datetime += "0";
                }
                datetime += day + " ";

                if (hh < 10) {
                    datetime += "0";
                }
                datetime += hh + ":";

                if (mm < 10) {
                    datetime += '0';
                }
                datetime += mm + ":";

                if (ss < 10) {
                    datetime += '0';
                }
                datetime += ss;
                return datetime;
            }
            return null;
        }
    }
});
