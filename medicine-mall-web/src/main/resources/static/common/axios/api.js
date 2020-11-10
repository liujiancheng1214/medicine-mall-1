//import Es6Promise from 'es6-promise'
////兼容低版本的es对Promise支持
//Es6Promise.polyfill();

window.api = axios.create({
	timeout: 15000,
	baseURL: apiUrl,
    withCredentials:true,
});

//axios基础配置
//api.defaults.timeout = 15000;  //响应时间 15秒
//api.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=UTF-8'; //配置请求头
//api.defaults.baseURL = process.env.API_DOMAIN;   //配置接口地址

// Expose Cancel & CancelToken
api.Cancel = axios.Cancel;
api.CancelToken = axios.CancelToken;
api.isCancel = axios.isCancel;

// Expose all/spread
api.all = axios.all;
api.spread = axios.spread;

//POST传参序列化(添加请求拦截器)
api.interceptors.request.use(function(config){
  //url带上时间的链接
  let url = config.url;
  if(url){
    let time = (new Date()).getTime();
    if(url.indexOf('?')>0){
      config.url = url + '&'+time;
    }else{
      config.url = url + '?'+time;
    }
  }
  //在发送请求之前做某件事
    /**
	 * post请求不序列化，后台用必须用RequestBody注解接收data体
	 * RequestParam接收url后面带的参数
     */
	// if(config.method === 'post') {
	// 	config.data = Qs.stringify(config.data,{arrayFormat: 'repeat'});
	// }
	return config;
}, function(error) {
	//return Promise.reject(error);
	return error;
});
//返回状态判断(添加响应拦截器)
api.interceptors.response.use(function(res) {
	//对响应数据做些事
	let code = res.data.code;
	let type = res.data.errorType;
	if(code != 0) {
		//405 forbidden 没有权限访问
		if(code == 405 && type =="auth" ) {
			alert("无权限访问");
			return res;
		}
        //405 需要调转到登录
        if(code == 901 && type =="auth" ) {
            window.location.href="/login";
        }
		return res;
	}
	return res;
}, function(error) {
	//return Promise.reject(error);
	return error;
});
