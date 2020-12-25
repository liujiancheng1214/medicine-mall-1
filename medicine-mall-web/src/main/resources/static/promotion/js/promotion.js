window.vm = new Vue({
    el: '#app',
    data() {
        return {
            dateOptions: {
                disabledDate(date) {
                    return date && date.valueOf() < Date.now() - 86400000;
                }
            },
            ruleValidate: {
                quotaUserNum: [{required: true, type: "integer", message: '当前项必填且必须为整数', trigger: 'blur'}],
                ruleId: [{required: true, type: "integer", message: '请选择规则模板', trigger: 'change'}]
            },
            tableHead: [],
            tableData: [],
            categoryData: [],
            selectedCategory: [],
            loading: true,
            brandList: [],
            tableHeight: 'auto',
            showEdit: false,
            showInfo: false,
            selectLoading: false,
            ruleOptions: [],
            itemList: [],
            itemBatchList: [[]],
            temp: 0,
            selectBatchList: [],
            selectedRule: {},
            promotionInfo: {},
            itemOptions: [],
            conditions: {
                searchValue: "",
                tab: "0"
            },
            page: {
                current: 1,
                total: 0,
                size: 10
            },
            selectList: [],
            editPromotion: {},
            checkedIds: [],
            newPromotion: {
                promotionId: "",
                promotionName: "",
                containerBusinessLevel: "",
                shareTitle: "",
                specialBusinessPlus: "",
                useCouponState: "",
                brokerGetState: "",
                quotaBuyState: "",
                givePointState: "",
                quotaUserNum: "",
                beginTime: "",
                endTime: "",
                promotionType: "",
                ruleId: "",
                items: [{
                    itemNo: "",
                    promotionPrice: "",
                    itemNum: "",
                    totalItemType: "",
                    totalItemNum: "",
                }]
            },
            timerange: [],
            dict: {
                promotionType: {1: "团购"},
                specialBusinessPlus: {
                    1: "独享",
                    2: "非独享"
                },
                givePointState: {
                    1: "可获取",
                    2: "不可获取"
                },
                brokerGetState: {
                    1: "返佣金",
                    2: "不返佣金"
                },
                useCouponState: {
                    1: "可使用",
                    2: "不可使用"
                }
            }
        }
    },
    mounted() {
        this.tableHead = [
            {type: 'selection', width: 60, align: 'center'},
            {title: '促销名称',minWidth: 150, key: 'promotionName'},
            {
                title: '促销类型', key: 'promotionType', width: 145, align: 'center',
                render: (h, params) => {
                    let type = "";
                    switch (params.row.promotionType) {
                        case 1:
                            type = "拼团";
                            break;
                    }
                    return h('span', type);
                }
            },
            {
                title: '活动时间', key: 'beginTime', width: 220, align: 'center',
                render: (h, params) => {
                    let promotion = params.row;
                    let operates = [h('p', {style: {}}, "开始时间：" + promotion.beginTime)];
                    operates.push(h('p', {style: {}}, "结束时间：" + promotion.endTime));
                    return h('div', operates);
                }
            },
            {
                title: '允许参与客户等级',minWidth:140, key: 'containerBusinessLevel',
                render: (h, params) => {
                    let levels = params.row.containerBusinessLevel;
                    return h('div', (levels == null || levels == '' ? "无限制" : levels));
                }
            },
            {
                title: '活动设置',minWidth:145, key: 'useCouponState',
                render: (h, params) => {
                    let promotion = params.row;
                    let operates = [h('p', {style: {}}, "优惠券：" + (promotion.useCouponState == 1 ? "可以使用" : "不能使用"))];
                    operates.push(h('p', {style: {}}, "返佣金：" + (promotion.brokerGetState == 1 ? "返佣" : "不返佣")));
                    operates.push(h('p', {style: {}}, "获取积分：" + (promotion.givePointState == 1 ? "可获取" : "不可获取")));
                    operates.push(h('p', {style: {}}, "PLUS独享：" + (promotion.specialBusinessPlus == 1 ? "独享" : "非独享")));
                    operates.push(h('p', {style: {}}, "限购：" + (promotion.quotaBuyState == 1 ? promotion.quotaUserNum + "份/人" : "不限购")));
                    return h('div', operates);
                }
            },
            {
                title: '操作记录', key: 'editInfo', width: 220,
                render: (h, params) => {
                    let promotion = params.row;
                    let operates = [h('p', {style: {}}, "创建用户：" + promotion.creator)];
                    operates.push(h('p', {style: {}}, "创建时间：" + promotion.createTime));
                    operates.push(h('p', {style: {}}, "修改用户：" + promotion.updator));
                    operates.push(h('p', {style: {}}, "修改时间：" + promotion.updateTime));
                    if (promotion.auditStatus == 1) {
                        operates.push(h('p', {style: {}}, "审核员：" + promotion.auditor));
                    }
                    return h('div', operates);
                }
            },
            {
                title: '操作',minWidth:75, key: 'action', align: 'center',
                render: function (h, params) {
                    let promotion = params.row;
                    let operates = [h('p', {style: {margin: '0px'}}, [h('Button', {
                        props: {
                            type: 'text',
                            size: 'small'
                        },
                        on: {
                            click: function () {
                                vm.showPromotionInfo(promotion)
                            }
                        }
                    }, '详情')])];
                    if (promotion.auditStatus == 2) {
                        operates.push(
                            h('p', {style: {margin: '0px'}}, [h('Button', {
                                    props: {
                                        type: 'text',
                                        size: 'small'
                                    },
                                    on: {
                                        click: function () {
                                            vm.editPromotion = promotion;
                                            vm.edit();
                                        }
                                    }
                                }, '编辑')]
                            ));
                        operates.push(
                            h('p', {style: {margin: '0px'}}, [h('Button', {
                                    props: {
                                        type: 'text',
                                        size: 'small'
                                    },
                                    on: {
                                        click: function () {
                                            vm.$Modal.confirm({
                                                title: '删除活动',
                                                content: '<p>确认要作废当前活动草稿么？</p>',
                                                onOk: () => {
                                                    vm.delete([promotion.promotionId])
                                                }
                                            });
                                        }
                                    }
                                }, '作废')]
                            ));
                    }
                    if ("promotion:approve" == "promotion:approve") {
                        if (promotion.auditStatus == 2) {
                            operates.push(
                                h('p', {style: {margin: '0px'}}, [h('Button', {
                                        props: {
                                            type: 'text',
                                            size: 'small'
                                        },
                                        on: {
                                            click: function () {
                                                vm.$Modal.confirm({
                                                    title: '审核活动',
                                                    content: '<p>确认此活动通过审核么？</p>',
                                                    onOk: () => {
                                                        vm.approve(promotion.promotionId)
                                                    }
                                                });
                                            }
                                        }
                                    }, '审核')]
                                ));
                        } else {
                            operates.push(
                                h('p', {style: {margin: '0px'}}, [h('Button', {
                                        props: {
                                            type: 'text',
                                            size: 'small'
                                        },
                                        on: {
                                            click: function () {
                                                vm.$Modal.confirm({
                                                    title: '取消活动',
                                                    content: '<p>确认取消此活动么？</p>',
                                                    onOk: () => {
                                                        vm.cancel(promotion.promotionId)
                                                    }
                                                });
                                            }
                                        }
                                    }, '取消')]
                                ));
                        }
                    }
                    return h('div', operates);
                }
            }
        ];
        this.tableData = [];
        //初始化分页加载数据
        this.initLoad();
    },
    methods: {
        initLoad() {
            this.loadData();
            this.$Message.config({
                duration: 2.5
            })
        },
        loadData() {
            api.get('/cms/promotion/page', {
                params: {
                    current: this.page.current,
                    size: this.page.size,
                    conditions: JSON.stringify(this.conditions)
                }
            }).then((resp) => {
                if (resp.data.code == 0) {
                    let data = resp.data.data;
                    this.tableData = data.records;
                    this.page.total = data.total;
                    this.page.current = data.current;
                    this.loading = false;
                }
            });
        },
        changePage(i) {
            this.loading = true;
            this.page.current = i;
            this.loadData();
        },
        addPromotion() {
            this.showEdit = true
        },
        edit() {
            this.initRuleAndItem(this.editPromotion.promotionId, this.editPromotion.ruleId, this.editPromotion.promotionType);
            this.showEdit = true;
            this.newPromotion = {
                promotionId: this.editPromotion.promotionId,
                promotionName: this.editPromotion.promotionName,
                containerBusinessLevel: this.editPromotion.containerBusinessLevel,
                shareTitle: this.editPromotion.shareTitle,
                specialBusinessPlus: this.editPromotion.specialBusinessPlus + "",
                useCouponState: this.editPromotion.useCouponState + "",
                brokerGetState: this.editPromotion.brokerGetState + "",
                quotaBuyState: this.editPromotion.quotaBuyState + "",
                givePointState: this.editPromotion.givePointState + "",
                quotaUserNum: this.editPromotion.quotaUserNum,
                beginTime: this.editPromotion.beginTime,
                endTime: this.editPromotion.endTime,
                promotionType: this.editPromotion.promotionType + "",
                ruleId: this.editPromotion.ruleId,
            }
            this.timerange = [];
            this.timerange.push(this.editPromotion.beginTime)
            this.timerange.push(this.editPromotion.endTime)
        },
        reset() {
            if (this.newPromotion.promotionId == "" || this.newPromotion.promotionId == null) {
                this.emptyForm();
            } else {
                this.edit();
            }
        },
        savePromotion() {
            this.changeTimerange();
            this.$refs["editForm"].validate(valid1 => {
                this.$refs["itemsForm"].validate(valid2 => {
                    if (valid1 && valid2) {
                        api.post('/cms/promotion/save', this.newPromotion).then((resp) => {
                            if (resp.data.code == 0) {
                                this.showEdit = false;
                                this.emptyForm();
                                this.loadData();
                                this.$Message.success('操作成功！');
                            } else {
                                this.$Modal.error({
                                    title: "保存失败",
                                    content: resp.data.msg
                                });
                            }
                        });
                    }
                });

            });
        },
        emptyForm() {
            this.timerange = [];
            this.$refs["editForm"].resetFields();
            this.newPromotion.promotionId = "";
            this.editPromotion = {};
            this.ruleOptions = [];
            this.selectedRule = {};
            this.newPromotion.items = [{
                itemNo: "",
                promotionPrice: "",
                itemNum: "",
                totalItemType: "",
                totalItemNum: "",
            }];
            this.itemBatchList = [[]]
            this.$refs["itemsForm"].resetFields();
        },
        loadCategory() {
            api.get('/cms/promotion/categoryTree').then((resp) => {
                let data = resp.data
                if (data.code == 0) {
                    this.categoryData = data.data
                }
            });
        },
        getSelection(list) {
            this.selectList = list;
        },
        approve(id) {
            api.post('/cms/promotion/approve',
                Qs.stringify({
                        id: id,
                    }
                )).then((resp) => {
                if (resp.data.code == 0) {
                    this.loadData();
                    this.$Message.success('审批成功！');
                } else {
                    this.$Message.error(resp.data.msg);
                }
            });
        },
        cancel(id) {
            api.post('/cms/promotion/cancel',
                Qs.stringify({
                        id: id,
                    }
                )).then((resp) => {
                if (resp.data.code == 0) {
                    this.loadData();
                    this.$Message.success('已取消活动！');
                } else {
                    this.$Message.error(resp.data.msg);
                }
            });
        },
        batchDelete() {
            this.$Modal.confirm({
                title: '删除活动',
                content: '<p>确认要将所选活动草稿全部作废么？</p>',
                onOk: () => {
                    let ids = []
                    for (let i of this.selectList) {
                        ids.push(i.promotionId)
                    }
                    this.delete(ids);
                }
            });
        },
        delete(ids) {
            api.post('/cms/promotion/delete',
                Qs.stringify({
                        promotionIds: JSON.stringify(ids),
                    }
                )).then((resp) => {
                if (resp.data.code == 0) {
                    this.loadData();
                    this.$Message.success('当前活动已作废！');
                } else {
                    this.$Message.error(resp.data.msg);
                }
            });
        },
        changeTimerange() {
            if (this.timerange.length != 0) {
                this.newPromotion.beginTime = this.timerange[0];
                this.newPromotion.endTime = this.timerange[1];
            } else {
                this.newPromotion.beginTime = null;
                this.newPromotion.endTime = null;
            }
        },
        remotePromotion(query) {
            if (query !== '') {
                this.selectLoading = true;
                api.get('/cms/promotion/rule', {
                    params: {
                        query: query,
                        type: this.newPromotion.promotionType
                    }
                }).then((resp) => {
                    this.selectLoading = false;
                    let data = resp.data;
                    if (data.code == 0) {
                        this.ruleOptions = data.data;
                    }
                });
            }
        },
        remoteItem(query) {
            this.itemList = [];
            if (query !== '') {
                this.selectLoading = true;
                api.get('/cms/item/itemSelect', {
                    params: {
                        query: query
                    }
                }).then((resp) => {
                    this.selectLoading = false;
                    let data = resp.data;
                    if (data.code == 0) {
                        this.itemList = data.data;
                    }
                });
            }
        },
        initRuleAndItem(id, ruleId, type) {
            api.get('/cms/promotion/getRuleAndItem', {
                params: {
                    id: id,
                    ruleId: ruleId,
                    type: type
                }
            }).then((resp) => {
                let data = resp.data;
                if (data.code == 0) {
                    this.selectedRule = data.data.rule;
                    this.ruleOptions = []
                    this.ruleOptions.push(this.selectedRule);
                    let items = data.data.items;
                    this.newPromotion.items = []
                    this.newPromotion.items.push(...items)
                    if (items.length == 0) {
                        this.newPromotion.items = [{
                            itemNo: "",
                            promotionPrice: "",
                            itemNum: "",
                            totalItemType: "",
                            totalItemNum: "",
                        }];
                    }
                }
            });
        },
        changeRule(id) {
            for (let option of this.ruleOptions) {
                if (option.id == id) {
                    this.selectedRule = option
                    return;
                }
            }
        },
        // changeItem(no, index) {
        //     api.get('/cms/item/getItemBatches', {
        //         params: {
        //             no: no
        //         }
        //     }).then((resp) => {
        //         let data = resp.data;
        //         if (data.code == 0) {
        //             this.itemBatchList[index] = data.data;
        //         }
        //     });
        // },
        addItemRow() {
            this.newPromotion.items.push({
                itemNo: "",
                promotionPrice: "",
                itemNum: "",
                totalItemType: "",
                totalItemNum: "",
            })
            this.itemBatchList.push([])
            this.temp++
        },
        deleteRow(index) {
            this.newPromotion.items.splice(index, 1);
            this.temp++
        },
        changeTemp() {
            this.temp++
        },
        
        showPromotionInfo(info) {
            api.get('/cms/promotion/getRuleAndItem', {
                params: {
                    id: info.promotionId,
                    ruleId: info.ruleId,
                    type: info.promotionType
                }
            }).then((resp) => {
                let data = resp.data;
                if (data.code == 0) {
                    this.promotionInfo = info
                    this.promotionInfo.items = data.data.items
                    this.promotionInfo.rule = data.data.rule
                    this.showInfo = true;
                }
            });
        }
    }
});