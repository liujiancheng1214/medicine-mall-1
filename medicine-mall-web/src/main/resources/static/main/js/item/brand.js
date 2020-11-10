window.vm = new Vue({
    el: '#brand',
    data() {
        return {
            tableData: [],
            loading: true,
            tableHeight: 'auto',
            searchValue: "",
            selectItem:{},
            addBrand_modal:false,
            isAddSub:false,
            selectItem:{},
            isNewItem:false,
            editBrand_modal:false,
            brandTree_modal:false,
            pageSizeOpts:[8,9,10,13,15,20],
            brandDetail:{},
            treeData :[],
            newBrand:{},
            tableHead : [
                { type: 'selection', width: 50, align: 'center'},
                {title: 'ID', key: 'id'},
                {title: '父级ID', key: 'parentId'},
                {title: '品牌名称', key: 'ibrandName'},
                {title: '品牌图片', render: (h, params) => {
                        return h('img',{
                            attrs:{
                                src:params.row.brandImg
                            },
                            style: {
                                display: 'inline-block',
                                width: '28px'
                                // marginRight: '120px'
                            }
                        },'');
                    }},
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
                        return h('div', params.row.isDeleted===0?'启用':'禁用');
                    }
                },
                {
                    title: '操作', key: 'action', align: 'center',
                    width: 150,
                    align: 'center',
                    render: (h, params)=> {
                        let item = params.row;
                        let delete_type = 'error';
                        let delete_text = '禁用';
                        if (params.row.isDeleted === 1){
                            delete_type = 'primary';
                            delete_text = '启用';
                        }

                        let operates = [h('Button', {
                            props: {
                                type: 'info',
                                size: 'small'
                                // disabled:true
                            },
                            style: {

                            },
                            on: {
                                click: () =>  {
                                    this.showDetail(item,null);
                                }
                            }
                        }, '编辑'),h('Button', {
                            props: {
                                type: delete_type,
                                size: 'small'
                            },
                            style: {
                                index: 100,
                                marginLeft: '5px'
                            },
                            on: {
                                click: () => {
                                    this.changeBrandStatus(item.id, item.isDeleted)
                                }
                            }
                        }, delete_text)
                        ];
                        return h('div', operates);
                    }
                }
            ],
            page: {
                current: 1,
                total: 0,
                size: 10
            }
        }
    },
    mounted() {

        this.tableData = [];
        //初始化分页加载数据
        this.brandListData();
        this.queryBrandTreeList();
    },
    methods: {
        loadData (item, callback) {
            let parentId = item.id || 0;
            let data = [];
            api.get('/cms/brand/sub',{params:{parentId:parentId}}).then((res) => {
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
                    obj.ibrandName = item.ibrandName;
                    obj.id = item.id; // 其他你想要添加的属性
                    obj.isDeleted = item.isDeleted; // 其他你想要添加的属性
                    obj.sort = item.sort; // 其他你想要添加的属性
                    obj.brandImg = item.brandImg; // 其他你想要添加的属性
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
                    h('span', data.ibrandName)
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
                            marginRight: '40px'
                        }
                    }, "排序："+data.sort),
                    h('img', {
                        attrs:{
                            src:data.brandImg
                        },
                        style: {
                            // display: 'inline-block',
                            width: '15px',
                            marginRight: '20px'
                        }
                    }, ''),
                    h('Button', {
                        props: Object.assign({},  {
                            type: 'primary',
                            size: 'small',
                        }),
                        style: {
                            marginRight: '8px'
                        },
                        on: {
                            click: () => { 
                                this.selectItem = data;
                                this.addBrand_modal = true;
                                this.isAddSub = true;
                            }
                        }
                    }, '添加'),
                    h('Button', {
                        props: Object.assign({},  {
                            type: 'primary',
                            size: 'small',
                        }),
                        style: {
                            marginRight: '8px'
                        },
                        on: {
                            click: () => { 
                                this.selectItem = data;
                                this.editBrand_modal = true;
                            }
                        }
                    }, '编辑'),
                    h('Button', {
                        props: Object.assign({},  {
                            type: data.isDeleted === 1 ? 'info' : 'error' ,
                            size: 'small',
                        }),
                        on: {
                            click: () => { this.changeBrandStatus(data.id, data.isDeleted) }
                        }
                    }, data.isDeleted === 1 ? '禁用' : '启用' )
                ])
            ]);
        },
        // 页面加载后 查询parentID=0 的分类
        queryBrandTreeList () {
            api.get('/cms/brand/sub',{params:{parentId:0}}).then((res) => {
                console.log(res);
                if (res.data.code === 0) {
                    // 这里给节点赋值
                    this.treeData = this.getTree(res.data.data)
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
        search(){
            this.page.current = 1;
            this.brandListData();
        },
        brandListData() {
            api.get('/cms/brand/page', {
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
            this.brandListData();
        },
        pageSizeChange(size){
            this.page.current = 1;
            this.page.size = size;
            this.brandListData();
        },
        addBrandSubmit (){
            if (this.isAddSub===true){
                this.newBrand.parentId = this.selectItem.id;
            }
            if (!this.newBrand.ibrandName || !this.newBrand.sort){
                this.tips_error("请将品牌名称或排序号码填写完整");
                return;
            }
            api.post('/cms/brand/add', this.newBrand).then((resp) => {
                if (resp.data.code === 0){
                    this.tips_success("操作成功！");
                    this.newBrand={};
                    this.addBrand_modal = false;
                    this.brandListData();
                    this.queryBrandTreeList();
                }else {
                    this.tips_error(resp.data.msg)
                }
            });
        },
        editBrandSubmit(){
            let brand = {};
            brand.id = this.selectItem.id;
            brand.sort = this.selectItem.sort;
            brand.ibrandName = this.selectItem.ibrandName;
            brand.brandImg = this.selectItem.brandImg;
            if (!brand.ibrandName || !brand.sort){
                this.tips_error("请将品牌名称或排序号码填写完整");
                return;
            }
            api.post('/cms/brand/update', brand).then((resp) => {
                if (resp.data.code === 0){
                    this.tips_success("操作成功！");
                    this.selectItem={};
                    this.editBrand_modal = false;
                    this.brandListData();
                    this.queryBrandTreeList();
                }else {
                    this.tips_error(resp.data.msg)
                }
            });

        },
        changeBrandStatus(id,status){
            let url = "/cms/brand/delete";
            if (status === 1){//如果是 删除的
                url = "/cms/brand/undelete";
            }
            api.post(url,{id:id}).then((resp) => {
                if (resp.data.code === 0){
                    this.tips_success("操作成功！");
                    this.brandListData();
                    this.queryBrandTreeList();
                }else {
                    this.tips_error(resp.data.msg)
                }
            });
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
        showDetail(currentRow,oldCurrentRow){
            this.selectItem = currentRow;
            this.editBrand_modal = true;
            // api.get('/cms/brand/detail',{params:{id:currentRow.id}}).then((resp) => {
            //     console.log(resp);
            //     if (resp.data.code === 0 && resp.data.data != null){
            //         this.tips_success("操作成功！");
            //         this.brandDetail = resp.data.data;
            //         this.brandTree_modal = true;
            //     }else {
            //         this.tips_error("系统错误！")
            //     }
            // });
        },
        getSelectNode(){
            let currentNode = this.$refs.brandTree.getCheckedNodes();
            currentNode[0].id;
            this.tips_info(currentNode[0].id+currentNode[0].title);
        },
        uploadFile(file){
            let formData = new FormData();
            formData.append(file.name,file);
            upload.post('/upload/uploadFile',formData).then((res)=>{
                let fileUrl = res.data.data.downloadUrl.toString();
                console.log(fileUrl);
                if (this.isNewItem){
                    this.newBrand.brandImg = fileUrl;
                }
                if (!this.isNewItem){
                    this.selectItem.brandImg = fileUrl;
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