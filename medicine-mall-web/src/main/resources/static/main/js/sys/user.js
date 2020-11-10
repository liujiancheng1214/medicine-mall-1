window.$vue = new Vue({
    el: '#sys_user',
    data() {
        return {
            loading: true,
            showBankCard: false,
            cardList: [],
            showEdit: false,
            showImg: false,
            showReceiptStatus: false,
            drawerTitle: "",
            accountStatus: "",
            accountShow: false,
            accountRefuse: false,
            remarkList: {
                accountRemark: "",
                accountRefuseRemark: "",
            },
            userDto: {
                userLevelId: "",
                startRegisterTime: "",
                endRegisterTime: "",
                mobile: "",
                provinceId: "",
                cityId: "",
                districtId: "",
                keyWord: ""
            },
            editItem: {},
            newItem: {
                id: "",
                helpCode: "",
                mobile: "",
                openid: "",
                password: "",
                type: 0,
                companyName: "",
                contactName: "",
                contactPhone: "",
                contactAddress: "",
                provinceId: "",
                cityId: "",
                districtId: "",
                taxNo: "",
                invoiceType: "",
                userLevelId: ""
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
            ruleValidate: {
                mobile: [
                    {required: true, message: '当前项为必填项，不能为空', trigger: 'blur'},
                    {pattern: /^1[3456789]\d{9}$/, message: "手机号码格式不正确", trigger: "blur"}
                ],
                contactPhone: [
                    {pattern: /^1[3456789]\d{9}$/, message: "手机号码格式不正确", trigger: "blur"}
                ],
            },
            addressData: [],
            addressList: [],
            searchAData: [],
            typeList: [{value: '0', label: '普通客户'}, {value: '1', label: 'PLUS客户'}],
            invoiceList: [{value: '0', label: '普通发票'}, {value: '1', label: '电子发票'}],
            receiptStatusList: [{value: '0', label: '已符合要求'}, {value: '1', label: '未符合要求'}],
            imgList: [],
            levelList: [],
            searchLevelList: [],
            model1: '',
            model2: '',
            model3: '',
            model4: '',
            model5: '',
            showInvoice: false,
            userItem: {
                userId: 0,
                userName: "",
                address: "",
                taxNo: '',
                invoiceType: '',
                receiptStatus: '',
            },
            pageSizeOpts: [5, 10, 20, 30, 40, 50],
            page: {current: 1, size: 5},
            userList: [],
            selectList: [],
            total: 0,
            searchValue: '',
            importExcel_modal: false,
            size: 0,
            current: 0,
            pages: 0,
            label1: (h) => {
                return h('div', [
                    h('span', '已开户'),
                    h('Badge', {
                        props: {
                            count: this.tab1,
                            showZero: true
                        },
                        style: {
                            marginLeft: "5px",
                        }
                    })
                ])
            },
            label2: (h) => {
                return h('div', [
                    h('span', '未开户'),
                    h('Badge', {
                        props: {
                            count: this.tab2,
                            showZero: true
                        },
                        style: {
                            marginLeft: "5px",
                        }
                    })
                ])
            },
            tab1: 0,
            tab2: 0,
            cardHead: [
                {
                    title: '序号',
                    type: 'index',
                    minWidth: 80,
                    align: 'center',
                },
                {
                    title: '银行卡类型',
                    key: 'bankName',
                    minWidth: 160,
                    align: 'center',
                },
                {
                    title: '银行卡号',
                    minWidth: 160,
                    key: 'bankCardNo',
                    align: 'center',
                },
                {
                    title: '开户人姓名',
                    key: 'bankUserName',
                    align: 'center',
                    minWidth: 160,
                },
                {
                    title: '开户人手机号',
                    key: 'bankUserMobile',
                    align: 'center',
                    minWidth: 160,
                },
            ],
            columns1: [
                {
                    title: '客户ID',
                    key: 'id',
                    minWidth: 100,
                    align: 'center',
                },
                {
                    title: '客户名称',
                    key: 'companyName',
                    minWidth: 200,
                    align: 'center',
                    render: (h, params) => {
                        let item = params.row;
                        let operates = [h('p', {style: {}}, '' + item.companyName)];
                        operates.push(h('p', {style: {}}, '手机号码：' + item.mobile));
                        operates.push(h('p', {style: {}}, '客户编码：' + item.helpCode));
                        return h('div', operates);
                    }
                },
                {
                    title: '联系地址',
                    minWidth: 200,
                    key: 'contactAddress',
                    align: 'center',
                },
                {
                    title: '联系人',
                    key: 'contactName',
                    align: 'center',
                    minWidth: 150,
                    render: (h, params) => {
                        let item = params.row;
                        let operates = [h('p', {style: {}}, '' + item.contactName)];
                        operates.push(h('p', {style: {}}, '' + item.contactPhone));
                        return h('div', operates);
                    }
                },
                {
                    title: '客户等级',
                    key: 'userLevelName',
                    align: 'center',
                    minWidth: 100,
                },
                {
                    title: '城市',
                    key: 'provinceName',
                    align: 'center',
                    minWidth: 150,
                    render: (h, params) => {
                        let item = params.row;
                        let operates = [h('p', {}, item.provinceName + item.cityName + item.districtName)];
                        return h('div', operates);
                    }
                },
                {
                    title: '注册时间',
                    key: 'createTime',
                    align: 'center',
                    minWidth: 160,
                    render: (h, params) => {
                        let time = Tools.chGMT2Str(params.row.createTime);
                        return h('span', time);
                    }
                },
                {
                    title: '相关证件',
                    align: 'center',
                    minWidth: 150,
                    render: (h, params) => {
                        let item = params.row;
                        let operates = [h('p', {
                            attrs: {class: "mouse-on"},
                            style: {
                                cursor: 'pointer',
                                color: '#69aa46',
                                fontSize: "13px",
                                marginTop: "6px",
                            },
                            on: {
                                click: function () {
                                    $vue.showImg = true;
                                    $vue.userItem.userId = item.id;
                                    $vue.userItem.userName = item.companyName;
                                    $vue.userItem.address = item.contactAddress == "" ? "暂未登记" : item.contactAddress;
                                    $vue.getImgList(item.id);
                                }
                            }
                        }, '查看/下载'), h('p', {
                            attrs: {class: "mouse-on"},
                            style: {
                                cursor: 'pointer',
                                color: '#69aa46',
                                fontSize: "13px",
                            },
                            on: {
                                click: function () {
                                    $vue.showReceiptStatus = true;
                                    $vue.userItem.userName = item.companyName;
                                    $vue.userItem.userId = item.id;
                                    $vue.userItem.receiptStatus = item.receiptStatus + "";
                                }
                            }
                        }, '收证状态'), h('p', {
                            attrs: {class: "mouse-on"},
                            style: {
                                cursor: 'pointer',
                                color: '#478fca',
                                fontSize: "13px",
                            },
                            on: {
                                click: function () {
                                    $vue.showInvoice = true;
                                    $vue.userItem.userName = item.companyName;
                                    $vue.userItem.taxNo = item.taxNo == "" ? "暂未登记" : item.taxNo;
                                    $vue.userItem.invoiceType = '发票类型：' + item.invoiceType == 0 ? "普通发票" : "电子发票";
                                }
                            }
                        }, '开票信息')];
                        operates.push(h('p', {
                            attrs: {class: "mouse-on"},
                            style: {
                                cursor: 'pointer',
                                color: '#478fca',
                                fontSize: "13px",
                                marginBottom: "6px",
                            },
                            on: {
                                click: function () {
                                    $vue.showBankCard = true;
                                    $vue.userItem.userName = item.companyName;
                                    $vue.getCardList(item.id);
                                }
                            }
                        }, '银行卡信息'))
                        return h('div', operates);
                    }
                }, {
                    title: '操作',
                    align: 'center',
                    minWidth: 150,
                    render: (h, params) => {
                        let item = params.row;
                        let add = false;
                        let action_style = '';
                        let action_text = '';
                        let type = '';
                        switch (params.row.status) {
                            case 1://删除
                                action_style = 'primary';
                                action_text = '解冻';
                                type = 0;
                                add = true;
                                break;
                            case 0://有效
                                action_style = 'error';
                                action_text = '冻结';
                                type = 1;
                                add = false;
                                break;
                            default:
                        }
                        let operates = [h('Button', {
                            props: {
                                type: action_style,
                                size: 'small',
                            },
                            style: {},
                            on: {
                                click: () => {
                                    this.accountFreeze(type, params.row)
                                }
                            }
                        }, action_text), h('Button', {
                            props: {
                                type: "info",
                                size: 'small',
                                disabled: add,
                            },
                            style: {
                                marginLeft: '8px'
                            },
                            on: {
                                click: function () {
                                    $vue.editItem = item;
                                    $vue.addressList = [item.provinceId == undefined ? null : item.provinceId + ""
                                        , item.cityId == undefined ? null : item.cityId + ""
                                        , item.districtId == undefined ? null : item.districtId + ""]
                                    $vue.edit();
                                }
                            }
                        }, '编辑')];
                        let open = [h('Button', {
                            props: {
                                type: "warning",
                                size: 'small',
                            },
                            style: {},
                            on: {
                                click: () => {
                                    $vue.accountShow = true;
                                    $vue.userItem.userName = item.companyName;
                                    $vue.userItem.userId = item.id;
                                }
                            }
                        }, "开户"), h('Button', {
                            props: {
                                type: "warning",
                                size: 'small',
                            },
                            style: {
                                marginLeft: '8px'
                            },
                            on: {
                                click: function () {
                                    $vue.accountRefuse = true;
                                    $vue.userItem.userName = item.companyName;
                                    $vue.userItem.userId = item.id;
                                }
                            }
                        }, '拒绝开户')];
                        if (item.accountStatus == 1) { //开户状态 0已开户 1未开户
                            return h('div', open);
                        }
                        return h('div', operates);
                    }
                }

            ]
        }
    },
    mounted() {
        this.getUserList();
        this.getLevelList(); //等级下拉框
        this.getAddressList() //地区下拉框
        //键盘回车执行搜索
        let my = this;
        document.onkeydown = function (e) {
            let key = window.event.keyCode;
            if (key == 13) {
                my.search();
            }
        }
    },
    components: {},
    methods: {
        getImgList(userId) { //查询相关证件
            api.get("/cms/user/img/getImg",
                {params: {userId: userId,}}
            ).then((res) => {
                if (res.data.code === 0) {
                    this.imgList = res.data.data;
                }
            })
        },
        getCardList(userId) {
            api.get("/cms/user/bankCard/list",
                {params: {userId: userId,}}
            ).then((res) => {
                if (res.data.code === 0) {
                    this.cardList = res.data.data;
                }
            })
        },
        getUserList() {
            this.$Spin.show();
            api.get("/cms/user/list",
                {
                    params: {
                        size: this.page.size,
                        current: this.page.current,
                        userLevelId: this.userDto.userLevelId,
                        startRegisterTime: this.userDto.startRegisterTime,
                        endRegisterTime: this.userDto.endRegisterTime,
                        provinceId: this.userDto.provinceIaccountStatusSubmitd,
                        cityId: this.userDto.cityId,
                        districtId: this.userDto.districtId,
                        keyWord: this.userDto.keyWord,
                        mobile: this.userDto.mobile,
                        accountStatus: this.accountStatus == "" ? 0 : this.accountStatus,
                    }
                }
            ).then((res) => {
                this.$Spin.hide();
                if (res.data.code === 0) {
                    let userData = res.data.data;
                    this.userList = userData.data.records;
                    this.total = userData.data.total;
                    this.size = userData.data.size;
                    this.current = userData.data.current;
                    this.pages = userData.data.pages;
                    this.tab1 = userData.openSize;
                    this.tab2 = userData.noOpenSize;
                }
            }).catch(() => {
                this.$Spin.hide();
            });
        },
        getLevelList() {
            api.get("/cms/level/listAll"
            ).then((res) => {
                if (res.data.code === 0) {
                    this.levelList = res.data.data;
                    this.searchLevelList = res.data.data;
                }
            }).catch(() => {
            });
        },
        getAddressList() {
            api.get("/cms/user/region"
            ).then((res) => {
                if (res.data.code === 0) {
                    this.addressData = res.data.data;
                    this.searchAData = res.data.data;
                }
            }).catch(() => {
            });
        },
        pageChange(current) {
            this.page.current = current;
            this.page.size = this.size;
            this.getUserList();
        },
        pageSizeChange(size) {
            this.page.current = 1;
            this.page.size = size;
            this.getUserList();
        },
        addItem() {
            this.showEdit = true;
            this.drawerTitle = "新增客户信息";
            this.addressList = [];
        },
        emptyImg() {
            this.showImg = false;
            this.userItem = {
                userId: 0,
                userName: "",
                address: "",
                taxNo: '',
                invoiceType: ''
            };
        },
        emptyForm() {
            this.showEdit = false;
            this.$refs["editForm"].resetFields();
            this.newItem.id = "";
            this.editItem = {};
        },
        addressChange(item) {
            this.addressList = item;
            this.newItem.provinceId = this.addressList[0] == null ? 0 : this.addressList[0];
            this.newItem.cityId = this.addressList[1] == null ? 0 : this.addressList[1];
            this.newItem.districtId = this.addressList[2] == null ? 0 : this.addressList[2];
        },
        saveItem() {
            this.$refs["editForm"].validate(valid => {
                if (valid) {
                    api.post('/cms/user/save', this.newItem).then((res) => {
                        if (res.data.code == 0) {
                            this.getUserList();
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
            this.drawerTitle = "编辑客户信息";
            this.newItem = {
                id: this.editItem.id,
                helpCode: this.editItem.helpCode,
                mobile: this.editItem.mobile,
                openid: this.editItem.openid,
                password: this.editItem.password,
                type: this.editItem.type + "",
                companyName: this.editItem.companyName,
                contactName: this.editItem.contactName,
                contactPhone: this.editItem.contactPhone,
                contactAddress: this.editItem.contactAddress,
                provinceId: this.addressList[0] == null ? 0 : this.addressList[0],
                cityId: this.addressList[1] == null ? 0 : this.addressList[1],
                districtId: this.addressList[2] == null ? 0 : this.addressList[2],
                taxNo: this.editItem.taxNo,
                invoiceType: this.editItem.invoiceType,
                userLevelId: this.editItem.userLevelId + ""
            }
            console.log(this.newItem)
        },
        accountStatusSubmit(type) {
            let content = ""
            if (type == 1) { //开户状态 0已开户 1未开户
                content = this.remarkList.accountRefuseRemark;
                if (content == "") {
                    this.loading = true,
                    this.$Message.warning("请填写备注！");
                    return;
                }
            } else {
                content = "恭喜您的账户已成功开户!" + this.remarkList.accountRemark;
            }
            api.post("/cms/user/desc/addDesc",
                {
                    userId: this.userItem.userId,
                    type: type,
                    content: content,
                }
            ).then((res) => {
                if (res.data.code === 0) {
                    this.noOpenCancel();
                    this.getUserList();
                    this.tips_success("操作成功！")
                }
            }).catch(() => {
            });
        },
        saveStatusSubmit() {
            api.post("/cms/user/update",
                {id: this.userItem.userId, receiptStatus: this.userItem.receiptStatus}
            ).then((res) => {
                if (res.data.code === 0) {
                    this.getUserList();
                    this.tips_success("操作成功！")
                }
            }).catch(() => {
            });
        },
        reset() {
            if (this.newItem.id == "" || this.newItem.id == null) {
                this.$refs["editForm"].resetFields();
            } else {
                this.edit();
            }
        },
        openCancel(){ //开户取消
            this.accountShow=false;
            this.$refs.accountFrom.resetFields();
        },
        noOpenCancel(){ //拒绝开户取消
            this.accountRefuse=false;
            this.$refs.accountRefuseFrom.resetFields();
        },
        //账号冻结  /  解冻  操作
        accountFreeze(type, user) {
            api.post("/cms/user/update",
                {id: user.id, status: type}
            ).then((res) => {
                if (res.data.code === 0) {
                    this.getUserList();
                    this.tips_success("操作成功！")
                }
            }).catch(() => {
            });
        },
        exportExcel() { //导出数据
            api.get("/cms/user/export", {responseType: 'blob'}).then((res) => {
                if (!res.data) {
                    return
                }
                let url = window.URL.createObjectURL(new Blob([res.data], {type: 'application/vnd.ms-excel'}))
                let link = document.createElement('a')
                link.style.display = 'none'
                link.href = url
                link.setAttribute('download', '客户表.xlsx')
                document.body.appendChild(link)
                link.click()
            }).catch(() => {
            });
        },
        importExcel() {
            this.importExcel_modal = true;
        },
        importExcelSubmit() { //导入数据
            let file = this.$refs.file.files;
            if (file.length == 0) {
                this.tips_warning("请先选择上传文件！")
                return;
            }
            let formData = new FormData()
            formData.append('file', file[0])
            api.post("/cms/user/read", formData
            ).then((res) => {
                if (res.data.code === 0) {
                    let suc = res.data.data.successCount;
                    let erc = res.data.data.errorCount;
                    let list = res.data.data.errorList;
                    this.$Notice.success({
                        title: '导入结果',
                        desc: '成功：' + suc + '条，失败：' + erc + '条'
                    });
                    this.getUserList();
                    if(erc > 0){
                        setTimeout(function () {
                            alert("失败数据（名称）："+list)
                        },3000)
                    }
                } else {
                    this.tips_error("操作失败！")
                }
            }).catch(() => {
            });
        },
        dateChange(item) { //选择注册时间
            this.userDto.startRegisterTime = item[0] == null ? null : item[0];
            this.userDto.endRegisterTime = item[1] == null ? null : item[1];
        },
        searchAChange(item) { //选择省市区
            console.log(item)
            this.userDto.provinceId = item[0] == null ? null : item[0];
            this.userDto.cityId = item[1] == null ? null : item[1];
            this.userDto.districtId = item[2] == null ? null : item[2];
        },
        refresh() { //刷新
            this.$refs.addressSearch.clearSelect()
            this.$refs.dateSearch.handleClear()
            this.userDto = {
                userLevelId: "",
                startRegisterTime: "",
                endRegisterTime: "",
                mobile: "",
                provinceId: "",
                cityId: "",
                districtId: "",
                keyWord: ""
            },
                this.page.current = 1;
            this.getUserList();
        },
        search() { //搜索
            this.page.current = 1;
            this.getUserList();
        },
        downLoadImg() {
            if (this.userItem.userId == 0) {
                this.$Message.warning("用户编号有误，下载失败！");
                return;
            }
            api.get("/cms/user/img/downLoadImg", {
                responseType: 'blob',
                params: {
                    userId: this.userItem.userId
                }
            }).then((res) => {
                if (!res.data) {
                    return
                }
                let url = window.URL.createObjectURL(new Blob([res.data], {type: 'application/OCTET-STREAM'}))
                let link = document.createElement('a')
                link.style.display = 'none'
                link.href = url
                link.setAttribute('download', '用户证件照片.zip')
                document.body.appendChild(link)
                link.click()
            }).catch(() => {
            });
        },
        downLoad() { //下载Excel模板
            api.get("/cms/user/downLoad", {responseType: 'blob'}).then((res) => {
                if (!res.data) {
                    return
                }
                let url = window.URL.createObjectURL(new Blob([res.data], {type: 'application/vnd.ms-excel'}))
                let link = document.createElement('a')
                link.style.display = 'none'
                link.href = url
                link.setAttribute('download', '客户表.xlsx')
                document.body.appendChild(link)
                link.click()
            }).catch(() => {
            });
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