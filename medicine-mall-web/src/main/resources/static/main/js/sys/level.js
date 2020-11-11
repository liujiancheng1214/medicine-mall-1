window.$vue = new Vue({
    el: '#sys_level',
    data() {
        return {
            addLevel_modal: false,
            loading: true,
            editItem: {},
            newLevel: {
                id: 0,
                levelName: '',
                levelValue: '',
                icon: ''
            },
            ruleValidate: {
                levelValue: [
                    {required: true, type: "number", message: '当前项为必填项，必须是数字类型', trigger: 'blur'},
                ],
            },
            levelTitle: "新增等级信息",
            page: {current: 1, size: 10},
            pageSizeOpts: [5, 10, 20, 30, 40, 50],
            levelList: [],
            selectList: [],
            total: 0,
            targetLevel: '',
            reSetPassword_modal: false,
            size: 0,
            current: 0,
            pages: 0,
            columns1: [
                {
                    title: '等级编号',
                    key: 'id',
                    minWidth: 100,
                    align: 'center',
                },
                {
                    title: '等级名称',
                    key: 'levelName',
                    align: 'center',
                    minWidth: 250,
                },
                {
                    title: '等级值',
                    key: 'levelValue',
                    align: 'center',
                    minWidth: 250,
                },
                {
                    title: '图标',
                    key: 'icon',
                    align: 'center',
                    minWidth: 250,
                    render: (h, params) => {
                        return h('img', {
                            attrs: {
                                src: params.row.icon
                            },
                            style: {
                                display: 'inline-block',
                                width: '30px',
                                borderRadius: '5px',
                                verticalAlign: 'middle'
                            }
                        }, '');
                    }
                },
                {
                    title: '操作',
                    align: 'center',
                    minWidth: 200,
                    render: (h, params) => {
                        let item = params.row;
                        let operates = [h('Button', {
                            props: {
                                type: 'error',
                                size: 'small'
                            },
                            style: {
                                marginRight: '5px'
                            },
                            on: {
                                click: function () {
                                    $vue.delete(item)
                                }
                            }
                        }, '删除'), h('Button', {
                            props: {
                                type: 'info',
                                size: 'small'
                            },
                            style: {
                                marginLeft: '8px'
                            },
                            on: {
                                click: () => {
                                    $vue.editItem = item;
                                    $vue.edit();
                                }
                            }
                        }, '编辑')];
                        return h('div', operates);
                    }
                }

            ]
        }
    },
    mounted() {
        this.getLevelList();
    },
    components: {},
    methods: {
        getLevelList() {
            this.$Spin.show();
            api.get("/cms/level/list",
                {params: {size: this.page.size, current: this.page.current, search: this.searchValue}}
            ).then((res) => {
                this.$Spin.hide();
                if (res.data.code === 0) {
                    this.levelList = res.data.data.records;
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
            this.getLevelList();
        },
        pageSizeChange(size) {
            this.page.current = 1;
            this.page.size = size;
            this.getLevelList();
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
        addLevel() {
            this.addLevel_modal = true;
            this.levelTitle = "新增等级信息";
            this.emptyItem();
        },
        saveLevelSubmit(type) {
            this.$nextTick(() => {
                this.loading = true;
            });
            if (!this.newLevel.levelName || !this.newLevel.levelValue || !this.newLevel.icon) {
                this.tips_error("必填项不能为空！");
                return;
            }
            let url = "";
            if (this.newLevel.id == 0) {
                url = "/cms/level/save";
            } else {
                url = "/cms/level/update";
            }
            api.post(url,
                this.newLevel
            ).then((res) => {
                if (res.data.code === 0) {
                    this.tips_success("操作成功！");
                    this.getLevelList();
                    this.emptyItem();
                    this.addLevel_modal = false;
                } else {
                    this.tips_error("操作失败！ 错误信息：" + res.data.msg)
                }
            }).catch(() => {
            });
        },
        emptyItem() {
            this.$refs["addLevelForm"].resetFields();
            this.newLevel.id = 0;
        },
        edit() {
            this.addLevel_modal = true;
            this.levelTitle = "编辑等级信息";
            this.newLevel = {
                id: this.editItem.id,
                levelName: this.editItem.levelName,
                levelValue: this.editItem.levelValue,
                icon: this.editItem.icon
            }
        },
        delete(item) {
            //询问框
            this.$Modal.confirm({
                title: '提示',
                content: '一旦删除数据将无法恢复，确认要删除吗？',
                onOk: () => {
                    api.post("/cms/level/delete", {id: item.id}
                    ).then((res) => {
                        if (res.data.code === 0) {
                            this.getLevelList();
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
        uploadFile(file) {
            let formData = new FormData();
            formData.append(file.name, file);
            upload.post('/file/fileUpload', formData).then((res) => {
                let fileUrl = res.data.data
                this.newLevel.icon = fileUrl;
            }).catch((error) => {
                console.log(error);
            });
            return false;
        },
    }
});
