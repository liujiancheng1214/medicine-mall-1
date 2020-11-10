window.$vue = new Vue({
    el: '#refund_reason_list',
    data() {
        return {
            page:{
                current: 1,
                size: 10,
                total: 0
            },
            searchVar:'',
            newReason:{},
            currentReason:{},
            loading:false,
            addReason_modal:false,
            editReason_modal:false,
            pageSizeOpts:[8,9,10,13,15,20],
            dataList:[],
            ruleValidate: {
                name: [
                    {required: true, message: '名称不能为空', trigger: 'change'}
                ],
                value: [
                    {required: true, message: '值不能为空', trigger: 'change'},
                ],
                sort: [
                    {required: true, message: '不能为空', trigger: 'change'}
                ]
            },
            tableHead: [
                {
                    type: 'selection',
                    minWidth: 20,
                    align: 'center'
                },
                {
                    title: '名称',
                    align: 'center',
                    key: 'name',
                    minWidth: 150
                },
                {
                    title: '值',
                    align: 'center',
                    key: 'value',
                    minWidth: 50
                },
                {
                    title: '排序',
                    align: 'center',
                    key: 'sort',
                    minWidth: 50
                },
                {
                    title: '是否多选',
                    align: 'center',
                    minWidth: 100,
                    render: (h, params) => {
                        let text = '-';
                        if (params.row.isMultiple === 1){
                            text = '多选';
                        }
                        if (params.row.isMultiple === 2){
                            text = '单选';
                        }
                        return [h('div', [h('p', {}, text)])];
                    }
                },
                {
                    title: '是否启用',
                    align: 'center',
                    key: 'name',
                    minWidth: 100,
                    render: (h, params) => {
                        let text = '启用';
                        if (params.row.isDeleted === 1){
                            text = '禁用';
                        }
                        return [h('div', [h('p', {}, text)])];
                    }
                },
                {
                    title: '备注',
                    align: 'center',
                    render: (h, params) => {
                        return [h('div', [h('p', {}, params.row.remark)])];
                    }
                },
                {
                    title: '操作',
                    align: 'center',
                    render: (h, params) => {
                        let del = params.row.isDeleted;
                        let operates = [h('Button', {props: {type: 'primary',size: 'small'},style: {},
                            on: {
                                click: () =>  {
                                    this.currentReason = params.row;
                                    this.editReason_modal = true;
                                }
                            }
                        }, '编辑'),
                            h('Button', {props: {type: del===1?'info':'error', size: 'small'}, style: {marginLeft: '5px'},
                            on: {
                                click: () => {
                                    this.changeDeleteStatus(params.row.id, params.row.isDeleted)
                                }
                            }
                        }, del===1?'启用':'禁用')
                        ];
                        return h('div', operates);
                    }
                }
            ]
        }
    },
    mounted() {
        this.getRefundReasonList();

    },
    components: {
    },
    methods: {
        tips_warning (msgContent) {
            this.$Message.warning(msgContent, 3);
        },
        tips_cancel (msgContent) {
            this.$Message.info(msgContent, 3);
        },
        tips_success (msgContent) {
            this.$Message.success(msgContent, 3);
        },
        tips_error (msgContent) {
            this.$Message.error(msgContent, 3);
        },
        getRefundReasonList(){
            //由于 退款原因要维护一个列表。且包含搜索功能。所以这里并没有走 sys dict.公共的接口。走的是自己独立的查库接口。
            //但这里数据编辑后 会调用 sys_dict  reinit 方法 让系统 字典重新读库。
            this.loading = true;
            api.get("/cms/refund/refundReasonPage", {params:{
                    "current": this.page.current,
                    "size": this.page.size,
                    "search": this.searchVar
                }}).then((res) => {
                if (res.data.code === 0) {
                    let result = res.data.data;
                    this.dataList = result.records;
                    this.page.total = result.total;
                    this.page.current = result.current;
                }
            });
            this.loading = false;
        },
        refreshAdminSysDict(){
            //刷新  后台 内存里的 字典。使其重新去数据库获取一遍
            api.get("/sys/reInitSysDict", {}).then((res) => {
                // if (res.data.code === 0) {
                // }
            });
        },
        search(){
            this.page.current = 1;
            this.getRefundReasonList();
        },
        addReasonSubmit(){
            if (this.checkForm()===false){
                this.tips_error("请输入必填项")
                return;
            }
            api.post("/cms/refund/addRefundReason", this.newReason).then((res) => {
                if (res.data.code === 0) {
                    this.tips_success("操作成功");
                    this.refreshAdminSysDict();
                    this.getRefundReasonList();
                    this.addReason_modal = false;
                }else {
                    this.tips_error(res.data.msg)
                }
            });
            //清空表单验证
            // this.$refs['addReasonForm'].resetFields();
        },
        editReasonSubmit(){
            let obj = {};
            obj.id = this.currentReason.id;
            obj.name = this.currentReason.name;
            obj.value = this.currentReason.value;
            obj.sort = this.currentReason.sort;
            obj.remark = this.currentReason.remark;
            api.post('/cms/refund/updateRefundReason', obj).then((res) => {
                if (res.data.code === 0) {
                    this.tips_success("操作成功");
                    this.refreshAdminSysDict();
                    this.getRefundReasonList();
                    this.editReason_modal = false;
                    this.currentReason = {};
                }else {
                    this.tips_error(res.data.msg)
                }
            });
        },
        changeDeleteStatus(id,del){
            let url = '/cms/refund/deleteRefundReason';
            if (del===1){
                url = '/cms/refund/unDeleteRefundReason';
            }
            api.post(url, {id:id}).then((res) => {
                if (res.data.code === 0) {
                    this.tips_success("操作成功");
                    this.refreshAdminSysDict();
                    this.getRefundReasonList();
                }else {
                    this.tips_error(res.data.msg)
                }
            });
        },
        checkForm(){
            this.$refs['addReasonForm'].validate((valid) => {
                console.log(valid);
                return valid;
            });
        },
        pageChange (current){
            this.page.current = current;
            this.getRefundReasonList();
        },
        pageSizeChange(size){
            this.page.current = 1;
            this.page.size = size;
            this.getRefundReasonList();
        },
        // 日期格式转换
        timeFormat(time) {
            if (time) {
                let date = new Date(time);

                // 年
                let year = date.getFullYear();
                // 月
                let month = date.getMonth() + 1;
                // 日
                let day = date.getDate();
                // 时
                let hh = date.getHours();
                // 分
                let mm = date.getMinutes();
                // 秒
                let ss = date.getSeconds();
                // 结果
                let datetime = year + "-";

                if (month < 10) {
                    datetime += "0";
                }
                datetime += month + "-";

                if (day < 10) {
                    datetime += "0";
                }
                datetime += day + " ";

                if (hh < 10) {
                    datetime += "0";
                }
                datetime += hh + ":";

                if (mm < 10) {
                    datetime += '0';
                }
                datetime += mm + ":";

                if (ss < 10) {
                    datetime += '0';
                }
                datetime += ss;
                return datetime;
            }
            return null;
        }
    }
});
