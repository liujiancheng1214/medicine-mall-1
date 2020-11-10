window.$vue = new Vue({
    el: '#app',
    data() {
        return {
            collapsed: false,
            isFullscreen: false,
            maxLogo: '',
            menuList: [],
            currentMenu: $homeMenu,
            password: {p1: '', p2: '', old: ''},
            currentMenuPath: [],
            breadMenuList: [],
            changePwd_modal: false,
            userId: '',
            userName: '',
            headImg: ''
        }
    },
    mounted() {
        this.userId = userSession.userId;
        this.userName = userSession.userName;
        this.headImg = userSession.headImg;
        if (userSession.account == "admin") {
            this.menuList = $menu;
        } else {
            this.initMenu($menu, this.menuList, userSession.permissionValue);
        }

    },
    methods: {
        menuClick(menu) {
            if (menu.children && menu.showChildren !== undefined) {
                menu.showChildren = !menu.showChildren;
            } else {
                this.setCurrentMenu(menu.id);
            }
        },
        setCurrentMenu(id) {
            $vue.currentMenuPath = [];
            let menu = $vue.findCurrentMenu(id, $vue.menuList);
            $vue.currentMenu = menu;
            $vue.breadMenuListAdd(menu);
        },
        findCurrentMenu(id, list) {
            for (let i = 0; i < list.length; i++) {
                let menu = list[i];
                if (menu.id === id) {
                    $vue.currentMenuPath.push(menu);
                    return menu;
                } else if (menu.children) {
                    let child = $vue.findCurrentMenu(id, menu.children);
                    if (child != null) {
                        let index = $vue.currentMenuPath.length - 1;
                        $vue.currentMenuPath.splice(index, 0, menu);
                        return child;
                    }
                }
            }
            return null;
        },
        breadMenuListAdd(menu) {
            for (let i = 0; i < this.breadMenuList.length; i++) {
                if (this.breadMenuList[i].id === menu.id) {
                    return;
                }
            }
            this.breadMenuList.push(menu);
            setTimeout(()=>{
                let width = document.querySelector(".scroll-body").getBoundingClientRect().width;
                let x = document.querySelector(".scroll-outer").getBoundingClientRect().width;
                if(width>x){
                    document.querySelector(".scroll-body").style.left = (x-width)+"px"
                }
            },200)
        },
        breadMenuListRemove(id) {
            let index;
            for (let i = 0; i < this.breadMenuList.length; i++) {
                if (this.breadMenuList[i].id === id) {
                    index = i;
                }
            }
            this.breadMenuList.splice(index, 1);
            //如果删除当前页面
            if (this.currentMenu.id == id) {
                if (this.breadMenuList && this.breadMenuList.length > 0) {
                    let nextId = this.breadMenuList[this.breadMenuList.length - 1].id
                    this.setCurrentMenu(nextId);
                } else {
                    this.goToHome();
                }
            }
        },
        breadMenuListRemoveAll() {
            this.breadMenuList = [];
            this.goToHome();
            document.querySelector(".scroll-body").style.left=(0+"px");

        },
        goToHome() {
            this.currentMenu = $homeMenu;
            this.currentMenuPath = [];
        },
        operateUser(op) {
            if (op === "changePwd") {
                this.changePwd_modal = true;
                return;
            }
            if (op === "loginOut") {
                this.$Modal.confirm({
                    title: '提示',
                    content: '<h4>确定要退出么</h4>',
                    onOk: () => {
                        api.post("/cms/auth/loginOut").then((res) => {
                            if (res.data.code === 0) {
                                //跳转到登录页面
                                window.location.href = "/login";
                            }
                        }).catch(() => {
                        });
                    },
                    onCancel: () => {
                    }
                });
                return;
            }
        },
        checkPwd() {
            if (this.password.p1 != this.password.p2) {
                this.$Message.warning("请输入相同的新密码！");
                return false;
            }
            return true;
        },
        changePwdSubmit() {
            if (this.checkPwd() === false) {
                return;
            }
            let adminDto = {};
            adminDto.adminId = this.userId;
            adminDto.oldPassword = this.password.old;
            adminDto.newPassword1 = this.password.p1;
            adminDto.newPassword2 = this.password.p2;
            api.post("/cms/admin/password/update",
                adminDto
            ).then((res) => {
                if (res.data.code === 0) {
                    this.$Message.warning("操作成功！");
                    this.password = {p1: '', p2: '', old: ''};
                } else {
                    this.$Message.warning("操作失败！ 错误信息：" + res.data.msg)
                }
            }).catch(() => {
            });
        },
        initMenu(list, menu, perms) {
            list.forEach(li => {
                let auth = li.auth;
                let children = li.children;
                if (auth == null || auth == "" || perms.indexOf(auth) >= 0) {
                    li.children = [];
                    menu.push(li)
                    if (children != null && children.length != 0) {
                        this.initMenu(children, li.children, perms)
                    }
                }
            })
        },
        scroll(isLeft) {
            let width = document.querySelector(".scroll-body").getBoundingClientRect().width;
            let x = document.querySelector(".scroll-outer").getBoundingClientRect().width;
            let style = document.querySelector(".scroll-body").style;
            let str = style.left;
            let left = Number(str.substr(0, str.length - 2));
            if(width>x){
                if (isLeft) {
                    left += 500
                    if (left > 0) {
                        left = 0
                    }
                } else {
                    left -= 500
                    if (left < x - width) {
                        left = x - width
                    }
                }
                style.left = (left + "px");
            }else if(left!=0){
                style.left = 0+"px";
            }
        },

    }
});