//注册vue的组件
Vue.component("module-tab",{
    props:['tabs'],
    template:"<div class='is-pageHead'><span  v-for='(tab, index) in tabs'><a :href='tab.url'>{{tab.ModName}}</a></span></div>"
})
