//注册vue的组件
Vue.component("my-tree",{
    props:['users','temp'],
    created:function(){
        if(this.temp=="qingjia"){
            this.data=[
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
                    ]
                }
            ];
        }else{
            this.data=[
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
                },
            ];
        }

    },
    data:function() {
        return {
            data: [],
            indeterminate: false,
            checkAll: false,
            userList: [],
            userData:[],
            checkAllShow: false,
            len:0,
            allLen:0
        }
    },
    template:"<div><div style=\"float:left;width:40%;border:1px solid rgba(217,217,217,.5);min-height: 348px;margin: 1px;\">\n" +
        "        <Tree :data=\"data\" :load-data=\"loadData\"  ref=\"tree\" @on-select-change=\"getSelectedNodes()\"></Tree>\n" +
        "    </div>\n" +
        "    <div style=\"float:left;width:58%;border:1px solid rgba(217,217,217,.5);min-height: 348px;margin: 1px;\" >\n" +
        "         <div style=\"border-bottom: 1px solid #e9e9e9;padding-bottom:6px;margin-bottom:6px;\" v-show='checkAllShow'>\n" +
        "            <Checkbox\n" +
        "                    :indeterminate=\"indeterminate\"\n" +
        "                    :value=\"checkAll\"\n" +
        "                    @click.prevent.native=\"handleCheckAll\">全选</Checkbox>\n" +
        "        </div>\n"+
        "        <checkbox-group v-model=\"userList\" @on-change=\"checkAllGroupChange\">\n" +
        "            <checkbox v-for=\"(user, index) in userData\" :label=\"JSON.stringify(user)\" :key=\"JSON.stringify(user)\" :title=\"'编号'+user.userNo\">\n" +
        "                <span>{{user.userName}}</span>\n" +
        "            </checkbox>\n" +
        "        </checkbox-group>\n" +
        "    </div></div>",
    methods: {
        getSelectedNodes:function () {
            let _this = this;
            let nodes = _this.$refs.tree.getSelectedNodes(); //方法的运用 getSelectedNodes也是如此用法
            if (nodes.length > 0) {
                let node = nodes[0];
                if (node.selected == true) {
                    _this.nodeId = node.id;
                    api.get(baseUrl+'/base-api/web/userTree/getUserList', {
                        params: {
                            id: node.id
                        }
                    }).then(function (resp) {
                        _this.userData = resp.data.data;
                        _this.userList = _this.users;
                        _this.allLen = _this.userList.length;
                        let arr = _this.userData;
                        if (arr.length > 0) {
                            _this.checkAllShow = true;
                        } else {
                            _this.checkAllShow = false;
                        }
                        if (_this.userList.length == 0) {
                            _this.indeterminate = false;
                            _this.checkAll = false;
                        } else {
                            let obj = new Array();
                            for (let i in arr) {
                               if(_this.userList.indexOf(JSON.stringify(arr[i])) > -1){
                                   obj[i] = arr[i];
                               }
                            }
                            if (obj.length == arr.length) {
                                _this.indeterminate = false;
                                _this.checkAll = true;
                                _this.len = arr.length;
                            } else if (obj.length > 0) {
                                _this.indeterminate = true;
                                _this.checkAll = false;
                                _this.len = obj.length;
                            } else {
                                _this.indeterminate = false;
                                _this.checkAll = false;
                                _this.len = 0;
                            }
                        }
                    });
                }
            }
        },
        loadData:function(item, callback) {
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
        handleCheckAll:function() {
            let _this = this;
            if (_this.indeterminate) {
                _this.checkAll = false;
            } else {
                _this.checkAll = !_this.checkAll;
            }
            _this.indeterminate = false;
            let arr = _this.userData;
            if (_this.checkAll) {
                //let arr = _this.userData;
                //let obj=new Array();
                for (let i in arr) {
                    //obj[i] = JSON.stringify(arr[i])
                    _this.userList.push(JSON.stringify(arr[i]));
                }
                _this.len = arr.length;
            } else {
                for (let i in arr) {
                    if (_this.userList.indexOf(JSON.stringify(arr[i])) > -1) {
                        _this.userList.splice(_this.userList.indexOf(JSON.stringify(arr[i])), 1);
                    }
                }
                //_this.userList = [];
                _this.len = 0;
            }
            _this.allLen = _this.userList.length;
            this.$emit('checkchange', _this.userList);
        },
        checkAllGroupChange:function(data) {
            let _this = this;
            if (data.length - _this.allLen == 1) {
                _this.allLen += 1;
                _this.len += 1;
            } else if (_this.allLen - data.length == 1) {
                _this.allLen -= 1;
                _this.len -= 1;
            }
            if (_this.len === _this.userData.length) {
                _this.indeterminate = false;
                _this.checkAll = true;
            } else if (_this.len > 0) {
                _this.indeterminate = true;
                _this.checkAll = false;
            } else {
                _this.indeterminate = false;
                _this.checkAll = false;
            }
            //console.log(_this.userList);
            this.$emit('checkchange', _this.userList);
        }
    }
})
