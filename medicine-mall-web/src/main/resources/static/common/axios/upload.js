
window.upload = axios.create({
  timeout: 60000, //文件可能比较大响应时间60秒
  baseURL: uploadUrl, //配置接口地址
  withCredentials:true,
  headers: {
    'Content-Type':'multipart/form-data' //配置请求头
  }
});

//POST传参序列化(添加请求拦截器)
upload.interceptors.request.use((config) => {
  // console.log(config.headers);
  return config;
},(error) =>{
  return error;
});
//返回状态判断(添加响应拦截器)
upload.interceptors.response.use((res) =>{
  //对响应数据做些事
  let code = res.data.code;
  if(code!=0){
    Toast(res.data.msg);
    return;
  }
  return res;
}, (error) => {
  return error;
});

