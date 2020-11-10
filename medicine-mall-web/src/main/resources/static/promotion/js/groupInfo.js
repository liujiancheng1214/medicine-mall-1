window.$vue = new Vue({
    el: '#sys_groupInfo',
    data() {
        return {
            showDetails: false,
            showStartGroup:false,
            selectedPromotion:{shareTitle:""},
            startGroupDTO:{
                promotionId:""
            },
            groupInfoDto: {
                startTime: "",
                endTime: "",
                promotionId: "",
                keyWord: "",
            },
            groupStatus: "",
            groupUserList: [],
            ongoingActivities: [],
            dateOption: {
                shortcuts: [
                    {
                        text: '最近一周',
                        value() {
                            const end = new Date();
                            const start = new Date();
                            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
                            return [start, end];
                        }
                    },
                    {
                        text: '最近一个月',
                        value() {
                            const end = new Date();
                            const start = new Date();
                            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
                            return [start, end];
                        }
                    },
                    {
                        text: '最近三个月',
                        value() {
                            const end = new Date();
                            const start = new Date();
                            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
                            return [start, end];
                        }
                    }
                ]
            },
            page: {current: 1, size: 10},
            pageSizeOpts: [5, 10, 20, 30, 40, 50],
            groupInfoList: [],
            total: 0,
            size: 0,
            current: 0,
            pages: 0,
            page2: {current: 1, size: 10},
            pageSizeOpts2: [5, 10, 20, 30, 40, 50],
            userList: [],
            total2: 0,
            size2: 0,
            current2: 0,
            pages2: 0,
            columns2: [
                {
                    title: '客户编号',
                    key: 'id',
                    minWidth: 100,
                    align: 'center'
                },
                {
                    title: '客户姓名',
                    key: 'userName',
                    align: 'center',
                    minWidth: 150,
                },
                {
                    title: '客户手机号',
                    key: 'userPhone',
                    align: 'center',
                    minWidth: 150,
                },
                {
                    title: '订单编号',
                    key: 'orderId',
                    align: 'center',
                    minWidth: 180,
                },
                {
                    title: '支付状态',
                    key: 'isPay',
                    align: 'center',
                    minWidth: 130,
                    render: (h, params) => {
                        let item = params.row;
                        let operates = ""
                        switch (item.isPay) {
                            case 1:
                                operates = [h('p', {style: {color:"red"}}, "未支付")];
                                break;
                            case 2:
                                operates = [h('p', {style: {}}, "支付锁定")];
                                break;
                            case 3:
                                operates = [h('p', {style: {color:"green"}}, "已支付")];
                                break;
                            default:
                                operates = [h('p', {style: {}}, "未支付")];
                        }
                        return h('div', operates);
                    }
                },
                {
                    title: '加入时间',
                    key: 'createTime',
                    align: 'center',
                    minWidth: 160,
                    render: (h, params) => {
                        let item = params.row;
                        let time = Tools.chGMT2Str(item.createTime);
                        let operates = [h('p', {style: {}}, time)];
                        return h('div', operates);
                    }
                },
            ],
            columns1: [
                {
                    title: '拼团编号',
                    key: 'id',
                    minWidth: 100,
                    align: 'center'
                },
                {
                    title: '活动名称',
                    key: 'promotionName',
                    align: 'center',
                    minWidth: 150,
                },
                {
                    title: '发起人（团长）',
                    key: 'plushUserName',
                    align: 'center',
                    minWidth: 150,
                    render: (h, params) => {
                        let item = params.row;
                        let operates = [h('p', {style: {}}, item.plushUserName)];
                        operates.push(h('p', {style: {}}, item.plushUserPhone));
                        return h('div', operates);
                    }
                },
                {
                    title: '参团人数',
                    key: 'userNum',
                    align: 'center',
                    minWidth: 100,
                    render: (h, params) => {
                        let item = params.row;
                        let operates = [h('p', {style: {}}, item.userNum+"人")];
                        return h('div', operates);
                    }
                },
                {
                    title: '购买数量',
                    key: 'itemNum',
                    align: 'center',
                    minWidth: 100,
                    render: (h, params) => {
                        let item = params.row;
                        let operates = [h('p', {style: {}}, item.itemNum+"件")];
                        return h('div', operates);
                    }
                },
                {
                    title: '拼团状态',
                    key: 'status',
                    align: 'center',
                    minWidth: 150,
                    render: (h, params) => {
                        let item = params.row;
                        let operates = ""
                        switch (item.status) {
                            case 1:
                                operates = [h('p', {style: {color:"red"}}, "拼团中")];
                                break;
                            case 2:
                                operates = [h('p', {style: {color:"green"}}, "拼团成功")];
                                break;
                            case 3:
                                operates = [h('p', {style: {}}, "已取消")];
                                break;
                            case 4:
                                operates = [h('p', {style: {}}, "活动结束未成功")];
                                break;
                            default:
                                operates = [h('p', {style: {}}, "拼团中")];
                        }
                        return h('div', operates);
                    }
                },
                {
                    title: '拼团时间',
                    key: 'createTime',
                    align: 'center',
                    minWidth: 220,
                    render: (h, params) => {
                        let item = params.row;
                        let time = Tools.chGMT2Str(item.createTime);
                        let endTime = Tools.chGMT2Str(item.endTime);
                        let operates = [h('p', {style: {}}, '发起时间：' + time)];
                        switch (item.status) {
                            case 2:
                                operates.push(h('p', {style: {}}, '结束时间：' + endTime));
                                break;
                            case 3:
                                operates.push(h('p', {style: {}}, '结束时间：' + endTime));
                                break;
                            case 4:
                                operates.push(h('p', {style: {}}, '结束时间：' + endTime));
                                break;
                        }
                        return h('div', operates);
                    }
                },
                {
                    title: '操作',
                    align: 'center',
                    minWidth: 150,
                    render: (h, params) => {
                        let item = params.row;
                        let operates = [h('Button', {
                            props: {
                                type: 'info',
                                size: 'small'
                            },
                            style: {
                                marginLeft: '8px'
                            },
                            on: {
                                click: () => {
                                    $vue.groupId = item.id;
                                    $vue.details();
                                }
                            }
                        }, '参团详情')];
                        return h('div', operates);
                    }
                }
            ]
        }
    },
    mounted() {
        this.getGroupInfoList();
        //键盘回车执行搜索
        let my = this;
        document.onkeydown = function(e) {
            let key = window.event.keyCode;
            if (key == 13) {
                my.search();
            }
        }
    },
    components: {},
    methods: {
        getGroupInfoList() {
            this.$Spin.show();
            api.get("/cms/group/list",
                {
                    params: {
                        size: this.page.size,
                        current: this.page.current,
                        status: this.groupStatus == 0 ? null : this.groupStatus,
                        startTime: this.groupInfoDto.startTime,
                        endTime: this.groupInfoDto.endTime,
                        promotionId: this.groupInfoDto.promotionId,
                        keyWord: this.groupInfoDto.plushUserId,
                    }
                }
            ).then((res) => {
                this.$Spin.hide();
                if (res.data.code === 0) {
                    this.groupInfoList = res.data.data.records;
                    this.total = res.data.data.total;
                    this.size = res.data.data.size;
                    this.current = res.data.data.current;
                    this.pages = res.data.data.pages;
                }
            }).catch(() => {
                this.$Spin.hide();
            });
        },
        initiate(){
            console.log(this.startGroupDTO)
            api.post("/cms/group/initiate",this.startGroupDTO).then((res) => {
                if(res.data.code === 0){
                    this.$Message.success('发起成功！');
                    this.showStartGroup = false;
                }
            })
        },
        changePromotion(promotion){
            if(promotion != null){
                this.startGroupDTO.promotionId = promotion.promotionId;
            }
        },
        pageChange(current) {
            this.page.current = current;
            this.page.size = this.size;
            this.getGroupInfoList();
        },
        pageSizeChange(size) {
            this.page.current = 1;
            this.page.size = size;
            this.getGroupInfoList();
        },
        pageChange2(current) {
            this.page2.current = current;
            this.page2.size = this.size2;
            this.getUserList();
        },
        startGroup(){
            api.get("/cms/promotion/ongoingActivities").then((res) => {
                if(res.data.code === 0){
                    this.ongoingActivities = res.data.data
                    if(this.ongoingActivities.length != 0){
                        this.showStartGroup = true;
                    }else {
                        this.$Message.warning("当前无可发起拼团的活动！", 3);
                    }

                }
            })

        }
        ,
        closeStartGroup(){
            this.startGroupDTO.promotionId = "";
            this.selectedPromotion = {shareTitle:""};
            this.showStartGroup = false
        },
        pageSizeChange2(size) {
            this.page2.curren = 1;
            this.page2.size = size;
            this.getUserList();
        },
        dateChange(item) { //选择发起时间
            this.groupInfoDto.startTime = item[0] == null ? null : item[0];
            this.groupInfoDto.endTime = item[1] == null ? null : item[1];
        },
        details(){ //查看详情
            this.showDetails = true;
            this.getUserList();
        },
        emptyForm() {
            this.showDetails = false;
        },
        getUserList(){ //查询已参团人
            this.$Spin.show();
            api.get("/cms/group/user/list",
                {
                    params: {
                        size: this.page.size,
                        current: this.page.current,
                        groupId: this.groupId,
                    }
                }
            ).then((res) => {
                this.$Spin.hide();
                if (res.data.code === 0) {
                    this.userList = res.data.data.records;
                    this.total2 = res.data.data.total;
                    this.size2 = res.data.data.size;
                    this.current2 = res.data.data.current;
                    this.pages2 = res.data.data.pages;
                }
            }).catch(() => {
                this.$Spin.hide();
            });
        },
        refresh() { //刷新
            this.$refs.dateSearch.handleClear()
            this.groupInfoDto = {
                startTime: "",
                endTime: "",
                promotionId: "",
                keyWord: "",
            },
            this.page.current = 1;
            this.getGroupInfoList();
        },
        search() { //搜索
            this.page.current = 1;
            this.getGroupInfoList();
        },
        tips_warning(msg_content) {
            this.$Message.warning(msg_content);
        },
        tips_cancel(msg_content) {
            this.$Message.info(msg_content);
        },
        tips_success(msg_content) {
            this.$Message.success(msg_content);
        },
        tips_error(msg_content) {
            this.$Message.error(msg_content);
        },
    }
});
