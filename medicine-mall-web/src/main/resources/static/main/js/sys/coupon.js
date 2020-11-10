window.$vue = new Vue({
    el: '#sys_coupon',
    data() {
        return {
            loading1: false,
            search_modal: false,
            showEdit: false,
            drawerTitle: "",
            showAmount: false,
            editItem: {},
            couponType: "",
            couponTitle: "",
            newItem: {
                id: 0,
                title: "",
                type: "",
                discountAmount: "",
                discountLimitAmount: "",
                description: "",
                limitUserLevelIds: "",
                isHandsel: "",
                limitItenIds: "",
                limitNum: "",
                isForbidOverlayPreferential: "",
                expireNoticeDays: "",
                expireDays: "",
            },
            ruleValidate: {
                discountAmount: [
                    {required: true, type: 'number', message: '当前项为必填项，必须为数字类型', trigger: 'blur'},
                ],
                discountLimitAmount: [
                    {required: true, type: 'number', message: '当前项为必填项，必须为数字类型', trigger: 'blur'},
                ],
                expireDays: [
                    {required: true, type: 'number', message: '当前项为必填项，必须为数字类型', trigger: 'blur'},
                ],
                expireNoticeDays: [
                    {required: false, type: 'number', message: '该项必须为数字类型', trigger: 'blur'},
                ],
                limitNum: [
                    {required: false, type: 'number', message: '该项必须为数字类型', trigger: 'blur'},
                ],
            },
            typeList: [{value: '1', label: '代金券'}, {value: '2', label: '折扣券'}, {value: '3', label: '满减券'}],
            handList: [{value: '0', label: '否'}, {value: '1', label: '是'}],
            forbidList: [{value: '0', label: '否'}, {value: '1', label: '是'}],
            userList: [],
            typeList1: [{value: '0', label: '部分客户'}, {value: '1', label: '所有客户'}],
            model1: '',
            model2: '',
            model3: '',
            model4: '',
            model5: '',
            page: {current: 1, size: 7},
            pageSizeOpts: [5, 7, 10, 20, 30, 40, 50],
            couponList: [],
            selectList: [],
            showUserSelect: true,
            userIds: [],
            couponId: 0,
            sendType: "0",//发放类型 0发给部分人 1发给所有人
            total: 0,
            size: 0,
            current: 0,
            pages: 0,
            columns1: [
                {
                    title: '优惠券ID',
                    key: 'id',
                    minWidth: 100,
                    align: 'center',
                },
                {
                    title: '优惠券名',
                    key: 'title',
                    minWidth: 150,
                    align: 'center',
                    render: (h, params) => {
                        let item = params.row;
                        let operates = [h('p', {style: {}}, item.title)];
                        operates.push(h('p', {style: {}}, '已发放：' + item.budgetSendTotalQty + '张'));
                        operates.push(h('p', {style: {}}, '已使用：' + item.budgetUseTotalQty + '张'));
                        return h('div', operates);
                    }
                },
                {
                    title: '类型',
                    key: 'type',
                    align: 'center',
                    minWidth: 100,
                    render: (h, params) => {
                        let item = params.row;
                        let result = '';
                        switch (item.type) {
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
                    title: '面额',
                    key: 'discountAmount',
                    align: 'center',
                    minWidth: 150,
                    render: (h, params) => {
                        let item = params.row;
                        let operates = [h('p', {style: {}}, item.discountAmount + "元")];
                        if (item.type == 3) {
                            operates = [h('p', {style: {}}, item.discountAmount + "元（满" + item.discountLimitAmount + "元可用）")];
                        }
                        return h('div', operates);
                    }
                },
                {
                    title: '使用说明',
                    key: 'description',
                    align: 'center',
                    minWidth: 200,
                },
                {
                    title: '限制规则',
                    key: 'isHandsel',
                    align: 'center',
                    minWidth: 150,
                    render: (h, params) => {
                        let item = params.row;
                        let result = '';
                        switch (item.isHandsel) {
                            case 0:
                                result = "否";
                                break;
                            case 1:
                                result = "是";
                                break;
                            default:
                                result = "否";
                        }
                        let isForbid = '';
                        switch (item.isForbidOverlayPreferential) {
                            case 0:
                                isForbid = "否";
                                break;
                            case 1:
                                isForbid = "是";
                                break;
                            default:
                                isForbid = "否";
                        }
                        let operates = [h('p', {style: {}}, "能否转赠：" + result)];
                        operates.push(h('p', {style: {}}, "能否叠加：" + isForbid));
                        operates.push(h('p', {style: {}}, "最多可领：" + item.limitNum + "张"));
                        return h('div', operates);
                    }
                },
                {
                    title: '操作记录',
                    key: 'createTime',
                    align: 'left',
                    minWidth: 220,
                    render: (h, params) => {
                        let item = params.row;
                        let createTime = Tools.chGMT2Str(item.createTime);
                        let updateTime = Tools.chGMT2Str(item.updateTime);
                        let operates = [h('p', {style: {marginTop: '6px'}}, "创建用户：" + item.creatorName)];
                        operates.push(h('p', {style: {}}, "创建时间：" + createTime));
                        operates.push(h('p', {style: {}}, "修改用户：" + item.updatorName));
                        operates.push(h('p', {style: {marginBottom: '6px'}}, "修改时间：" + updateTime));
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
                                size: 'small',
                            },
                            style: {
                                marginLeft: '8px'
                            },
                            on: {
                                click: function () {
                                    $vue.editItem = item;
                                    $vue.edit();
                                }
                            }
                        }, '编辑'), h('Button', {
                            props: {
                                type: 'warning',
                                size: 'small',
                            },
                            style: {
                                marginLeft: '8px'
                            },
                            on: {
                                click: function () {
                                    $vue.search_modal = true;
                                    $vue.couponId = item.id
                                    $vue.couponTitle = item.title
                                    $vue.getUserList();//加载用户下拉框
                                }
                            }
                        }, '发放')];
                        return h('div', operates);
                    }
                }

            ]
        }
    },
    mounted() {
        this.getCouponList();
    },
    components: {},
    methods: {
        getCouponList() {
            this.$Spin.show();
            api.get("/cms/coupon/list",
                {
                    params: {
                        size: this.page.size,
                        current: this.page.current,
                        type: this.couponType == 0 ? null : this.couponType
                    }
                }
            ).then((res) => {
                this.$Spin.hide();
                if (res.data.code === 0) {
                    this.couponList = res.data.data.records;
                    this.total = res.data.data.total;
                    this.size = res.data.data.size;
                    this.current = res.data.data.current;
                    this.pages = res.data.data.pages;
                }
            }).catch(() => {
                this.$Spin.hide();
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
            this.getCouponList();
        },
        pageSizeChange(size) {
            this.page.current = 1;
            this.page.size = size;
            this.getCouponList();
        },
        addItem() {
            this.showEdit = true;
            this.drawerTitle = "新增优惠券类别";
            this.newItem.isHandsel = "0"; //默认
            this.newItem.isForbidOverlayPreferential = "0"; //默认
            this.newItem.expireDays = 15; //默认天数
            this.newItem.expireNoticeDays = 3; //默认
            this.newItem.limitNum = 5; //默认
        },
        changeUser(item) {
            this.userIds = item;
        },
        getUserList() {
            api.get("/cms/user/listSelect", {}).then((res) => {
                if (res.data.code === 0) {
                    this.userList = res.data.data;
                }
            }).catch(() => {

            });
        },
        searchSubmit() {
            if (this.couponId == 0) {
                this.tips_error("未找到优惠券类别ID")
                return;
            }
            if (this.sendType == "0") {
                if (this.userIds.length <= 0) {
                    this.tips_warning("请选择客户")
                    return;
                }
            }
            //询问框
            this.$Modal.confirm({
                title: '安全提示',
                content: '为确保安全，请确认是否发放？',
                onOk: () => {
                    //发放优惠券
                    api.post('/cms/coupon/addRecord', {
                        userIds: this.userIds,
                        couponId: this.couponId,
                        sendType: this.sendType,
                    }).then((res) => {
                        if (res.data.code == 0) {
                            this.getCouponList();
                            this.emptyFormItem();
                            this.$Message.success('操作成功！');
                        } else {
                            this.$Message.error(res.data.msg);
                        }
                    });
                },
                onCancel: () => {

                }
            });
        },
        changeType(item) {
            if (item == "0") {
                this.showUserSelect = true;
            } else if (item == "1") {
                this.showUserSelect = false;
                this.userIds = [];
            }
        },
        refresh() { //刷新
            this.page.current = 1;
            this.getCouponList();
        },
        typeChange(item) {
            if (item == "1") {
                this.showAmount = false;
            } else if (item == "2") {
                this.showAmount = false;
            } else if (item == "3") {
                this.showAmount = true;
            }
        },
        cancel() {
            this.emptyFormItem();
        },
        emptyFormItem() {
            this.search_modal = false;
            if(this.sendType == "0"){
                this.$refs.userSearchList.clearSingleSelect();
            }
            this.sendType = "0";
        },
        emptyForm() {
            this.showEdit = false;
            this.showAmount = false,
            this.$refs["editForm"].resetFields();
            this.newItem.id = 0;
            this.editItem = {};
        },
        saveItem() {
            this.$refs["editForm"].validate(valid => {
                if (valid) {
                    this.newItem.type = parseInt(this.newItem.type);
                    this.newItem.isForbidOverlayPreferential = parseInt(this.newItem.isForbidOverlayPreferential);
                    this.newItem.isHandsel = parseInt(this.newItem.isHandsel);
                    this.newItem.id = this.newItem.id == '' ? 0 : this.newItem.id
                    this.newItem.discountLimitAmount = this.newItem.discountLimitAmount == '' ? 0 : this.newItem.discountLimitAmount
                    api.post('/cms/coupon/save', this.newItem).then((res) => {
                        if (res.data.code == 0) {
                            this.getCouponList();
                            this.emptyForm();
                            this.$Message.success('操作成功！');
                        } else {
                            this.$Message.error(res.data.msg);
                        }
                    });
                }
            });
        },
        edit() {
            this.showEdit = true;
            this.drawerTitle = "编辑优惠券类别";
            if (this.editItem.type == 1) {
                this.showAmount = false;
            } else if (this.editItem.type == 2) {
                this.showAmount = false;
            } else if (this.editItem.type == 3) {
                this.showAmount = true;
            }
            this.newItem = {
                id: this.editItem.id,
                title: this.editItem.title,
                type: this.editItem.type + "",
                discountAmount: this.editItem.discountAmount,
                discountLimitAmount: this.editItem.discountLimitAmount,
                description: this.editItem.description,
                limitUserLevelIds: this.editItem.limitUserLevelIds,
                isHandsel: this.editItem.isHandsel + "",
                limitItenIds: this.editItem.limitItenIds,
                limitNum: this.editItem.limitNum,
                isForbidOverlayPreferential: this.editItem.isForbidOverlayPreferential + "",
                expireNoticeDays: this.editItem.expireNoticeDays,
                expireDays: this.editItem.expireDays
            }
        },
        reset() {
            if (this.newItem.id == 0) {
                this.$refs["editForm"].resetFields();
            } else {
                this.edit();
            }
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