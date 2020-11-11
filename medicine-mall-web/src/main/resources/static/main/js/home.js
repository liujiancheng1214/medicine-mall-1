window.$vue = new Vue({
    el: '#app',
    data() {
        return {
            fileSrc:null
        }
    },
    mounted(){
    },
    methods: {
        uploadFile(file){
            let formData = new FormData();
            formData.append("file", file);
            debugger;
            upload.post('/file/fileUpload',formData).then((res)=>{
                debugger;
                this.fileSrc = res.data.data;
            }).catch((error)=>{
                console.log(error);
            });
            return false;
        }
    }
});
