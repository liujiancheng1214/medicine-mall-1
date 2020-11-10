window.vm = new Vue({
    el: '#app',
    data() {
        return {
            tableHead: [],
            tableData: [],
            loading: true,
            tableHeight: 'auto',
            showEdit: false,
            searchValue: "",
            page: {
                current: 1,
                total: 0,
                size: 10
            },
            data: [{
                title: "所有权限",
                expand: false,
                children: []
            }],
            defData: [{
                title: "所有权限",
                expand: false,
                children: []
            }],
            newRole: {
                id: "",
                roleName: "",
                perms: [],
            },
            nodes: []
        }
    },
    mounted() {
        this.loadPermTree();
        this.tableHead = [
            {type: 'index', title: '序号', width: 80, align: 'center'},
            {title: '角色名称', minWidth: 160,key: 'roleName',align: 'center',},
            {
                title: '创建时间', minWidth: 160,key: 'createTime',align: 'center',
            },
            {
                title: '修改时间', minWidth: 160,key: 'updateTime',align: 'center',
            },
            {
                title: '操作', minWidth: 150,key: 'action', align: 'center',
                render: function (h, params) {
                    let operates = [h('Button', {
                        props: {
                            type: 'info',
                            size: 'small'
                        },
                        style: {
                            marginLeft: '8px'
                        },
                        on: {
                            click: function () {
                                vm.edit(params.row);
                            }
                        }
                    }, '编辑')];
                    return h('div', operates);
                }
            }
        ];
        this.tableData = [];
        //初始化分页加载数据
        this.loadData();
    },
    methods: {
        loadData() {
            api.get('/cms/sys/role/page', {
                params: {
                    current: this.page.current,
                    size: this.page.size,
                    searchValue: this.searchValue
                }
            }).then((resp) => {
                let data = resp.data.data;
                this.tableData = data.records;
                this.page.total = data.total;
                this.page.current = data.current;
                this.loading = false;
            });
        },
        loadPermTree() {
            api.get('/cms/sys/perm/permTree', {}
            ).then((resp) => {
                let data = resp.data;
                if (data.code == 0) {
                    this.data[0].children = data.data;
                    this.defData[0].children = JSON.parse(JSON.stringify(data.data));
                }
            });
        },
        changePage(i) {
            this.loading = true;
            this.page.current = i;
            this.loadData();
        },
        addRole() {
            this.showEdit = true;
        },
        clear() {
            this.data = JSON.parse(JSON.stringify(this.defData));
            this.newRole = {
                id: "",
                roleName: "",
                perms: [],
            }
        },
        saveRole() {
            let name = this.newRole.roleName.trim();
            if (name == "") {
                this.$Message.warning('角色名称不能为空！');
                return;
            }
            this.nodes.forEach(nodes => {
                let id = nodes.value;
                if (id != null) {
                    this.newRole.perms.push(id);
                }
            })
            api.post('/cms/sys/role/save', this.newRole).then((resp) => {
                this.newRole = {
                    id: "",
                    roleName: "",
                    perms: [],
                }
                this.data = JSON.parse(JSON.stringify(this.defData));
                vm.loadData();
                if (resp.data.code == 0) {`
                    this.$Message.success('操作成功！');`
                } else {
                    this.$Message.error(resp.data.msg);
                }
            });
        },
        checked() {
            this.nodes = this.$refs.tree.getCheckedAndIndeterminateNodes();
        },
        edit(role) {
            let roleId = role.id;
            this.newRole = {
                id: roleId,
                roleName: role.roleName,
                perms: [],
            }
            api.get('/cms/sys/role/perms', {
                params: {
                    roleId: roleId
                }
            }).then((resp) => {
                let data = resp.data;
                if (data.code == 0) {
                    this.newRole.perms = data.data;
                    let datum = this.data[0];
                    console.log(datum)
                    this.initChecked(datum.children)
                    Vue.set(datum, "expand", true)
                    this.showEdit = true;
                }
            });
        },
        initChecked(list) {
            list.forEach(node => {
                let children = node.children;
                if (this.newRole.perms.indexOf(node.value) >= 0) {
                    if (children != null && children.length != 0) {
                        node.expand = true
                        this.initChecked(children);
                    } else {
                        node.checked = true;
                    }
                }
            })
        }
    }
});
