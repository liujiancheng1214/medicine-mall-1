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
            formData.append(file.name,file);
            debugger;
            upload.post('/upload/uploadFile',formData).then((res)=>{
                debugger;
                this.fileSrc = res.data.data;
            }).catch((error)=>{
                console.log(error);
            });
            return false;
        }
    }
});