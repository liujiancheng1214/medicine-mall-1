
window.vm=new Vue({
    el:'#moduleTreeVue',
    data:function() {
        return {
            data: [
                {
                    id:-1,
                    title: '用户树',
                    expand: true,
                    children: [
                        {
                            title: '教师',
                            id:'tea',
                            // expand: true,
                            children: [
                                {
                                    title: '教师状态',
                                    id: 'tea_status',
                                    loading: false,
                                    children: []
                                },
                                {
                                    title: '教师部门',
                                    id: 'tea_dept',
                                    loading: false,
                                    children: []
                                }
                            ]
                        },
                        {
                            title: '学生',
                            id:'stu',
                            //expand: true,
                            loading: false,
                            children: []
                        }
                    ]
                }
            ],
            indeterminate: false,
            checkAll: false,
            userList: [],
            userData:[]
        }
    },
    methods: {
        //提示消息弹层
        ModalMsg:function(type,str,fun) {
            this.$Modal[type]({
                title: '提示信息',
                content: str,
                onOk:fun!=undefined?fun:function(){}
            });
        },
        getSelectedNodes:function () {
            let _this = this;
            let nodes = _this.$refs.tree.getSelectedNodes(); //方法的运用 getSelectedNodes也是如此用法
            //console.log(nodes);
            if (nodes.length > 0) {
                let node = nodes[0];
                if (node.selected == true) {
                    api.get(baseUrl+'/base-api/web/userTree/getUserList', {
                        params: {
                            id: node.id
                        }
                    }).then(function (resp) {
                        _this.userData = resp.data.data;
                        let parentWin=window.parent;//获得对应iframe的window对象
                        let arr = parentWin.vm.userList;
                        let obj=new Array();
                        for (let i in arr) {
                            obj[i] = JSON.stringify(arr[i])
                        }
                        _this.userList = obj;
                    });
                }
            }
        },
        loadData (item, callback) {
            //console.log(item);
            let _this = this;
            //加载子节点
            api.get(baseUrl+'/base-api/web/userTree/getTree', {
                params: {
                    id: item.id
                }
            }).then(function (resp) {
                let list = resp.data.data;
                const array = [];
                for (let i = 0; i < list.length; i++) {
                    let item = list[i]
                    array.push(item = {id: item.id, title: item.title, loading: false, children: [], source: item})
                    if (item.source.hasChildren == 0) {
                        delete item.children;
                        delete item.loading;
                    }
                }
                //console.log(array);
                callback(array);
            });
        },
        handleCheckAll () {
            let _this = this;
            if (_this.indeterminate) {
                _this.checkAll = false;
            } else {
                _this.checkAll = !_this.checkAll;
            }
            _this.indeterminate = false;

            if (_this.checkAll) {
                let arr = _this.userData;
                let obj=new Array();
                for (let i in arr) {
                    obj[i] = JSON.stringify(arr[i])
                }
                _this.userList = obj;
            } else {
                _this.userList = [];
            }
            _this.returnJson(_this.userList);
        },
        checkAllGroupChange (data) {
            let _this = this;
            if (data.length === _this.userData.length) {
                _this.indeterminate = false;
                _this.checkAll = true;
            } else if (data.length > 0) {
                _this.indeterminate = true;
                _this.checkAll = false;
            } else {
                _this.indeterminate = false;
                _this.checkAll = false;
            }
            _this.returnJson(_this.userList);
        },
        /*将数组元素字符串转换成JSON*/
        returnJson(arr){
            let obj=new Array();
            let userName = [];
            for(let i in arr){
                obj[i]=JSON.parse(arr[i]);
                userName.push(obj[i].userName);
            }
            let parentWin = window.parent;//获得对应iframe的window对象
            parentWin.vm.userList = obj;
            parentWin.vm.addFormItem.userNo = userName;
        }
    }

});