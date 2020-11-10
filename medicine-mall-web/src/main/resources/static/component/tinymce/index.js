//注册vue的组件
const toolbar = ['searchreplace bold italic underline strikethrough alignleft aligncenter alignright outdent indent  blockquote undo redo removeformat subscript superscript code codesample', 'hr bullist numlist link image charmap preview anchor pagebreak insertdatetime media table emoticons forecolor backcolor fullscreen']
const plugins = ['advlist anchor autolink autosave code codesample colorpicker colorpicker contextmenu directionality emoticons fullscreen hr image imagetools importcss insertdatetime link lists media nonbreaking noneditable pagebreak paste preview print save searchreplace spellchecker tabfocus table template textcolor textpattern visualblocks visualchars wordcount']


Vue.component("Tinymce",{
    // components: { editorImage },
    template:'<div :class="{fullscreen:fullscreen}" class="tinymce-container editor-container">' +
    '    <textarea :id="tinymceId" class="tinymce-textarea"/>' +
    // '    <div class="editor-custom-btn-container">\n' +
    // '      <editorImage color="#1890ff" class="editor-upload-btn" @successCBK="imageSuccessCBK"/>\n' +
    // '    </div>\n' +
    '  </div>',
    props: {
        id: {
            type: String,
            default: function() {
                return 'vue-tinymce-' + +new Date() + ((Math.random() * 1000).toFixed(0) + '')
            }
        },
        value: {
            type: String,
            default: ''
        },
        toolbar: {
            type: Array,
            required: false,
            default() {
                return []
            }
        },
        menubar: {
            type: String,
            default: 'file edit insert view format table'
        },
        width: {
            type: Number,
            required: false,
            default: 800
        },
        height: {
            type: Number,
            required: false,
            default: 360
        }
    },
    data() {
        return {
            hasChange: false,
            hasInit: false,
            tinymceId: this.id,
            fullscreen: false,
            // languageTypeList: {
            //     'en': 'en',
            //     'zh': 'zh_CN'
            // }
            language:'zh_CN'
        }
    },
    computed: {
        // language() {
        //     return this.languageTypeList[this.$store.getters.language]
        // }
    },
    watch: {
        value(val) {
            if (!this.hasChange && this.hasInit) {
                this.$nextTick(() =>
                    window.tinymce.get(this.tinymceId).setContent(val || ''))
            }
        },
        language() {
            this.destroyTinymce()
            this.$nextTick(() => this.initTinymce())
        }
    },
    mounted() {
        this.initTinymce()
    },
    activated() {
        this.initTinymce()
    },
    deactivated() {
        this.destroyTinymce()
    },
    destroyed() {
        this.destroyTinymce()
    },
    methods: {
        initTinymce() {
            const _this = this;
            window.tinymce.init({
                language: this.language,  //语言
                selector: `#${this.tinymceId}`, //选择器
                elementpath:false,
                width:this.width,
                height: this.height, //高度
                body_class: 'panel-body ', //样式
                object_resizing: false, //是否可以缩放
                toolbar: this.toolbar.length > 0 ? this.toolbar : toolbar,//工具栏
                menubar: this.menubar, //菜单栏
                // menubar: false,
                plugins: plugins, //插件
                end_container_on_empty_block: true,
                powerpaste_word_import: 'clean',
                code_dialog_height: 450,
                code_dialog_width: 1000,
                advlist_bullet_styles: 'square',
                advlist_number_styles: 'default',
                imagetools_cors_hosts: ['www.tinymce.com', 'codepen.io'],
                default_link_target: '_blank',
                link_title: false,
                nonbreaking_force_tab: true, // inserting nonbreaking space &nbsp; need Nonbreaking Space Plugin
                init_instance_callback: editor => {
                    if (_this.value) {
                        editor.setContent(_this.value)
                    }
                    _this.hasInit = true
                    editor.on('NodeChange Change KeyUp SetContent', () => {
                        this.hasChange = true
                        this.$emit('input', editor.getContent())
                    })
                },
                setup(editor) {
                    editor.on('FullscreenStateChanged', (e) => {
                        _this.fullscreen = e.state
                    })
                }
                // 整合七牛上传
                // images_dataimg_filter(img) {
                //   setTimeout(() => {
                //     const $image = $(img);
                //     $image.removeAttr('width');
                //     $image.removeAttr('height');
                //     if ($image[0].height && $image[0].width) {
                //       $image.attr('data-wscntype', 'image');
                //       $image.attr('data-wscnh', $image[0].height);
                //       $image.attr('data-wscnw', $image[0].width);
                //       $image.addClass('wscnph');
                //     }
                //   }, 0);
                //   return img
                // },
                // images_upload_handler(blobInfo, success, failure, progress) {
                //   progress(0);
                //   const token = _this.$store.getters.token;
                //   getToken(token).then(response => {
                //     const url = response.data.qiniu_url;
                //     const formData = new FormData();
                //     formData.append('token', response.data.qiniu_token);
                //     formData.append('key', response.data.qiniu_key);
                //     formData.append('file', blobInfo.blob(), url);
                //     upload(formData).then(() => {
                //       success(url);
                //       progress(100);
                //     })
                //   }).catch(err => {
                //     failure('出现未知问题，刷新页面，或者联系程序员')
                //     console.log(err);
                //   });
                // },
            })
        },
        destroyTinymce() {
            if (window.tinymce.get(this.tinymceId)) {
                window.tinymce.get(this.tinymceId).destroy()
            }
        },
        setContent(value) {
            window.tinymce.get(this.tinymceId).setContent(value)
        },
        getContent() {
            window.tinymce.get(this.tinymceId).getContent()
        },
        imageSuccessCBK(arr) {
            const _this = this;
            arr.forEach(v => {
                window.tinymce.get(_this.tinymceId).insertContent(`<img class="wscnph" src="${v.url}" >`)
            })
        }
    }
});
