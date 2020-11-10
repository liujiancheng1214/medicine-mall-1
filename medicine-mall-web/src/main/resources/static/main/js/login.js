window.pwd = "";
window.vm = new Vue({
    el: '#container',
    data: function () {
        return {
            account: '',
            pwd: ''
        }
    },
    mounted: function () {
    },
    methods: {
        //信息弹层
        info: function (str) {
            this.$Modal.info({
                title: '提示信息',
                content: str
            });
        },
        error: function (str) {
            this.$Modal.error({
                title: '提示信息',
                content: str
            });
        },
        //登录
        login() {
            api.post('/cms/auth/login', {
                account: this.account,
                password: hex_md5(this.pwd)
            }).then(function (resp) {
                let vo = resp.data;
                if (vo.code == 0) {
                    window.location.href = "/main";
                } else {
                    alert(vo.msg);
                }
            });
        }
    }
})
;
