window.$vue = new Vue({
    el: '#sys_promotionGroup',
    data() {
        return {
            showEdit: false,
            drawerTitle: "",
            minSuccessTitle: "最低参团人数",
            dwTitle: '人',
            showOnly: true,
            editItem: {},
            newItem: {
                id: 0,
                groupName: "",
                groupType: "1",
                onlyNew: "1",
                groupCondition: "1",
                minSuccessNum: "",
                maxGroupNum: -1,
                leaderRebate: 0,
                groupStatus: "1",
                rule: "",
            },
            ruleValidate: {
                maxGroupNum: [
                    {required: true, type: 'number', message: '当前项为必填项，必须为数字类型', trigger: 'blur'},
                ],
                minSuccessNum: [
                    {required: false, type: 'number', message: '该项必须为数字类型', trigger: 'blur'},
                ],
                leaderRebate: [
                    {required: false, type: 'number', message: '该项必须为数字类型', trigger: 'blur'},
                ],
            },
            typeList: [{value: '1', label: '老带新团'}, {value: '2', label: '中团'}, {value: '3', label: '阶梯团'}],
            onlyNewList: [{value: '1', label: '只能邀请新客户'}, {value: '2', label: '可邀请老客户'}],
            groupConditionList: [{value: '1', label: '按参团人数'}, {value: '2', label: '按成交数量'}],
            statusList: [{value: '1', label: '可使用'}, {value: '2', label: '不可用'}],
            model1: '',
            model2: '',
            model3: '',
            model4: '',
            page: {current: 1, size: 5},
            pageSizeOpts: [5, 10, 20, 30, 40, 50],
            promotionGroupList: [],
            selectList: [],
            total: 0,
            size: 0,
            current: 0,
            pages: 0,
            columns1: [
                {
                    title: '模板编号',
                    key: 'id',
                    minWidth: 100,
                    align: 'center',
                },
                {
                    title: '模板名称',
                    key: 'groupName',
                    align: 'center',
                    minWidth: 150,
                    render: (h, params) => {
                        let item = params.row;
                        let operates = [h('p', {style: {}}, item.groupName)];
                        return h('div', operates);
                    }
                },
                {
                    title: '规则描述',
                    key: 'rule',
                    align: 'center',
                    minWidth: 200,
                },
                {
                    title: '基本信息',
                    key: 'groupType',
                    align: 'center',
                    minWidth: 150,
                    render: (h, params) => {
                        let item = params.row;
                        let result = '';
                        switch (item.groupType) {
                            case 1:
                                result = "团类型：老带新团";
                                break;
                            case 2:
                                result = "团类型：中团";
                                break;
                            case 3:
                                result = "团类型：阶梯团";
                                break;
                            default:
                                result = "团类型：老带新团";
                        }
                        let operates = [h('p', {style: {marginTop: '6px'}}, result)];
                        switch (item.groupType) {
                            case 1:
                            if (item.onlyNew == 1) {
                                operates.push([h('p', {style: {}}, "（只能邀请新客户）")]);
                            } else {
                                operates.push([h('p', {style: {}}, "（可邀请老客户）")]);
                            }
                            break;
                        }
                        if (item.maxGroupNum == -1) {
                            operates.push([h('p', {style: {}}, "最大开团数量：" + item.maxGroupNum)]);
                            operates.push([h('p', {style: {}}, "（-1代表不限制）")]);
                        } else {
                            operates.push([h('p', {style: {}}, "最大开团数量：" + item.maxGroupNum + "个")]);
                        }
                        operates.push([h('p', {style: {marginBottom: '6px'}}, "团长返利金额：" + item.leaderRebate + "元")]);
                        return h('div', operates);
                    }
                },
                {
                    title: '成团条件',
                    key: 'groupCondition',
                    align: 'center',
                    minWidth: 150,
                    render: (h, params) => {
                        let item = params.row;
                        let result = '';
                        switch (item.groupCondition) {
                            case 1:
                                result = "按参团人数";
                                break;
                            case 2:
                                result = "按成交数量";
                                break;
                            default:
                                result = "按参团人数";
                        }
                        let operates = [h('p', {style: {}}, result)];
                        switch (item.groupCondition) {
                            case 1:
                                operates.push([h('p', {style: {}}, "（最低参团" + item.minSuccessNum + "人）")])
                                break;
                            case 2:
                                operates.push([h('p', {style: {}}, "（最低购买" + item.minSuccessNum + "件）")])
                                break;
                            default:
                                operates.push([h('p', {style: {}}, "（最低参团" + item.minSuccessNum + "人）")])
                        }
                        return h('div', operates);
                    }
                },
                {
                    title: '模板状态',
                    key: 'groupStatus',
                    align: 'center',
                    minWidth: 100,
                    render: (h, params) => {
                        let item = params.row;
                        let operates = '';
                        switch (item.groupStatus) {
                            case 1:
                                operates = [h('p', {style: {color:'green'}}, "可使用")];
                                break;
                            case 2:
                                operates = [h('p', {style: {color:'red'}}, "不可用")];
                                break;
                            default:
                                operates = [h('p', {style: {color:'green'}}, "可使用")];
                        }
                        return h('div', operates);
                    }
                },
                {
                    title: '创建时间',
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
                {
                    title: '操作',
                    align: 'center',
                    minWidth: 150,
                    render: (h, params) => {
                        let item = params.row;
                        let operates = [h('Button', {
                            props: {
                                type: 'error',
                                size: 'small',
                            },
                            style: {
                                marginLeft: '8px'
                            },
                            on: {
                                click: function () {
                                    $vue.delete(item)
                                }
                            }
                        }, '删除'), h('Button', {
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
                                    if (item.groupType == 1) {
                                        $vue.showOnly = true;
                                    } else {
                                        $vue.showOnly = false;
                                    }
                                    if (item.groupCondition == 1) {
                                        $vue.minSuccessTitle = '最低参团人数';
                                        $vue.dwTitle = '人';
                                    } else {
                                        $vue.minSuccessTitle = '最低购买数量';
                                        $vue.dwTitle = '件';
                                    }
                                    $vue.edit();
                                }
                            }
                        }, "编辑")];
                        return h('div', operates);
                    }
                }

            ]
        }
    },
    mounted() {
        this.getPromotionGroupList();
    },
    components: {},
    methods: {
        getPromotionGroupList() {
            this.$Spin.show();
            api.get("/cms/promotion/group/list",
                {
                    params: {
                        size: this.page.size,
                        current: this.page.current,
                    }
                }
            ).then((res) => {
                this.$Spin.hide();
                if (res.data.code === 0) {
                    this.promotionGroupList = res.data.data.records;
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
            this.getPromotionGroupList();
        },
        pageSizeChange(size) {
            this.page.current = 1;
            this.page.size = size;
            this.getPromotionGroupList();
        },
        delete(item) {
            //询问框
            this.$Modal.confirm({
                title: '提示',
                content: '一旦删除数据将无法恢复，确认要删除吗？',
                onOk: () => {
                    api.post("/cms/promotion/group/delete", {id: item.id}
                    ).then((res) => {
                        if (res.data.code === 0) {
                            this.getPromotionGroupList();
                            this.tips_success("操作成功！");
                        } else {
                            this.tips_error("操作失败！ " + res.data.msg)
                        }
                    }).catch(() => {
                    });
                },
                onCancel: () => {

                }
            });
        },
        addGroup() {
            showOnly: true,
            this.showEdit = true;
            this.drawerTitle = "新增模板";
            this.newItem = {
                id: 0,
                groupName: "",
                groupType: "1",
                onlyNew: "1",
                groupCondition: "1",
                minSuccessNum: "",
                maxGroupNum: -1,
                leaderRebate: 0,
                groupStatus: "1",
                rule: "",
            }
            this.minSuccessTitle = '最低参团人数';
            this.dwTitle = '人';
        },
        refresh() { //刷新
            this.page.current = 1;
            this.getPromotionGroupList();
        },
        typeChange(item) {
            if (item == "1") {
                this.showOnly = true;
            } else {
                this.showOnly = false;
            }
        },
        groupConditionChange(item) {
            if (item == "1") {
                this.minSuccessTitle = '最低参团人数';
                this.dwTitle = '人';
            } else {
                this.minSuccessTitle = '最低购买数量';
                this.dwTitle = '件';
            }
        },
        saveItem() {
            this.$refs["editForm"].validate(valid => {
                if (valid) {
                    this.newItem.groupType = parseInt(this.newItem.groupType);
                    this.newItem.groupStatus = parseInt(this.newItem.groupStatus);
                    this.newItem.groupCondition = parseInt(this.newItem.groupCondition);
                    this.newItem.onlyNew = parseInt(this.newItem.onlyNew);
                    this.newItem.minSuccessNum = this.newItem.minSuccessNum == "" ? 0 : this.newItem.minSuccessNum;
                    api.post('/cms/promotion/group/save', this.newItem).then((res) => {
                        if (res.data.code == 0) {
                            this.getPromotionGroupList();
                            this.emptyForm();
                            this.$Message.success('操作成功！');
                        } else {
                            this.$Message.error(res.data.msg);
                        }
                    })
                }
            });
        },
        emptyForm() {
            this.showEdit = false;
            this.$refs["editForm"].resetFields();
            this.newItem.id = 0;
            this.editItem = {};
        },
        edit() {
            this.showEdit = true;
            this.drawerTitle = "编辑模板";
            this.newItem = {
                id: this.editItem.id,
                groupName: this.editItem.groupName,
                groupType: this.editItem.groupType + "",
                onlyNew: this.editItem.onlyNew + "",
                groupCondition: this.editItem.groupCondition + "",
                minSuccessNum: this.editItem.minSuccessNum,
                maxGroupNum: this.editItem.maxGroupNum,
                leaderRebate: this.editItem.leaderRebate,
                groupStatus: this.editItem.groupStatus + "",
                rule: this.editItem.rule,
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