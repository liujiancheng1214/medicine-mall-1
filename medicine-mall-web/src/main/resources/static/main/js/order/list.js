window.$vue = new Vue({
    el: '#order_list',
    data() {
        return {
            page:{
                current: 1,
                size: 10,
                total: 0
            },
            leftStyle: {textAlign:'left',paddingLeft:'20%',paddingRight:'20%'},
            loading: true,
            load: true,
            index: '0',
            searchValue: null,
            dateArray: [],
            startDate: "",
            endDate: "",
            selectList: [],
            tableHeight: 'auto',
            orderList: [],
            infoList: [],
            detailList: [],
            companyList: [],
            refundReasonList: [],
            downloadCount: 0,
            show: false,
            importExcelModal: false,
            wuliuModal: false,
            refundModal: false,
            sendInfo: {
                expressNo: [{value:null}]
            },
            refundDto: {
                refundType: 1,
                reason: null,
                list: []
            },
            selectOrder: {},
            user: {},
            tab1: 0,
            tab2: 0,
            tab3: 0,
            tab4: 0,
            tab5: 0,
            tab6: 0,
            tab7: 0,
            tab8: 0,
            tab9: 0,
            label1: (h) => {
                return h('div', [
                    h('span', '未付款'),
                    h('Badge', {
                        props: {
                            count: this.tab1,
                            showZero: true
                        }
                    })
                ])
            },
            label2: (h) => {
                return h('div', [
                    h('span', '待处理'),
                    h('Badge', {
                        props: {
                            count: this.tab2,
                            showZero: true
                        }
                    })
                ])
            },
            label3: (h) => {
                return h('div', [
                    h('span', '审核资质'),
                    h('Badge', {
                        props: {
                            count: this.tab3,
                            showZero: true
                        }
                    })
                ])
            },
            label4: (h) => {
                return h('div', [
                    h('span', '正在开单'),
                    h('Badge', {
                        props: {
                            count: this.tab4,
                            showZero: true
                        }
                    })
                ])
            },
            label5: (h) => {
                return h('div', [
                    h('span', '分拣中'),
                    h('Badge', {
                        props: {
                            count: this.tab5,
                            showZero: true
                        }
                    })
                ])
            },
            label6: (h) => {
                return h('div', [
                    h('span', '待配送'),
                    h('Badge', {
                        props: {
                            count: this.tab6,
                            showZero: true
                        }
                    })
                ])
            },
            label7: (h) => {
                return h('div', [
                    h('span', '配送中'),
                    h('Badge', {
                        props: {
                            count: this.tab7,
                            showZero: true
                        }
                    })
                ])
            },
            label8: (h) => {
                return h('div', [
                    h('span', '配送完成'),
                    h('Badge', {
                        props: {
                            count: this.tab8,
                            showZero: true
                        }
                    })
                ])
            },
            label9: (h) => {
                return h('div', [
                    h('span', '整单退款'),
                    h('Badge', {
                        props: {
                            count: this.tab9,
                            showZero: true
                        }
                    })
                ])
            },
            tableHead: [
                {
                    type: 'selection',
                    minWidth: 40,
                    align: 'center'
                },
                {
                    title: '订单ID',
                    align: 'center',
                    key: 'orderId',
                    minWidth: 100
                },
                {
                    title: '下单/最新处理时间',
                    align: 'center',
                    minWidth: 180,
                    render: (h, params) => {
                        let arr = [];
                        let createTime = Tools.chGMT2Str(params.row.createTime);
                        let updateTime = Tools.chGMT2Str(params.row.updateTime);
                        if (createTime) {
                            arr.push(h('div', [h('p', {}, createTime)]));
                        }
                        if (updateTime) {
                            arr.push(h('br'));
                            arr.push(h('div', [h('p', {}, "/" + updateTime)]));
                        }
                        return arr;
                    }
                },
                {
                    title: '药店名称',
                    align: 'center',
                    minWidth: 150,
                    render: (h, params) => {
                        let arr = [];
                        let companyName = params.row.user.companyName;
                        let helpCode = params.row.user.helpCode;
                        if (companyName) {
                            arr.push(h('div', [h('p', {style:this.leftStyle}, companyName)]));
                        }
                        if (helpCode) {
                            arr.push(h('div', [h('p', {style:this.leftStyle}, "药店编码：" + helpCode)]));
                        }
                        return arr;
                    }
                },
                {
                    title: '总金额',
                    align: 'center',
                    minWidth: 80,
                    render: (h, params) => {
                        let arr = [];
                        let totalAmount = params.row.totalAmount;
                        if (totalAmount) {
                            totalAmount = totalAmount.toFixed(2);
                            arr.push(h('div', [h('p', {style:this.leftStyle}, totalAmount + "元")]));
                        }
                        let discountAmount = params.row.discountAmount;
                        if (discountAmount){
                            discountAmount = discountAmount.toFixed(2);
                            arr.push(h('div', [h('p', {style:this.leftStyle}, "(含商家优惠" + discountAmount + "元)")]));
                        }
                        return arr;
                    }
                },
                {
                    title: '处理情况',
                    align: 'center',
                    minWidth: 160,
                    render: (h, params) => {
                        let arr = [];
                        let orderStatus = params.row.orderStatus;
                        let userRemark = params.row.userRemark;
                        let adminRemark = params.row.adminRemark;
                        let updateTime = Tools.chGMT2Str(params.row.updateTime);
                        let expressCompany = params.row.expressCompany;
                        let expressNo = params.row.expressNo;
                        if (orderStatus != null) {
                            orderStatus = this.getStr(orderStatus);
                        }
                        arr.push(h('div', [h('p', {style:this.leftStyle}, "状态：" + orderStatus)]));
                        if (!userRemark) {
                            userRemark = "";
                        }
                        arr.push(h('div', [h('p', {style:this.leftStyle}, "药店备注：" + userRemark)]));
                        if (!adminRemark) {
                            adminRemark = "";
                        }
                        arr.push(h('div', [h('p', {style:this.leftStyle}, "后台备注：" + adminRemark)]));
                        if (updateTime) {
                            arr.push(h('div', [h('p', {style:this.leftStyle}, updateTime)]));
                        }
                        if (expressNo && expressCompany) {
                            arr.push(h('div', [h('p', {style:this.leftStyle}, expressCompany +" "+ expressNo)/*,
                                h('Icon', {
                                    attrs: {
                                        type:"md-create"
                                    },
                                    style: {
                                    }
                                })*/
                            ]));
                        }
                        return arr;
                    }
                },
                {
                    title: '收货信息',
                    align: 'center',
                    minWidth: 200,
                    render: (h, params) => {
                        let contactName = params.row.user.contactName;
                        let contactPhone = params.row.user.contactPhone;
                        let provinceName = params.row.user.provinceName;
                        let cityName = params.row.user.cityName;
                        let contactAddress = params.row.user.contactAddress;
                        return [h('div', [h('p', {style:this.leftStyle}, contactName)]),
                            h('div', [h('p', {style:this.leftStyle}, contactPhone)]),
                            h('div', [h('p', {style:this.leftStyle}, provinceName + cityName)]),
                            h('div', [h('p', {style:this.leftStyle}, contactAddress)])];
                    }
                },
                {
                    title: '订单下载次数',
                    align: 'center',
                    minWidth: 80,
                    render: (h, params) => {
                        let arr = [];
                        arr.push(h('div', [h('p', {}, this.downloadCount + "次")]));
                        arr.push(h("br"));
                        arr.push(h('div', [h('i-button', {
                            props: {
                                type: 'primary',
                                size: 'small'
                            },
                            on: {
                                click: () => {
                                    this.show = true;
                                    this.selectOrder = params.row;
                                    this.getInfoList();
                                }
                            }
                        }, "订单详情")]));
                        return arr;
                    }
                }
            ],
            infoHead: [
                {
                    title: '序号',
                    align: 'center',
                    type: 'index',
                    minWidth: 50
                },
                {
                    title: '商品编码',
                    align: 'center',
                    minWidth: 150,
                    key: 'itemNo'
                },
                {
                    title: '商品信息',
                    align: 'center',
                    minWidth: 200,
                    render: (h, params) => {
                        return h('div', [h('p', {}, params.row.itemName + " 有效期至" + Tools.chGMT2Str(params.row.validDay))]);
                    }
                },
                {
                    title: '规格/包装',
                    align: 'center',
                    minWidth: 120,
                    key: 'spec'
                },
                {
                    title: '厂家',
                    align: 'center',
                    minWidth: 160,
                    key: 'factory'
                },
                {
                    title: '数量',
                    align: 'center',
                    minWidth: 60,
                    key: 'itemNum'
                },
                {
                    title: '单价',
                    align: 'center',
                    minWidth: 60,
                    render: (h, params) => {
                        let itemPrice = params.row.itemPrice.toFixed(2);
                        return h('div', [h('p', {}, itemPrice)]);
                    }
                },
                {
                    title: '总金额',
                    align: 'center',
                    minWidth: 80,
                    render: (h, params) => {
                        let totalPrice = params.row.totalPrice.toFixed(2);
                        return h('div', [h('p', {}, totalPrice)]);
                    }
                },
                {
                    title: '退款数量',
                    align: 'center',
                    minWidth: 80,
                    key: 'refundNum'
                },
                {
                    title: '退款金额',
                    align: 'center',
                    minWidth: 80,
                    render: (h, params) => {
                        let refundPrice = params.row.refundPrice.toFixed(2);
                        return h('div', [h('p', {}, refundPrice)]);
                    }
                }
            ],
            refundHead: [
                {
                    title: '药品信息',
                    align: 'center',
                    minWidth: 180,
                    render: (h, params) => {
                        let arr = [];
                        arr.push(h('div', [h('p', {style:this.leftStyle}, params.row.itemNo)]));
                        arr.push(h('div', [h('p', {style:this.leftStyle}, params.row.itemName + " " + params.row.spec + " 有效期至" + Tools.chGMT2Str(params.row.validDay))]));
                        arr.push(h('div', [h('p', {style:this.leftStyle}, params.row.factory)]));
                        return arr;
                    }
                },
                {
                    title: '单价',
                    align: 'center',
                    minWidth: 80,
                    render: (h, params) => {
                        let itemPrice = params.row.itemPrice.toFixed(2);
                        return h('div', [h('p', {}, itemPrice)]);
                    }
                },
                {
                    title: '购买数量',
                    align: 'center',
                    minWidth: 80,
                    render: (h, params) => {
                        return [h('div', [h('p', {}, params.row.itemNum)]),
                            h('div', [h('p', {}, "库存：" + params.row.qty)])];
                    }
                },
                {
                    title: '退款数量',
                    align: 'center',
                    minWidth: 70,
                    render: (h, params) => {
                        let refundNum = params.row.refundNum;
                        let itemPrice = params.row.itemPrice;
                        return  h('div', [h('InputNumber', {
                            props: {
                                max: params.row.itemNum,
                                min: 0,
                                value: refundNum
                            },
                            style: {
                                textAlign: 'center'
                            },
                            on: {
                                'on-change': num => {
                                    this.detailList[params.index].refundNum = num;
                                    this.detailList[params.index].refundPrice = num*itemPrice;
                                }
                            }
                        })]);
                    }
                },
                {
                    title: '退款金额',
                    align: 'center',
                    minWidth: 80,
                    render: (h, params) => {
                        let refundPrice = params.row.refundPrice;
                        return  h('div', [h('p', {}, refundPrice)]);
                    }
                }
            ],
            dateOption: {
                disabledDate(date) {
                    if (date != null && date !== "") {
                        return date.getTime() > new Date(new Date(new Date(new Date().setDate(new Date().getDate() + 1)).toLocaleDateString()));;
                    }
                },
                shortcuts: [
                    {
                        text: '今天',
                        value: () => {
                            let start = new Date(new Date(new Date().toLocaleDateString()));
                            let end = this.getDateTime();
                            return [start, end];
                        }
                    },
                    {
                        text: '最近一周',
                        value: () => {
                            let start = this.getDateTime("day", 6);
                            let end = this.getDateTime();
                            return [start, end];
                        }
                    },
                    {
                        text: '最近一个月',
                        value: () => {
                            let start = this.getDateTime("month", 1);
                            let end = this.getDateTime();
                            return [start, end];
                        }
                    },
                    {
                        text: '最近三个月',
                        value: () => {
                            let start = this.getDateTime("month", 3);
                            let end = this.getDateTime();
                            return [start, end];
                        }
                    },
                    {
                        text: '最近一年',
                        value: () => {
                            let start = this.getDateTime("year", 1);
                            let end = this.getDateTime();
                            return [start, end];
                        }
                    }
                ]
            }
        }
    },
    mounted() {
        this.getCompanyList();
        this.getRefundReasonList();
        this.getOrderCount();
        this.getOrderList();
        // 表格高度跟着浏览器窗口高度变化
        /*window.onresize = () => {
            return (() => {
                this.changeTableHeight();
            })();
        };*/
    },
    components: {
    },
    methods: {
        tips_warning (msgContent) {
            this.$Message.warning(msgContent, 3);
        },
        tips_info (msgContent) {
            this.$Message.info(msgContent, 3);
        },
        tips_success (msgContent) {
            this.$Message.success(msgContent, 3);
        },
        tips_error (msgContent) {
            this.$Message.error(msgContent, 3);
        },
        changeTableHeight() {
            this.tableHeight = window.innerHeight - this.$refs.table.$el.offsetTop - 140;
        },
        getOrderList(){
            this.loading = true;
            api.get("/cms/order/list", {params:{
                    "current": this.page.current,
                    "size": this.page.size,
                    "searchValue": this.searchValue,
                    "startDate": this.startDate,
                    "endDate": this.endDate,
                    "orderStatus": this.index
                }}).then((res) => {
                if (res.data.code === 0 && res.data.data != null) {
                    let result = res.data.data;
                    this.orderList = result.records;
                    this.page.total = result.total;
                    this.page.current = result.current;
                }
            });
            this.loading = false;
        },
        getOrderCount() {
            api.get("/cms/order/orderCount", {params:{
                    "searchValue": this.searchValue,
                    "startDate": this.startDate,
                    "endDate": this.endDate,
                }}).then((res) => {
                if (res.data.code === 0 && res.data.data != null) {
                    let data = res.data.data;
                    if (data != null) {
                        this.tab1 = data.tab1;
                        this.tab2 = data.tab2;
                        this.tab3 = data.tab3;
                        this.tab4 = data.tab4;
                        this.tab5 = data.tab5;
                        this.tab6 = data.tab6;
                        this.tab7 = data.tab7;
                        this.tab8 = data.tab8;
                        this.tab9 = data.tab9;
                    }
                }
            });
        },
        getInfoList() {
            this.load = true;
            this.infoList = [];
            api.get("/cms/order/getInfoByOrderId", {params:{
                    "orderId": this.selectOrder.orderId
                }}).then((res) => {
                if (res.data.code === 0 && res.data.data != null) {
                    this.infoList = res.data.data;
                }
            });
            this.load = false;
        },
        getCompanyList() {
            api.get("/pub/expressCompany/list").then((res) => {
                if (res.data.code === 0 && res.data.data != null) {
                    this.companyList = res.data.data;
                }
            });
        },
        getRefundReasonList() {
            api.get("/sys/dict", {params:{type:"REFUND_REASON"}}).then((res) => {
                if (res.data.code === 0 && res.data.data != null) {
                    this.refundReasonList = res.data.data;
                }
            });
        },
        changeStatus(type) {
            if (this.selectList.length === 0) {
                return this.tips_warning("请选择需要处理的订单");
            }
            let url = "/cms/order/";
            if (type === "receive" || type === "starting" || type === "started" ||
                type === "ready" || type === "delivered") {
                url += type;
            }
            let orderIds = [];
            this.selectList.forEach((order) => {
                orderIds.push(order.orderId);
            });
            api.post(url, {orderIds: orderIds}).then((res) => {
                if (res.data.code === 0 && res.data.data === true) {
                    this.tips_success("操作成功");
                    this.search();
                }else {
                    this.tips_error("操作失败，" + res.msg);
                }
            });
        },
        send() {
            if (this.selectList.length === 0) {
                return this.tips_warning("请选择需要发货的订单");
            }else if (this.selectList.length > 1) {
                return this.tips_warning("只能勾选一个订单哦");
            }
            this.sendInfo = {expressNo: [{value:null}]};
            this.selectOrder = JSON.parse(JSON.stringify(this.selectList[0]));
            this.wuliuModal = true;
        },
        sendLogistic(type) {
            if (type) {
                this.$refs.sendInfo.validate((valid) => {
                    if (valid) {
                        let arr = [];
                        for(let no of this.sendInfo.expressNo){
                            if (no.value.indexOf(" ") !== -1) {
                                return this.tips_warning("快递单号中不能有空格");
                            }
                            if(arr.indexOf(no.value) !== -1) {
                                return this.tips_warning("快递单号不能重复");
                            }
                            arr.push(no.value);
                        }
                        api.post("/cms/order/sendLogistic", {
                            orderIds: this.selectOrder.orderId,
                            expressCompanyId: this.sendInfo.expressCompanyId,
                            expressNo: arr
                        }).then((res) => {
                            if (res.data.code === 0 && res.data.data === true) {
                                this.tips_success("物流发货成功");
                                this.wuliuModal = false;
                                this.search();
                            }else {
                                this.tips_error("物流发货失败，" + res.msg);
                            }
                        });
                    } else {
                        return this.tips_warning("有必填项未填写");
                    }
                });
            } else {
                this.$refs.sendInfo.resetFields();
                this.wuliuModal = false;
            }
        },
        refund() {
            this.infoList = [];
            if (this.selectList.length === 0) {
                return this.tips_warning("请选择需要退款的订单");
            }else if (this.selectList.length > 1) {
                return this.tips_warning("只能勾选一个订单哦");
            }
            this.refundDto = {refundType: 1, reason:null, list: []};
            this.selectOrder = JSON.parse(JSON.stringify(this.selectList[0]));
            api.get("/cms/order/orderDetail", {params:{
                    "orderId": this.selectOrder.orderId
                }}).then((res) => {
                if (res.data.code === 0 && res.data.data != null) {
                    this.detailList = res.data.data;
                }
            });
            this.selectOrder.totalAmount = this.selectOrder.totalAmount.toFixed(2);
            this.user = this.selectOrder.user;
            this.refundModal = true;
        },
        addRefund(type) {
            if (type) {
                this.$refs.refundDto.validate((valid) => {
                    if (valid) {
                        this.refundDto.orderId = this.selectOrder.orderId;
                        if (this.refundDto.refundType === 1) {
                            this.refundDto.totalRefundAmount = this.selectOrder.totalAmount;
                            this.detailList.forEach(d => {
                                this.refundDto.list.push({itemId: d.itemId,num:d.itemNum});
                            });
                        }else if (this.refundDto.refundType === 2) {
                            this.refundDto.list = [];
                            this.detailList.forEach(l => {
                                if (l.refundNum > 0) {
                                    this.refundDto.list.push({itemId:l.itemId, num:l.refundNum});
                                }
                            });
                            if (this.refundDto.list.length === 0) {
                                return this.tips_warning("请填写需要退款的商品的数量");
                            }
                        }
                        api.post("/cms/order/addRefund", this.refundDto).then((res) => {
                            if (res.data.code === 0 && res.data.data === true) {
                                this.tips_success("退款申请成功");
                                this.refundModal = false;
                                this.search();
                            }else {
                                return this.tips_error("退款申请失败，" + res.msg);
                            }
                        });
                    }
                });
            } else {
                this.$refs.refundDto.resetFields();
                this.refundModal = false;
            }
        },
        importTemplate() {
            this.$Modal.info({
                title: "模板说明",
                content: "请不要随意改动模板格式，红色标题为必填项，建议单次导入excel行数不要超过5000行！"
            });
            download.get('/cms/order/template').then((res) => {
                if (res.status === 200) {
                    this.downFile(res, '订单导入模板.xls');
                    this.tips_success("订单导入模板下载成功")
                } else {
                    this.tips_error("订单导入模板下载失败，" + res.msg);
                    console.log(res);
                }
            });
        },
        importExcelSubmit() { //导入数据
            let file = this.$refs.file.files;
            if (file.length === 0) {
                this.tips_warning("请先选择上传文件！");
                return;
            }
            let formData = new FormData();
            formData.append('file', file[0]);
            api.post("/cms/order/template", formData
            ).then((res) => {
                if (res.data.code === 0) {
                    let suc = res.data.data.successCount;
                    let erc = res.data.data.errorCount;
                    this.$Notice.success({
                        title: '导入结果',
                        desc: '成功：' + suc + '条，失败：' + erc + '条'
                    });
                    this.getUserList();
                } else {
                    this.tips_error("操作失败！");
                }
            });
        },
        exportOrder(type) {
            let orderIds = [];
            if (type) {
                this.selectList.forEach(i => {
                    orderIds.push(i.orderId);
                });
            }else {
                if (this.orderList.length === 0) {
                    return this.tips_warning("暂不数据可以导出");
                }
            }
            download.get("/cms/order/export", {
                params: {
                    orderIds: orderIds,
                    orderStatus: this.index
                }
            }).then((res) => {
                if (res.status === 200) {
                    this.downFile(res, '订单信息.xls');
                    this.tips_success("订单信息导出成功");
                } else {
                    this.tips_error("订单信息导出失败，" + res.msg);
                }
            });
        },
        changeTab() {
            this.search();
        },
        getSelection(list) {
            this.selectList = list;
        },
        pageChange(current){
            this.page.current = current;
            this.getOrderList();
        },
        pageSizeChange(size){
            this.page.current = 1;
            this.page.size = size;
            this.getOrderList();
        },
        search(){
            this.page.current = 1;
            this.orderList = [];
            this.getOrderList();
        },
        selectDate(arr) {
            this.startDate = arr[0];
            this.endDate = arr[1];
        },
        addInput () {
            this.sendInfo.expressNo.push({value:null});
        },
        removeInput (index) {
            this.sendInfo.expressNo.splice(index, 1);
        },
        getStr(status) {
            let str = "";
            switch (status) {
                case 0:
                    str = "未付款";
                    break;
                case 1:
                    str = "促销活动";
                    break;
                case 2:
                    str = "已付款";
                    break;
                case 3:
                    str = "审核资质";
                    break;
                case 4:
                    str = "正在开单";
                    break;
                case 5:
                    str = "分拣中";
                    break;
                case 6:
                    str = "待配送";
                    break;
                case 7:
                    str = "配送中";
                    break;
                case 8:
                    str = "客户申请退款";
                    break;
                case 9:
                    str = "后台退款";
                    break;
                case 10:
                    str = "活动失败退款";
                    break;
                case 11:
                    str = "配送完成";
                    break;
                default:
            }
            return str;
        },
        getDateTime(type, num) {
            switch (type) {
                case "day":
                    return new Date(new Date(new Date(new Date().setDate(new Date().getDate() - num)).toLocaleDateString()));
                case "month":
                    return new Date(new Date(new Date(new Date().setMonth(new Date().getMonth() - num)).toLocaleDateString()));
                case "year":
                    return new Date(new Date(new Date(new Date().setFullYear(new Date().getFullYear() - num)).toLocaleDateString()));
                default:
                    return new Date(new Date(new Date().toLocaleDateString()).getTime() + 24 * 60 * 60 * 1000 - 1);
            }
        },
        downFile(res, fileName) {
            let blob = new Blob([res.data], {type: 'application/vnd.ms-excel'}); // 构造一个blob对象来处理数据
            let elink = document.createElement('a'); // 创建a标签
            elink.download = fileName; // a标签添加属性
            elink.style.display = 'none';
            elink.href = URL.createObjectURL(blob);
            document.body.appendChild(elink);
            elink.click(); // 执行下载
            URL.revokeObjectURL(elink.href); // 释放URL 对象
            document.body.removeChild(elink); // 释放标签
        }
    }
});
