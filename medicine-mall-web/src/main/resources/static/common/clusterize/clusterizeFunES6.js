
var clusterizeTable=Vue.component('clusterize-table',{
    created(){
        if(typeof this.height=="string"&&this.height.indexOf('%')!=-1){
            this.styles.wrapH=this.height;
        }else{
            this.styles.boxHeight=parseInt(this.height)-42; /*41px(表头)  1px(iview的.ivu-table-wrapper容器样式边框线)*/
        }
    },
    mounted(){
        this.initTable('scrollArea','contentArea');
        this.colw_resize('custom-scrolltable','scrolltable');
        var _this=this;
        window.onresize=function(){
        _this.colw_resize('custom-scrolltable','scrolltable');
        };
    },
    props:['head','data','height'],
    data(){
        return{
            col_w:'auto',
            styles:{
                wrapH:'auto',
                paddingRight:0,
                boxHeight:0
            }
        }
    },
    methods:{
        /*计算表格col和主体滚动部分高度*/
        colw_resize(wrapperRef,scrollBoxRef){
            let wrapper=this.$refs[wrapperRef]; 
            let scrollW=wrapper.offsetWidth-this.$refs[scrollBoxRef].offsetWidth;
            this.styles.paddingRight=scrollW;
            this.col_w=(wrapper.offsetWidth-(scrollW))/this.head.length;
            this.styles.boxHeight=wrapper.offsetHeight-42; /*41px(表头)  1px(iview的.ivu-table-wrapper容器样式边框线)*/
        },
        /*数据修改完resetHtml*/
        resetHtml(obj,index,key){
            vm.tableData[index][key]=obj.value;
            obj.parentNode.innerHTML=obj.value;
        },
        /*初始化table*/
        initTable(scrollId,contentId){
            var data=[];
            for(let i=0;i<this.data.length;i++){
                let str=`<tr class="ivu-table-row" data-index="${i}">`;
                for(let j in this.head){
                    str+=`<td style="cursor:pointer" data-tdIndex="${j}"><div class="ivu-table-cell">${this.data[i][this.head[j].key]}</div></td>`;
                }
                str+='</tr>';
                data.push(str);
            }
            /*滚动加载实例*/
            clusterize = new Clusterize({
                rows: data,
                scrollId: scrollId,
                contentId: contentId,
                rows_in_block:40
            });
            let _this=this;
            /*点击切换输入框*/
            document.querySelector(`#${scrollId} #${contentId}`).onclick=function(e){
               /*
                  var showDoms=this.querySelectorAll('.ivu-table-cell .ivu-input').length>1?this.querySelectorAll('.ivu-table-cell .ivu-input'):[]; 
                  for(let i=0;i<showDoms.length;i++){
                    showDoms[i].parentNode.innerHTML=showDoms[i].value;
                  }
               */
                var ev = e || window.Event;
                var elm = ev.target || ev.srcElement;
                var dom=elm;
                if(dom.id==`${contentId}`||dom.tagName=="TR"){return;}
                try{
                    while (dom.tagName != "TD") {
                        dom = dom.parentNode;
                    }
                }catch(e){
                    return;
                }
                if(dom.querySelector('.ivu-table-cell *')!=null)return;
                let index=parseInt(dom.parentNode.getAttribute('data-index'));
                let headObj=_this.head[dom.getAttribute('data-tdIndex')];
                if(headObj.type==undefined){
                    return;
                }
                let newHtml=_this.htmlTemplate(headObj,index)
                dom.querySelector('.ivu-table-cell').innerHTML=newHtml;
                dom.querySelector('.ivu-table-cell .ivu-input').focus();
            };
        },
        /*输入框布局调用*/
        htmlTemplate(headObj,index){
            console.log(clusterizeTable);
            let str=null;
            if(headObj.type=='input')str=`<input autocomplete="off" value="${this.data[index][headObj.key]}" onblur="clusterizeTable.options.methods.resetHtml(this,${index},'${headObj.key}')" spellcheck="false" type="text" placeholder="${headObj.placeholder}" class="ivu-input ivu-input-default">`;
            if(headObj.type=='select'){
                str=`<select class="ivu-input ivu-input-default" style="cursor:pointer;" onchange="clusterizeTable.options.methods.resetHtml(this,${index},'${headObj.key}')">`;
                    for(let i=0;i<headObj.options.length;i++){
                        str+=`<option ${this.data[index][headObj.key]==headObj.options[i].value?'selected':''} value="${headObj.options[i].value}">${headObj.options[i].label}</option>`;
                    }
                str+='</select>';
            }
            return str;
        }
    }, 
    template:`<div class="clusterize"  ref="custom-scrolltable" :style="'height:'+styles.wrapH">

                <div class="ivu-table-wrapper"  ref="custom-scrolltable" style="height:100%">
                    <div class="ivu-table ivu-table-default" style="height:100%">

                        <!-- 表格头部 -->
                        <div class="ivu-table-header" :style="'background-color: #f8f8f9;padding-right:'+styles.paddingRight+'px'">
                            <table cellspacing="0" cellpadding="0" border="0" style="width:100%">
                                <colgroup>
                                    <col v-for="(item,index) in head" :width="col_w">
                                </colgroup>
                                <thead>
                                    <tr>
                                        <th v-for="(item,index) in head">
                                            <div class="ivu-table-cell">
                                                <span v-html="item.title">姓名</span>
                                            </div>
                                        </th>
                                    </tr>
                                </thead>
                            </table>
                        </div>

                        <div id="scrollArea" class="clusterize-scroll" :style="'max-height: none;height:'+styles.boxHeight+'px;'"> 

                            <!-- 表格主体数据 -->
                            <div class="ivu-table-body" ref="scrolltable">
                                <table cellspacing="0" cellpadding="0" border="0" style="width:100%">
                                    <colgroup>
                                        <col v-for="(item,index) in head" :width="col_w">
                                    </colgroup>
                                    <tbody id="contentArea" class="ivu-table-tbody clusterize-content">
                                        <!-- <tr class="ivu-table-row" :data-index="index"  v-for="(item,index) in data"> 
                                            <td :data-tdIndex="index" v-for="(head,index) in head">
                                                <div class="ivu-table-cell" v-html="item[head.key]"></div>
                                            </td>
                                        </tr> -->
                                    </tbody>
                                </table>
                            </div>

                        </div>
                    </div>
                </div>

                </div>`
});

