window.$vue = new Vue({
    el: '#sys_couponRecord',
    data() {
        return {
            selectLoading: false,
            typeList: [{value: '1', label: '代金券'}, {value: '2', label: '折扣券'}, {value: '3', label: '满减券'}],
            statusList: [{value: '1', label: '可使用'}, {value: '2', label: '已过期'}, {value: '3', label: '已使用'}],
            userList: [],
            model1: '',
            model2: '',
            model3: '',
            couponDto: {
                userId: "",
                couponId: "",
                startTime: "",
                endTime: "",
                couponType: "",
                couponStatus: "",
            },
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
            couponRecordList: [],
            selectList: [],
            total: 0,
            size: 0,
            current: 0,
            pages: 0,
            columns1: [
                {
                    title: '领取编号',
                    key: 'id',
                    minWidth: 100,
                    align: 'center'
                },
                {
                    title: '客户姓名',
                    key: 'userName',
                    align: 'center',
                    minWidth: 150,
                    render: (h, params) => {
                        let item = params.row;
                        let operates = [h('p', {style: {}}, item.userName)];
                        return h('div', operates);
                    }
                },
                {
                    title: '优惠券名',
                    key: 'title',
                    align: 'center',
                    minWidth: 150,
                    render: (h, params) => {
                        let item = params.row;
                        let operates = [h('p', {style: {}}, item.title)];
                        return h('div', operates);
                    }
                },
                {
                    title: '类型',
                    key: 'couponType',
                    minWidth: 100,
                    align: 'center',
                    render: (h, params) => {
                        let item = params.row;
                        let result = '';
                        switch (item.couponType) {
                            case 1:
                                result = "代金券";
                                break;
                            case 2:
                                result = "折扣券";
                                break;
                            case 3:
                                result = "满减券";
                                break;
                            default:
                                result = "代金券";
                        }
                        let operates = [h('p', {style: {}}, result)];
                        return h('div', operates);
                    }
                },
                {
                    title: '状态',
                    key: 'couponStatus',
                    align: 'center',
                    minWidth: 100,
                    render: (h, params) => {
                        let item = params.row;
                        let operates = ""
                        switch (item.couponStatus) {
                            case 1:
                                operates = [h('p', {style: {color:"green"}}, "可使用")];
                                break;
                            case 2:
                                operates = [h('p', {style: {}}, "已过期")];
                                break;
                            case 3:
                                operates = [h('p', {style: {}}, "已使用")];
                                break;
                            default:
                                operates = [h('p', {style: {}}, "可使用")];
                        }
                        return h('div', operates);
                    }
                },
                {
                    title: '有效期',
                    key: 'createTime',
                    align: 'center',
                    minWidth: 220,
                    render: (h, params) => {
                        let item = params.row;
                        let expireTime = Tools.chGMT2Str(item.expireTime);
                        let time = Tools.chGMT2Str(item.createTime);
                        let useTime = Tools.chGMT2Str(item.usedAt);
                        let operates = [h('p', {style: {}}, '领取时间：' + time)];
                        operates.push(h('p', {style: {}}, '过期时间：' + expireTime));
                        switch (item.couponStatus) {
                            case 3:
                                operates.push(h('p', {style: {}}, '使用时间：' + useTime));
                                break;
                        }
                        return h('div', operates);
                    }
                },

            ]
        }
    },
    mounted() {
        this.getCouponRecordList();
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
        getCouponRecordList() {
            this.$Spin.show();
            api.get("/cms/coupon/listRecord",
                {
                    params: {
                        size: this.page.size,
                        current: this.page.current,
                        userId: this.couponDto.userId,
                        couponId: this.couponDto.couponId,
                        startTime: this.couponDto.startTime,
                        endTime: this.couponDto.endTime,
                        couponType: this.couponDto.couponType,
                        couponStatus: this.couponDto.couponStatus,
                    }
                }
            ).then((res) => {
                this.$Spin.hide();
                if (res.data.code === 0) {
                    this.couponRecordList = res.data.data.records;
                    this.total = res.data.data.total;
                    this.size = res.data.data.size;
                    this.current = res.data.data.current;
                    this.pages = res.data.data.pages;
                }
            }).catch(() => {
                this.$Spin.hide();
            });
        },
        remoteItem(item) {//加载用户下拉框
            this.selectLoading = true;
            api.get("/cms/user/listSelect", { params: {userName:item}}).then((res) => {
                this.selectLoading = false
                if (res.data.code === 0) {
                    this.userList = res.data.data;
                    console.log(res.data.data)
                }
            }).catch(() => {
                this.selectLoading = false
            });
        },
        select(selection, row) {
            this.selectList = selection;
        },
        selectAll(selection) {
            this.selectList = selection;
        },
        selectChange(selection) {
            this.selectList = selection;
        },
        pageChange(current) {
            this.page.current = current;
            this.page.size = this.size;
            this.getCouponRecordList();
        },
        pageSizeChange(size) {
            this.page.current = 1;
            this.page.size = size;
            this.getCouponRecordList();
        },
        dateChange(item) { //选择领取时间
            this.couponDto.startTime = item[0] == null ? null : item[0];
            this.couponDto.endTime = item[1] == null ? null : item[1];
        },
        refresh() { //刷新
            this.$refs.dateSearch.handleClear()
            this.$refs.userSearchList.clearSingleSelect();
            this.couponDto = {
                userId: "",
                couponId: "",
                startTime: "",
                endTime: "",
                couponType: "",
                couponStatus: "",
            },
            this.page.current = 1;
            this.getCouponRecordList();
        },
        search() { //搜索
            this.page.current = 1;
            this.getCouponRecordList();
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