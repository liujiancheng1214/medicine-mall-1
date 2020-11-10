window.$vue = new Vue({
    el: '#sys_admin',
    data() {
        return {
            collapsed: false,
            addUser_modal: false,
            changeRole_modal: false,
            loading: true,
            pageSizeOpts: [5, 10, 20, 30, 40, 50],
            // allRolesData: this.getMockData(),
            // adminRolesData: this.getTargetKeys(),
            allRolesData: [],
            adminRolesData: [],
            listStyle: {
                width: '250px',
                height: '300px'
            },
            newUser: {
                account: '',
                mobile: '',
                name: ''
            },
            ruleValidate: {
                account: [
                    {required: true, message: '账号不能为空', trigger: 'blur'}
                ],
                mobile: [
                    {required: true, message: '手机号码不能为空', trigger: 'blur'},
                    {pattern: /^1[3456789]\d{9}$/, message: "手机号码格式不正确", trigger: "blur"}
                ],
                name: [
                    {required: true, message: '名称不能为空', trigger: 'blur'}
                ]
            },
            page: {current: 1, size: 10},
            userList: [],
            selectList: [],
            total: 0,
            searchValue: '',
            targetUser: '',
            reSetPassword_modal: false,
            size: 0,
            current: 0,
            pages: 0,
            columns1: [
                {
                    title: '全选',
                    type: 'selection',
                    width: 50,
                    align: 'center',
                },
                {
                    title: 'ID',
                    key: 'id',
                    align: 'center',
                    minWidth: 80,
                },
                {
                    title: '账号',
                    key: 'account',
                    align: 'center',
                    minWidth: 150,
                },
                {
                    title: '手机号码',
                    key: 'mobile',
                    minWidth: 150,
                    align: 'center',
                },
                {
                    title: '名称',
                    key: 'name',
                    align: 'center',
                    minWidth: 150,
                },
                // {
                //     title: '头像图片地址',
                //     key: 'headImg'
                // },
                // {
                //     title: '创建时间',
                //     key: 'createTime',
                //     render (row, column, index) {
                //         return `<strong>${row.name}</strong>`;
                //     }
                // },
                // {
                //     title: '更新时间',
                //     key: 'updateTime'
                // },
                // {
                //     title: '最后登录时间',
                //     key: 'lastLoginTime'
                // },
                {
                    title: '账户状态',
                    key: 'status',
                    align: 'center',
                    minWidth: 100,
                    render: (h, params) => {
                        let result = '';
                        switch (params.row.status) {
                            case 1:
                                result = "删除";
                                break;
                            case 2:
                                result = "冻结";
                                break;
                            default:
                                result = "有效";
                        }
                        return h('div', [
                            h('p', {}, result)
                        ]);
                    }
                },
                {
                    title: '操作',
                    minWidth: 100,
                    align: 'center',
                    render: (h, params) => {
                        let action_style = '';
                        let action_text = '';
                        let type = '';
                        switch (params.row.status) {
                            case 1://删除
                                action_style = 'primary';
                                action_text = '解冻';
                                type = 0;
                                break;
                            case 2://冻结
                                action_style = 'primary';
                                action_text = '解冻';
                                type = 0;
                                break;
                            case 0://有效
                                action_style = 'error';
                                action_text = '冻结';
                                type = 1;
                                break;
                            default:
                        }
                        return h('div', [
                            h('Button', {
                                props: {
                                    type: action_style,
                                    size: 'small'
                                },
                                style: {
                                    marginRight: '5px'
                                },
                                on: {
                                    click: () => {
                                        this.accountFreeze(type, params.row)
                                    }
                                }
                            }, action_text)
                        ]);
                    }
                }

            ]
        }
    },
    mounted() {
        // userSession.permissionValue
        this.getUserList();
    },
    components: {
        // IForm: Form,
        // IFormItem: FormItem,
        // IInput: Input,
        // IButton: Button,
        // ISelect: Select,
        // IOption: Option,
        // ITable: Table,
        // IPage: Page
    },
    methods: {
        getUserList() {
            api.get("/cms/admin/list",
                {params: {size: this.page.size, current: this.page.current, search: this.searchValue}}
            ).then((res) => {
                if (res.data.code === 0) {
                    this.userList = res.data.data.records;
                    this.total = res.data.data.total;
                    this.size = res.data.data.size;
                    this.current = res.data.data.current;
                    this.pages = res.data.data.pages;

                }
            }).catch(() => {
            });
        },
        select(selection, row) {
            this.selectList = selection;
            console.log(selection);
        },
        selectAll(selection) {
            this.selectList = selection;
            console.log(selection);
        },
        selectChange(selection) {
            this.selectList = selection;
            console.log(selection);
        },
        show(index) {
            this.$Modal.info({
                title: '用户信息',
                content: `姓名：${this.userList[index].name}<br>手机号：${this.userList[index].mobile}`
            })
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
        reSetPassword() {
            if (this.selectList.length === 0) {
                this.tips_warning("请选中一个用户");
                return;
            }
            if (this.selectList.length >= 2) {
                this.tips_warning("只能选中一个用户进行操作");
                return;
            }
            this.targetUser = this.selectList[0];
            this.reSetPassword_modal = true;
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
        //账号冻结  /  解冻  操作
        accountFreeze(type, user) {
            let url = "";
            switch (type) {
                case 1://冻结
                    url = "/cms/admin/freeze";
                    break;
                case 0://解冻
                    url = "/cms/admin/unfreeze";
                    break;
                default:
            }
            api.post(url,
                {adminId: user.id}
            ).then((res) => {
                if (res.data.code === 0) {
                    this.getUserList();
                    this.tips_success("操作成功！")
                }
            }).catch(() => {
            });
        },
        reSetPasswordSubmit() {
            api.post("/cms/admin/restPassword",
                {adminId: this.targetUser.id}
            ).then((res) => {
                if (res.data.code === 0) {
                    this.tips_success("操作成功！")
                } else {
                    this.tips_error("操作失败！")
                }
                this.cleanSelect();
            }).catch(() => {
            });
        },
        search() {
            this.page.current = 1;
            this.getUserList();
        },
        cleanSelect() {
            this.selectList = [];
            this.targetUser = {};
            this.$refs.usertable.selectAll(false)
        },
        addUserSubmit(type) {
            this.$nextTick(() => {
                this.loading = true;
            });
            if (type) { //提交
                if (!this.newUser.name || !this.newUser.account || !this.newUser.mobile) {
                    this.tips_error("请填写必填项");
                    return;
                }
                api.post("/cms/admin/save",
                    this.newUser
                ).then((res) => {
                    if (res.data.code === 0) {
                        this.tips_success("操作成功！");
                        this.newUser = {account: '', mobile: '', name: ''};
                        this.addUser_modal = false;
                    } else {
                        this.tips_error("操作失败！ 错误信息：" + res.data.msg)
                    }
                }).catch(() => {
                });
                return;
            }
            //清空对象数据
            this.newUser = {account: '', mobile: '', name: ''};
        },
        changeRoleSubmit() {
            let adminDto = {adminId: this.targetUser.id, adminRoles: this.adminRolesData};
            api.post("/cms/admin/role/save",
                adminDto
            ).then((res) => {
                if (res.data.code === 0) {
                    this.tips_success("操作成功！");
                    this.cleanSelect();
                } else {
                    this.tips_error("操作失败！ 错误信息：" + res.data.msg)
                }
            }).catch(() => {
            });
            this.changeRole_modal = false;
        },
        getAdminRole() {
            if (this.selectList.length === 0) {
                this.tips_warning("请选中一个用户");
                return;
            }
            if (this.selectList.length >= 2) {
                this.tips_warning("只能选中一个用户进行操作");
                return;
            }
            this.targetUser = this.selectList[0];
            this.reloadRoleData();
        },
        handleChange3(newTargetKeys) {
            this.adminRolesData = newTargetKeys;
        },
        render3(item) {//穿梭框 里的 item 显示数据的组成方式。
            return item.label + ' - ' + item.description;
        },
        reloadRoleData() {
            this.adminRolesData = [];
            this.allRolesData = [];
            api.get("/cms/admin/role",
                {params: {adminId: this.targetUser.id}}
            ).then((res) => {
                if (res.data.code === 0) {
                    let adminRoles = res.data.data.adminRoles;
                    let allRoles = res.data.data.allRoles;

                    for (let i = 0; i < adminRoles.length; i++) {
                        this.adminRolesData[i] = adminRoles[i].id.toString();
                    }
                    for (let i = 0; i < allRoles.length; i++) {
                        this.allRolesData.push({
                            key: allRoles[i].id.toString(),
                            label: '角色：' + i,
                            description: allRoles[i].roleName,
                            disabled: false
                        });
                    }
                    this.changeRole_modal = true;
                } else {
                    this.tips_error("操作失败！ 错误信息：" + res.data.msg)
                }
            }).catch(() => {
            });
        }

    }
});