window.vm = new Vue({
    el: '#category',
    data() {
        return {
            treeData:[],
            selectItem:{},
            tableHeight: 'auto',
            searchValue:'',
            categoryEdit_modal:false,
            addCategory_modal:false,
            pageSizeOpts:[8,9,10,13,15,20],
            selectItem:{},
            isNewItem:false,
            isAddSub:false,
            newCategory:{},
            tableData:[],
            loading:false,
            page: {
                current: 1,
                total: 0,
                size: 10
            },
            tableHead : [
                { type: 'selection', width: 50, align: 'center'},
                {title: 'ID', key: 'id'},
                {title: '父级ID', key: 'parentId'},
                {title: '类目名称', key: 'categoryName'},
                {title: '类目编码', key: 'categoryNo'},
                {title: '类目图片', key: 'categoryImg',
                    render: (h, params) => {
                        return h('img',{
                            attrs:{
                                src:params.row.categoryImg
                            },
                            style: {
                                display: 'inline-block',
                                width: '40px',
                                marginRight: '120px'
                            }
                        },'');
                    }
                },
                {title: '排序', key: 'sort',width: 60},
                {
                    title: '创建时间', key: 'createTime',
                    render: (h, params) => {
                        let time = params.row.createTime;
                        return h('span', time);
                    }
                },
                {
                    title: '修改时间', key: 'updateTime',
                    render: (h, params) => {
                        let time = params.row.updateTime;
                        return h('span', time);
                    }
                },
                {title: '状态', key: 'isDeleted',width: 60,
                    render:(h,params)=>{
                        return h('div', params.row.isDeleted===0?'启用':'停用');
                    }
                },
                {
                    title: '操作', key: 'action', align: 'center',
                    width: 250,
                    align: 'center',
                    render: (h, params)=> {
                        let item = params.row;
                        let add = false;
                        let delete_type = 'error';
                        let delete_text = '禁用';
                        if (params.row.isDeleted === 1){
                            delete_type = 'primary';
                            delete_text = '启用';
                            add = true;
                        }

                        let operates = [h('Button', {
                            props: {
                                type: delete_type,
                                size: 'small'
                            },
                            style: {
                                // marginLeft: '5px'
                            },
                            on: {
                                click: () => {
                                    this.changeCategoryStatus(item.id, item.isDeleted)
                                }
                            }
                        }, delete_text),h('Button', {
                            props: {
                                type: 'info',
                                size: 'small'
                                // disabled:add
                            },
                            style: {
                                marginLeft: '5px'
                            },
                            on: {
                                click: () =>  {
                                    this.showDetail(item,null);
                                }
                            }
                        }, '编辑'),h('Button', {
                            props: {
                                type: 'info',
                                size: 'small',
                                disabled:add
                            },
                            style: {
                                marginLeft: '5px'
                            },
                            on: {
                                click: () =>  {
                                    this.selectItem = item;
                                    this.addCategory_modal = true;
                                    this.isAddSub = true;
                                }
                            }
                        }, '添加子类目')];
                        return h('div', operates);
                    }
                }
            ]
        }
    },
    mounted() {
        this.querySubCategory();
        this.queryCategoryListData();
    },
    methods: {
        loadData (item, callback) {
            let parentId = item.id || 0;
            let data = [];
            api.get('/cms/category/sub',{params:{parentId:parentId}}).then((res) => {
                if (res.data.code === 0) {
                    data = this.getTree(res.data.data);
                    callback(data)
                } else {
                    this.$Notice.error({
                        title: '错误',
                        desc: res.msg
                    })
                }
            }).catch(error => {
                this.$Notice.error({
                    title: '错误',
                    desc: '网络连接错误'
                });
                console.log(error)
            })
        },
        getTree (tree = []) {
            let arr = [];
            if (tree.length !== 0) {
                tree.forEach(item => {
                    let obj = {};
                    obj.categoryName = item.categoryName;
                    obj.id = item.id; // 其他你想要添加的属性
                    obj.isDeleted = item.isDeleted; // 其他你想要添加的属性
                    obj.sort = item.sort; // 其他你想要添加的属性
                    obj.categoryImg = item.categoryImg; // 其他你想要添加的属性
                    obj.categoryNo = item.categoryNo; // 其他你想要添加的属性
                    obj.parentId = item.parentId; // 其他你想要添加的属性
                    if(item.child !== 0) {
                        obj.children = [];
                        obj.loading = false;
                    }
                    arr.push(obj);
                });
            }
            return arr
        },
        renderContent (h, { root, node, data }) {
            return h('span', {
                style: {
                    display: 'inline-block',
                    width: '100%'
                }
            }, [
                h('span',[
                    h('Icon', {
                        style: {
                            marginRight: '8px'
                        }
                    }),
                    h('span', data.categoryName)
                ]),
                h('span', {
                    style: {
                        display: 'inline-block',
                        float: 'right',
                        marginRight: '32px'
                    }
                }, [
                    h('span', {
                        style: {
                            // display: 'inline-block',
                            // 'text-align': 'center',
                            width: '25px',
                            marginRight: '140px'
                        }
                    }, "编码："+data.categoryNo),
                    h('span', {
                        style: {
                            // display: 'inline-block',
                            // 'text-align': 'center',
                            width: '50px',
                            marginRight: '80px'
                        }
                    }, "排序："+data.sort),
                    h('img', {
                        attrs:{
                            src:data.categoryImg
                        },
                        style: {
                            // display: 'inline-block',
                            width: '15px',
                            marginRight: '100px'
                        }
                    }, ''),
                    h('Button', {
                        props: Object.assign({},  {
                            type: 'info',
                            size: 'small',
                        }),
                        style: {
                            marginRight: '8px'
                        },
                        on: {
                            click: () => {
                                this.selectItem = data;
                                this.isAddSub = true;
                                this.addCategory_modal = true;
                            }
                        }
                    }, '添加子类目'),
                    h('Button', {
                        props: Object.assign({},  {
                            type: 'info',
                            size: 'small',
                        }),
                        style: {
                            marginRight: '8px'
                        },
                        on: {
                            click: () => {
                                this.selectItem = data;
                                this.categoryEdit_modal = true;
                            }
                        }
                    }, '编辑'),
                    h('Button', {
                        props: Object.assign({},  {
                            type: data.isDeleted === 1 ? 'info' : 'error' ,
                            size: 'small',
                        }),
                        on: {
                            click: () => { this.changeCategoryStatus(data.id, data.isDeleted) }
                        }
                    }, data.isDeleted === 1 ? '启用' : '停用' )
                ])
            ]);
        },
        // 页面加载后 查询parentID=0 的分类 (重新加载 tree )
        querySubCategory () {
            api.get('/cms/category/sub',{params:{parentId:0}}).then((res) => {
                console.log(res);
                if (res.data.code === 0) {
                    // 这里给节点赋值
                    this.treeData = this.getTree(res.data.data)
                    this.$forceUpdate();
                } else {
                    this.$Notice.error({
                        title: '错误',
                        desc: res.msg
                    })
                }
            }).catch(error => {
                this.$Notice.error({
                    title: '错误',
                    desc: '网络连接错误'
                });
                console.log(error)
            })
        },
        categoryEditSubmit(){
            let category = {};
            category.id = this.selectItem.id;
            category.categoryNo = this.selectItem.categoryNo;
            category.categoryName = this.selectItem.categoryName;
            category.sort = this.selectItem.sort;
            category.categoryImg = this.selectItem.categoryImg;
            if (!category.categoryNo || !category.categoryName || !category.sort){
                this.tips_error("请将编码、名称或排序填写完整");
                return;
            }
            api.post('/cms/category/update', category).then((resp) => {
                if (resp.data.code === 0){
                    this.tips_success("操作成功！");
                    this.newCategory={};
                    this.categoryEdit_modal = false;
                    this.querySubCategory();
                    this.queryCategoryListData();
                }else {
                    this.tips_error(resp.data.msg)
                }
            });
            this.querySubCategory();
            this.queryCategoryListData();
        },
        changeCategoryStatus(id,status){
            debugger;
            let url = "/cms/category/delete";
            if (status === 1){//如果是 删除的
                url = "/cms/category/undelete";
            }
            api.post(url,{id:id}).then((resp) => {
                if (resp.data.code === 0){
                    this.tips_success("操作成功！");
                    this.querySubCategory();
                    this.queryCategoryListData();
                }else {
                    this.tips_error(resp.data.msg)
                }
            });
        },
        addCategorySubmit(){
            this.newCategory.parentId = this.selectItem.id;
            if (!this.newCategory.categoryNo || !this.newCategory.categoryName || !this.newCategory.sort){
                this.tips_error("请将编码、名称或排序填写完整");
                return;
            }
            api.post('/cms/category/add', this.newCategory).then((resp) => {
                if (resp.data.code === 0){
                    this.tips_success("操作成功！");
                    this.newCategory={};
                    this.addCategory_modal = false;
                }else {
                    this.tips_error(resp.data.msg)
                }
            });
            this.querySubCategory();
        },
        search(){
            this.page.current = 1;
            this.queryCategoryListData();
        },
        //加载列表数据
        queryCategoryListData() {
            api.get('/cms/category/page', {
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
            this.$forceUpdate();
        },
        changePage(i) {
            this.loading = true;
            this.page.current = i;
            this.queryCategoryListData();
        },
        pageSizeChange(size){
            this.page.current = 1;
            this.page.size = size;
            this.queryCategoryListData();
        },
        showDetail(currentRow,oldCurrentRow){
            this.selectItem = currentRow;
            this.categoryEdit_modal = true;
        },
        tips_warning (msg_content) {
            this.$Message.warning(msg_content);
        },
        tips_info (msg_content) {
            this.$Message.info(msg_content);
        },
        tips_success (msg_content) {
            this.$Message.success(msg_content);
        },
        tips_error (msg_content) {
            this.$Message.error(msg_content);
        },
        uploadFile(file){
            let formData = new FormData();
            formData.append("file", file);
            upload.post('/file/fileUpload',formData).then((res)=>{
                debugger;
                let fileUrl = res.data.data
                if (this.isNewItem){
                    this.newCategory.categoryImg = fileUrl;
                }
                if (!this.isNewItem){
                    this.selectItem.categoryImg = fileUrl;
                }
                this.$forceUpdate();
            }).catch((error)=>{
                console.log(error);
            });
            return false;
        },
        clickUpLoad(p){
            if (p==='add'){
                this.isNewItem = true;
            }
            if (p==='edit'){
                this.isNewItem = false;
            }
        }
    }
});
