window.vm = new Vue({
    el: '#app',
    data() {
        return {
            tableHead: [],
            tableData: [],
            loading: true,
            showEdit: false,
            selectedValue: "0",
            tableHeight: 'auto',
            searchValue: "",
            newPerm: {
                id: "",
                name: "",
                value: "",
                parentId: "0"
            },
            total: 0,
            pageSizeOpts: [5, 9, 10, 20, 30, 40, 50],
            page: {
                current: 1,
                size: 9
            },
            data: []
        }
    },
    mounted() {
        this.tableHead = [
            {type: 'index', title: '序号', width: 80, align: 'center', align: 'center',},
            {title: '权限名称', minWidth: 150, key: 'name', align: 'center',},
            {title: '权限值', minWidth: 150, key: 'value', align: 'center',},
            {
                title: '创建时间', minWidth: 150, key: 'createTime', align: 'center',
            },
            {
                title: '修改时间', minWidth: 150, key: 'updateTime', align: 'center',
            },
            {
                title: '操作', minWidth: 150, key: 'action', align: 'center',
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
        this.initPermTree();
    },
    methods: {
        loadData() {
            api.get('/cms/sys/perm/page', {
                params: {
                    current: this.page.current,
                    size: this.page.size,
                    searchValue: this.searchValue
                }
            }).then((resp) => {
                let data = resp.data;
                if (data.code == 0) {
                    let page = data.data;
                    this.tableData = page.records;
                    this.total = page.total;
                    this.page.current = page.current;
                    this.loading = false;
                }
            });
        },
        initPermTree() {
            this.selectedValue = "0";
            this.data = [{
                value: '0',
                title: '无父权限'
            }]
            this.loadPermTree();
        },
        loadPermTree() {
            api.get('/cms/sys/perm/permTree', {}
            ).then((resp) => {
                let data = resp.data;
                if (data.code == 0) {
                    this.data.push(...data.data);
                }
            });
        },
        changePage(i) {
            this.loading = true;
            this.page.current = i;
            this.loadData();
        },
        pageSizeChange(size) {
            this.page.current = 1;
            this.page.size = size;
            this.loadData();
        },
        addPerm() {
            this.showEdit = true;
        },
        savePerm() {
            let name = this.newPerm.name.trim();
            let value = this.newPerm.value.trim();
            let parentId = this.newPerm.parentId;
            if (name == "") {
                this.$Message.warning('权限名称不能为空！');
                return;
            } else if (value == "") {
                this.$Message.warning('权限值不能为空！');
                return;
            } else if (parentId == "") {
                this.$Message.warning('请选择父权限！');
                return;
            }
            api.post('/cms/sys/perm/save', this.newPerm
            ).then((resp) => {
                vm.loadData();
                vm.initPermTree();
                if (resp.data.code == 0) {
                    this.$Message.success('操作成功！');
                } else {
                    this.$Message.error(resp.data.msg);
                }
            });
            this.newPerm = {name: "", value: "", parentId: "0"}
        },
        clear() {
            this.newPerm = {
                id: "",
                name: "",
                value: "",
                parentId: "0"
            },
                this.selectedValue = "0";
        },
        changeSelected(value) {
            this.newPerm.parentId = value;
        },
        edit(perm) {
            this.newPerm = {
                id: perm.id,
                name: perm.name,
                value: perm.value,
                parentId: perm.parentId
            }
            this.selectedValue = perm.parentId;
            this.showEdit = true;
        }
    }
});