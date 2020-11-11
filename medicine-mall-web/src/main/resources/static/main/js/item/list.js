window.vm = new Vue({
    el: '#app',
    data() {
        return {
            tableHead: [],
            tableData: [],
            categoryData: [],
            selectedCategory: [],
            loading: true,
            brandList: [],
            tableHeight: 'auto',
            showEdit: false,
            conditions: {
                searchValue: "",
                tab: "0"
            },
            ruleValidate: {
                retailPrice: [{required: true, type: "number", message: '当前项必须为数字且不能为空', trigger: 'blur'}],
                startPurchaseAmount: [{required: true, type: "number", message: '当前项必须为数字且不能为空', trigger: 'blur'}],
                bigPackage: [{required: true, type: "number", message: '当前项必须为数字且不能为空', trigger: 'blur'}],
                expiryDate: [{required: true, type: "number", message: '当前项必须为数字且不能为空', trigger: 'blur'}],
                revolveDay: [{required: false, type: "number", message: '当前项必须为数字', trigger: 'blur'}],
                costPrice: [{required: false, type: "number", message: '当前项必须为数字', trigger: 'blur'}],
                inTax: [{required: false, type: "number", message: '当前项必须为数字', trigger: 'blur'}],
                outTax: [{required: false, type: "number", message: '当前项必须为数字', trigger: 'blur'}],
                itemBrandId: [{required: true, type: "number", message: '请选择选项', trigger: 'blur'}],
                important: [{required: true, message: '请选择选项', trigger: 'blur'}],
                businessModel: [{required: true, message: '请选择选项', trigger: 'blur'}],
                stopBuy: [{required: true, message: '请选择选项', trigger: 'blur'}],
                itemCategoryId: [{required: true, type: "number", message: '请选择选项', trigger: 'blur'}]
            },
            batchRuleValidate: {},
            page: {
                current: 1,
                total: 0,
                size: 10
            },
            selectList: [],
            editItem: {},
            checkedIds: [],
            newItem: {
                id: "",
                itemName: "",
                itemBrandId: "",
                itemCategoryId: "",
                helpCode: "",
                itemNo: "",
                imgCover: null,
                imgFront: null,
                imgReverse: null,
                approvalNo: "",
                approvalNoEndAt: "",
                factory: "",
                originPlace: "",
                regNo: "",
                regNoEndAt: "",
                spec: "",
                unit: "",
                bigPackage: "",
                mediumPackage: "",
                remark: "",
                retailPrice: "",
                platformPrice: "",
                startPurchaseAmount: "",
                qty: "",
                licenseHolder: "",
                expiryDate: "",
                storageMode: "",
                tags: "",
                effect: "",
                forPeople: "",
                saleStrategy: "",
                important: "",
                revolveDay: "",
                inTax: "",
                outTax: "",
                stopBuy: "",
                businessModel: "",
                itemBatch: []
            }
        }
    },
    mounted() {
        this.tableHead = [
            {type: 'selection', width: 60, align: 'center'},
            {
                title: '商品显示名称',minWidth:180, key: 'itemName',
                render: (h, params) => {
                    let item = params.row;
                    let operates = [];
                    if (item.imgCover != null && item.imgCover != "") {
                        operates.push(h('img', {
                                attrs: {
                                    src: item.imgCover
                                },
                                style: {
                                    height:'60px',
                                    width:'60px'
                                }
                            },
                            ""
                        ));
                    }
                    operates.push(h('span', {style: {}}, item.itemName));
                    return h('div', operates);
                }
            },
            {
                title: '商品信息',minWidth:200, key: 'itemInfo',
                render: (h, params) => {
                    let item = params.row;
                    let operates = [h('p', {style: {}}, '批准文号：' + item.approvalNo)];
                    operates.push(h('p', {style: {}}, '商品名称：' + item.itemName));
                    operates.push(h('p', {style: {}}, '厂家：' + item.factory));
                    operates.push(h('p', {style: {}}, '商品规格：' + item.spec));
                    operates.push(h('p', {style: {}}, '计量规格：' + item.bigPackage));
                    operates.push(h('p', {style: {}}, '商品编码：' + item.itemNo));
                    return h('div', operates);
                }
            },
            {
                title: '价格', width: 160, key: 'price', render: (h, params) => {
                    let item = params.row;
                    let operates = [h('p', {style: {}}, '零售价格：' + item.retailPrice)];
                    operates.push(h('p', {style: {}}, '起购价格：' + item.startPurchaseAmount));
                    return h('div', operates);
                }
            },
            {
                title: '销售', width: 220, key: 'sell',
                render: (h, params) => {
                    let item = params.row;
                    let operates = [h('p', {style: {}}, '总销售次数：' + (item.subtotalSaleTimes == null ? 0 : item.subtotalSaleTimes))];
                    operates.push(h('p', {style: {}}, '总销售数量：' + (item.subtotalSaleNum == null ? 0 : item.subtotalSaleNum)));
                    operates.push(h('p', {style: {}}, '总销售金额：' + (item.subtotalSettAmount == null ? 0 : item.subtotalSettAmount)));
                    return h('div', operates);
                }
            },
            {
                title: '库存', key: 'qty', width: 110,
                render: (h, params) => {
                    let item = params.row;
                    return h('span', item.qty + item.unit);
                }
            },
            {
                title: '操作',minWidth:100, key: 'action', align: 'center',
                render: function (h, params) {
                    let item = params.row;
                    let operates = [h('Button', {
                        props: {
                            type: 'text',
                            size: 'small'
                        },
                        style: {
                            marginLeft: '8px'
                        },
                        on: {
                            click: function () {
                                vm.editItem = item;
                                vm.edit();
                            }
                        }
                    }, '编辑')];
                    if (item.state == 0) {
                        operates.push(
                            h('Button', {
                                props: {
                                    type: 'text',
                                    size: 'small'
                                },
                                style: {
                                    marginLeft: '8px'
                                },
                                on: {
                                    click: function () {
                                        vm.changeStateOne(item)
                                    }
                                }
                            }, '下架')
                        );
                    } else if (item.state == 1) {
                        operates.push(
                            h('Button', {
                                props: {
                                    type: 'text',
                                    size: 'small'
                                },
                                style: {
                                    marginLeft: '8px'
                                },
                                on: {
                                    click: function () {
                                        vm.changeStateOne(item)
                                    }
                                }
                            }, '上架')
                        );
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
            this.loadBrand();
            this.loadCategory();
            this.$Message.config({
                duration: 2.5
            })
        },
        loadData() {
            api.get('/cms/item/page', {
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
        loadBrand() {
            api.get('/cms/item/brandList').then((resp) => {
                let data = resp.data;
                if (data.code == 0) {
                    this.brandList = data.data
                }
            });
        },
        changePage(i) {
            this.loading = true;
            this.page.current = i;
            this.loadData();
        },
        edit() {
            this.showEdit = true;
            this.newItem = {
                id: this.editItem.id,
                imgCover: this.editItem.imgCover,
                imgFront: this.editItem.imgFront,
                imgReverse: this.editItem.imgReverse,
                itemName: this.editItem.itemName,
                itemBrandId: this.editItem.itemBrandId,
                itemCategoryId: this.editItem.itemCategoryId,
                helpCode: this.editItem.helpCode,
                itemNo: this.editItem.itemNo,
                approvalNo: this.editItem.approvalNo,
                approvalNoEndAt: this.editItem.approvalNoEndAt,
                factory: this.editItem.factory,
                originPlace: this.editItem.originPlace,
                regNo: this.editItem.regNo,
                regNoEndAt: this.editItem.regNoEndAt,
                spec: this.editItem.spec,
                unit: this.editItem.unit,
                bigPackage: this.editItem.bigPackage,
                mediumPackage: this.editItem.mediumPackage,
                remark: this.editItem.remark,
                retailPrice: this.editItem.retailPrice,
                platformPrice: this.editItem.platformPrice,
                erpId: this.editItem.erpId,
                startPurchaseAmount: this.editItem.startPurchaseAmount,
                qty: this.editItem.qty,
                licenseHolder: this.editItem.licenseHolder,
                expiryDate: this.editItem.expiryDate,
                storageMode: this.editItem.storageMode,
                tags: this.editItem.tags,
                effect: this.editItem.effect,
                forPeople: this.editItem.forPeople,
                saleStrategy: this.editItem.saleStrategy,
                important: this.editItem.important == null ? null : this.editItem.important + "",
                revolveDay: this.editItem.revolveDay,
                inTax: this.editItem.inTax,
                outTax: this.editItem.outTax,
                stopBuy: this.editItem.stopBuy + "",
                businessModel: this.editItem.businessModel == null ? null : this.editItem.businessModel + "",
                itemBatch: []
            }
            this.newItem.itemBatch.push(...this.editItem.itemBatch)
        },
        reset() {
            if (this.newItem.id == "" || this.newItem.id == null) {
                this.emptyForm();
            } else {
                this.edit();
            }
        },
        saveItem() {
            this.$refs["editForm"].validate(valid1 =>{
                this.$refs["batchForm"].validate(valid2 => {
                    if (valid1 && valid2) {
                        api.post('/cms/item/save', this.newItem).then((resp) => {
                            if (resp.data.code == 0) {
                                this.showEdit = false
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
            this.$refs.cascader.$refs.panel.activePath = []
            this.$refs["editForm"].resetFields();
            this.newItem.id = "";
            this.editItem = {};
            this.newItem.itemBatch = [];
        },
        changeStateOne(item) {
            let state = 1 - this.conditions.tab
            if (state == 0 && item.qty <= 0) {
                this.$Modal.warning({
                    title: "商品上架",
                    content: "库存为0的商品不能上架，请确认商品"
                });
                return;
            }
            this.changeState([item.id], state);
        },
        batchChangeState() {
            let itemIds = []
            let state = 1 - this.conditions.tab
            for (let i of this.selectList) {
                if (state == 0 && i.qty <= 0) {
                    this.$Modal.warning({
                        title: "商品上架",
                        content: "库存为0的商品不能上架，请确认商品"
                    });
                    return;
                }
                itemIds.push(i.id)
            }
            this.changeState(itemIds, state);
        },
        changeState(itemIds, state) {
            api.post('/cms/item/changeState',
                Qs.stringify({
                        itemIds: JSON.stringify(itemIds),
                        state: state
                    }
                )).then((resp) => {
                if (resp.data.code == 0) {
                    this.loadData();
                    this.$Message.success('状态修改成功！');
                } else {
                    this.$Message.error(resp.data.msg);
                }
            });
        },
        loadCategory() {
            api.get('/cms/item/categoryTree').then((resp) => {
                let data = resp.data
                if (data.code == 0) {
                    this.categoryData = data.data
                }
            });
        },
        getSelection(list) {
            this.selectList = list;
        },
        uploadImg(file, img) {
            let formData = new FormData();
            formData.append("file", file);
            upload.post('/file/fileUpload', formData).then((res) => {
                if (img == "Cover") {
                    this.newItem.imgCover = res.data.data;
                } else if (img == "Front") {
                    this.newItem.imgFront = res.data.data;
                } else if (img == "Reverse") {
                    this.newItem.imgReverse = res.data.data;
                }
            }).catch((error) => {
                console.log(error);
            });
            return false;
        },
        imgRemove(img) {
            if (img == "Cover") {
                this.newItem.imgCover = null;
            } else if (img == "Front") {
                this.newItem.imgFront = null;
            } else if (img == "Reverse") {
                this.newItem.imgReverse = null;
            }
        },
        exportItem(type) {
            let ids = null
            if (type == '1') {
                let temp = []
                this.selectList.forEach(i => temp.push(i.id));
                ids = JSON.stringify(temp);
            }
            download.get('/cms/item/export', {
                params: {
                    itemIds: ids,
                    state: this.conditions.tab
                }
            }).then((resp) => {
                if (resp.status == 200) {
                    this.downFile(resp, '商品详细信息.xls')
                } else {
                    console.log(resp)
                }
            });
        },
        importTemplate() {
            this.$Modal.info({
                title: "模板说明",
                content: "请不要随意改动模板格式，红色标题为必填项，建议单次导入excel行数不要超过5000行！"
            });
            download.get('/cms/item/template').then((resp) => {
                if (resp.status == 200&&resp.data.code==null) {
                    this.downFile(resp, '商品导入模板.xls')
                } else {
                    console.log(resp)
                }
            });
        },
        downFile(resp, fileName) {
            const blob = new Blob([resp.data], {type: 'application/vnd.ms-excel'}) // 构造一个blob对象来处理数据
            const elink = document.createElement('a') // 创建a标签
            elink.download = fileName // a标签添加属性
            elink.style.display = 'none'
            elink.href = URL.createObjectURL(blob)
            document.body.appendChild(elink)
            elink.click() // 执行下载
            URL.revokeObjectURL(elink.href) // 释放URL 对象
            document.body.removeChild(elink) // 释放标签
        },
        importData(file) {
            let formData = new FormData();
            formData.append("file", file);
            api.post('/cms/item/import', formData).then((res) => {
                if (res.data.code == 0) {
                    this.$Message.success('导入成功！');
                } else {
                    this.$Modal.error({
                        title: "导入失败",
                        content: res.data.msg
                    });
                }
            }).catch((error) => {
                console.log(error)
            });
            return false;
        },
        validateTest(rule, value, callback){
            if (/^(-?\d+)(\.\d+)?$/.test(value)) {
                callback();
            } else {
                return callback(new Error("当前项必须为数字"));
            }
        },
        addBatchRow() {
            this.newItem.itemBatch.push({
                itemId: "",
                producedTime: "",
                qty: ""
            })
        },
        deleteRow(index) {
            this.newItem.itemBatch.splice(index, 1);
        }
    }
});
